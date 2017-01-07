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


    fun loadGroovyScriptFile() {
        if (try {
            Class.forName("groovy.lang.GroovyClassLoader")
            false
        } catch (e: Exception) {
            true
        }) {
            return
        }

        val content = loadContent()

        val groovyClassLoader = GroovyClassLoader()
        val scriptClass = groovyClassLoader.parseClass(GroovyDef.groovy + content)

        var keyHolder = ArrayList<HashMap<String, String>>()
        var robotHolder = HashMap<String, List<Int>>()

        var bind = Binding()
        bind.setVariable("envList", keyHolder)
        bind.setVariable("envMap", robotHolder)

        ShortHolder.clear()

        bind.setVariable("vmode", ShortHolder.select)
        bind.setVariable("smode", ShortHolder.single)
        bind.setVariable("mmode", ShortHolder.movement)
        bind.setVariable("cmode", ShortHolder.compose)
        bind.setVariable("bmode", ShortHolder.bottom)

        InvokerHelper.createScript(scriptClass, bind).run()

        RobotHandler.holder.clear()
        RobotHandler.holder.putAll(robotHolder)

        CodeSnippet.clear()

        for (map in keyHolder) {
            CodeSnippet.desc.put(map["key"] as String, map["desc"] as String)
            CodeSnippet.code.put(map["key"] as String, map["code"] as String)
        }
    }


    fun saveScript(text: String) {
        if (StringUtils.isNotBlank(text)) {
            val homeDirName = System.getProperty("user.home")
            if (homeDirName != null) {
                val file = File(homeDirName, OH_FILE)
                try {
                    FileUtil.writeToFile(file, text)
                    loadGroovyScriptFile()
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
