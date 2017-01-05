package com.codingbaby.ohmyidea.script

import com.codingbaby.ohmyidea.shortcut.*
import com.intellij.openapi.util.io.FileUtil
import groovy.lang.Binding
import groovy.lang.GroovyClassLoader
import org.apache.commons.lang.StringUtils
import org.codehaus.groovy.runtime.InvokerHelper
import java.io.File
import java.io.IOException
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

        def key(key,desc,code) {
            outerList << ["key":key,"desc":desc,"code":code]
        }

}

class ActorContainer {

        def outerAction


        ActorContainer(env){
            outerAction = env
        }


    def key(key,action,desc) {
            outerAction.add(key, action, desc)
    }


}

def code_snippet = {
    closure ->
        closure.delegate = new CodeContainer(envList)
        closure()
}

def app_quick_open = {
    closure ->
        closure.delegate = new RobotContainer(envMap)
        closure()
}

def keyboard_select = {
    closure ->
        closure.delegate = new ActorContainer(vmode)
        closure()
}

def keyboard_single = {
    closure ->
        closure.delegate = new ActorContainer(smode)
        closure()
}

def keyboard_movement = {
    closure ->
        closure.delegate = new ActorContainer(mmode)
        closure()
}

def keyboard_composite = {
    closure ->
        closure.delegate = new ActorContainer(cmode)
        closure()
}

def keyboard_bottom = {
    closure ->
        closure.delegate = new ActorContainer(bmode)
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
        loadGroovy(content)
    }


    fun loadGroovy(text: String) {
        val groovyClassLoader = GroovyClassLoader()
        val scriptClass = groovyClassLoader.parseClass(dsl + text)

        var keyHolder = ArrayList<HashMap<String, String>>()
        var robotHolder = HashMap<String, List<Int>>()

        var bind = Binding()
        bind.setVariable("envList", keyHolder)
        bind.setVariable("envMap", robotHolder)

        VisualShort.commandHolder.clear()
        SingleShort.commandHolder.clear()
        MoveShort.commandHolder.clear()
        ComposeShort.commandHolder.clear()
        BottomShort.commandHolder.clear()

        bind.setVariable("vmode", VisualShort.commandHolder)
        bind.setVariable("smode", SingleShort.commandHolder)
        bind.setVariable("mmode", MoveShort.commandHolder)
        bind.setVariable("cmode", ComposeShort.commandHolder)
        bind.setVariable("bmode", BottomShort.commandHolder)

        InvokerHelper.createScript(scriptClass, bind).run()

        RobotHandler.holder.clear()
        RobotHandler.holder.putAll(robotHolder)

        CodeSnippet.desc.clear()
        CodeSnippet.code.clear()

        for (map in keyHolder) {
            var key = map["key"]
            var desc = map["desc"]
            var code = map["code"]

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
