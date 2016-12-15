package com.codingbaby.ohmyidea.script


import com.intellij.openapi.util.io.FileUtil
import groovy.lang.Binding
import groovy.lang.GroovyClassLoader
import org.apache.commons.lang.StringUtils
import org.codehaus.groovy.runtime.InvokerHelper
import java.io.File
import java.io.IOException
import java.util.*


/**
 * _ls|描述
 * List list = new ArrayList()
 *
 */
object OhScript {

    val OH_FILE = ".oh-my-idea"

    val dsl = """
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


"""


    fun loadScriptFile() {
        val content = loadContent()
        val groovyClassLoader = GroovyClassLoader()
        val scriptClass = groovyClassLoader.parseClass(dsl + content)
        var bind = Binding()
        var holder = ArrayList<HashMap<String, String>>()
        bind.setVariable("envList", holder)
        var script = InvokerHelper.createScript(scriptClass, bind)
        script.run()

        for (map in holder) {
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
