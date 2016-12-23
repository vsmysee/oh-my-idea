package com.codingbaby.ohmyidea.script

import clojure.lang.RT
import com.intellij.openapi.util.io.FileUtil
import groovy.lang.Binding
import groovy.lang.GroovyClassLoader
import org.apache.commons.lang.StringUtils
import org.codehaus.groovy.runtime.InvokerHelper
import java.io.File
import java.io.IOException
import java.io.StringReader
import java.util.*


object OhScript {

    val OH_FILE = ".oh-my-idea"

    val dsl = """

import java.awt.Robot;
import java.awt.event.KeyEvent;

class RobotContainer {

        def outerList

        RobotContainer(env){
            outerList = env
        }

        def key(key,list) {
            outerList[key] = list
        }
}


class CodeContainer {

        def outerList

        def holder

        CodeContainer(env){
            outerList = env
        }


    def key(key) {
        holder = [:]
        holder["key"] = key
    }

    def desc(desc) {
        holder["desc"] = desc

    }

    def code(code) {
        holder["code"] = code
        outerList << holder
    }

}

def oh = {
    closure ->
        closure.delegate = new CodeContainer(envList)
        closure()
}

def ho = {
    closure ->
        closure.delegate = new RobotContainer(envMap)
        closure()
}


"""


    fun loadScriptFile() {

        if (try {
            Class.forName("groovy.lang.GroovyClassLoader")
            false
        } catch (e: Exception) {
            true
        }) {
            return
        }

        val content = loadContent()
        if (content.startsWith("#lang:groovy")) {
            loadGroovy(content.replace("#lang:groovy", ""))
        }
        if (content.startsWith("#lang:clojure")) {
            loadClojure(content.replace("#lang:clojure", ""))
        }

    }

    fun loadClojure(text: String) {
        try {
            RT.load("clojure/core")
            clojure.lang.Compiler.load(StringReader(text))
            val scriptData = RT.`var`("oh-my-idea", "oh").invoke()
        } catch (e: Exception) {
        }
    }

    fun loadGroovy(text: String) {
        val groovyClassLoader = GroovyClassLoader()
        val scriptClass = groovyClassLoader.parseClass(dsl + text)

        var keyHolder = ArrayList<HashMap<String, String>>()
        var robotHolder = HashMap<String, ArrayList<Int>>()

        var bind = Binding()
        bind.setVariable("envList", keyHolder)
        bind.setVariable("envMap", robotHolder)

        InvokerHelper.createScript(scriptClass, bind).run()

        RobotHandler.holder.clear()
        RobotHandler.holder.putAll(robotHolder)

        for (map in keyHolder) {
            var key = map["key"]
            var desc = map["desc"]
            var code = map["code"]

            CodeSnippet.desc.clear()
            CodeSnippet.code.clear()

            CodeSnippet.desc.put(key as String, desc as String)
            CodeSnippet.code.put(key, code as String)
        }
    }


    fun saveScript(text: String) {
        if (StringUtils.isNotBlank(text)) {
            val homeDirName = System.getProperty("user.home")
            if (homeDirName != null) {
                val file = File(homeDirName, OH_FILE)
                try {
                    FileUtil.writeToFile(file, text)
                    loadScriptFile()
                } catch (e: IOException) {
                }

            }
        }
    }

    fun loadContent(): String {
        val homeDirName = System.getProperty("user.home")
        if (homeDirName != null) {
            val file = File(homeDirName, OH_FILE)
            if (file.exists()) {
                try {
                    return file.readText(Charsets.UTF_8)
                } catch (e: IOException) {
                    return ""
                }
            }
        }
        return ""
    }


}
