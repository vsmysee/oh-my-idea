package com.codingbaby.ohmyidea.script

import com.codingbaby.ohmyidea.script.lisp.EnvBuilder
import com.codingbaby.ohmyidea.script.lisp.Evaler
import com.codingbaby.ohmyidea.script.lisp.FormReader
import com.codingbaby.ohmyidea.script.lisp.Tokenr
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
    val OH_JET_FILE = ".oh-my-jetbrains"


    fun loadGroovyScriptFile() {
        if (try {
            Class.forName("groovy.lang.GroovyClassLoader")
            false
        } catch (e: Exception) {
            true
        }) {
            val content = loadContent(OH_JET_FILE)
            val env = EnvBuilder.globalEnv()
            val lists = Evaler.eval(FormReader.readFrom(Tokenr.tokenize(content)), env) as java.util.ArrayList<Object>
            for (list in lists) {
                var single = list as java.util.ArrayList<String>
                ShortHolder.single.add(single.get(0), single.get(1), single.get(2))
            }
            return
        }

        val content = loadContent(OH_FILE)

        val groovyClassLoader = GroovyClassLoader()
        val scriptClass = groovyClassLoader.parseClass(GroovyDef.groovy + content)

        var codes = ArrayList<HashMap<String, String>>()
        var robotHolder = HashMap<String, List<Int>>()

        var bind = Binding()
        bind.setVariable("envList", codes)
        bind.setVariable("envMap", robotHolder)

        ShortHolder.clear()

        bind.setVariable("smode", ShortHolder.single)
        bind.setVariable("vmode", ShortHolder.select)
        bind.setVariable("mmode", ShortHolder.movement)
        bind.setVariable("cmode", ShortHolder.compose)
        bind.setVariable("bmode", ShortHolder.bottom)

        InvokerHelper.createScript(scriptClass, bind).run()

        RobotHandler.holder.clear()
        RobotHandler.holder.putAll(robotHolder)

        CodeSnippet.clear()

        for (map in codes) {
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

    fun loadContent(path:String): String {
        val homeDirName = System.getProperty("user.home")
        if (homeDirName != null) {
            val file = File(homeDirName, path)
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
