package com.codingbaby.ohmyidea.script

import `fun`.codecode.OhPlugin
import groovy.lang.Binding
import groovy.lang.GroovyClassLoader
import org.codehaus.groovy.runtime.InvokerHelper
import java.io.File
import java.io.IOException
import java.util.*


object OhScript {

    val OH_FILE = ".oh-my-idea"

    val keyMap = hashMapOf(
            "single" to ShortHolder.single,
            "select" to ShortHolder.select,
            "movement" to ShortHolder.movement,
            "composite" to ShortHolder.composite,
            "bottom" to ShortHolder.bottom
    )

    fun loadGroovyScriptFile() {

        //check groovy jar
        if (try {
                    Class.forName("groovy.lang.GroovyClassLoader")
                    false
                } catch (e: Exception) {
                    true
                }) {
            OhPlugin.active(false)
            return
        }

        //check config file
        val content = loadContent(OH_FILE)
        if (content == "") {
            OhPlugin.active(false)
            return
        }

        val groovyClassLoader = GroovyClassLoader()
        val scriptClass = groovyClassLoader.parseClass(GroovyDef.groovy + content)

        var robotHolder = HashMap<String, List<Int>>()

        var bind = Binding()
        bind.setVariable("envMap", robotHolder)

        ShortHolder.clear()

        bind.setVariable("keyMap", keyMap)

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
