package com.codingbaby.ohmyidea.script

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
        val scriptClass = groovyClassLoader.parseClass(GroovyDef.groovy + text)

        var keyHolder = ArrayList<HashMap<String, String>>()
        var robotHolder = HashMap<String, List<Int>>()

        var bind = Binding()
        bind.setVariable("envList", keyHolder)
        bind.setVariable("envMap", robotHolder)

        ShortHolder.select.clear()
        ShortHolder.single.clear()
        ShortHolder.movement.clear()
        ShortHolder.compose.clear()
        ShortHolder.bottom.clear()

        bind.setVariable("vmode", ShortHolder.select)
        bind.setVariable("smode", ShortHolder.single)
        bind.setVariable("mmode", ShortHolder.movement)
        bind.setVariable("cmode", ShortHolder.compose)
        bind.setVariable("bmode", ShortHolder.bottom)

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
