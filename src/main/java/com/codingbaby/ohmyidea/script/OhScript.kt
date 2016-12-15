package com.codingbaby.ohmyidea.script


import com.intellij.openapi.util.io.FileUtil
import groovy.lang.Binding
import groovy.lang.GroovyClassLoader
import org.apache.commons.lang.StringUtils
import org.codehaus.groovy.runtime.InvokerHelper
import java.io.File
import java.io.IOException


/**
 * _ls|描述
 * List list = new ArrayList()
 *
 */
object OhScript {

    val OH_FILE = ".oh-my-idea"

    val dsl = """
import com.codingbaby.ohmyidea.script.CodeSnippet

class CodeDefine {

    private def holder = [:]

    def key(key) {
        holder["key"] = key
    }

    def desc(desc) {
        holder["desc"] = desc

    }

    def code(code) {
        holder["code"] = code

        cs.code.put(holder["key"],holder["code"])
        cs.desc.put(holder["key"],holder["desc"])
    }

}

def oh = {
    closure ->
        def helper = new CodeDefine()
        closure.delegate = helper
        closure()
}


"""


    fun loadScriptFile() {
        val content = loadContent()

        val groovyClassLoader = GroovyClassLoader()
        val scriptClass = groovyClassLoader.parseClass(dsl + content)
        var script = InvokerHelper.createScript(scriptClass, Binding())
        script.setProperty("cs", CodeSnippet())
        script.run()

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
