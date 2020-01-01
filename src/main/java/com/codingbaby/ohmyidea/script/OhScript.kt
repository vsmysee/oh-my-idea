package com.codingbaby.ohmyidea.script

import com.codingbaby.ohmyidea.script.LispDef.content
import com.codingbaby.ohmyidea.script.lisp.EnvBuilder
import com.codingbaby.ohmyidea.script.lisp.Evaler
import com.codingbaby.ohmyidea.script.lisp.FormReader
import groovy.lang.Binding
import groovy.lang.GroovyClassLoader
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
            val env = EnvBuilder.globalEnv()
            Evaler.eval(FormReader.readForm(content), env)
            return
        }

        val content = loadContent(OH_FILE)

        val groovyClassLoader = GroovyClassLoader()
        val scriptClass = groovyClassLoader.parseClass(GroovyDef.groovy + content)

        var robotHolder = HashMap<String, List<Int>>()

        var bind = Binding()
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

    }


    fun loadContent(path: String): String {
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
