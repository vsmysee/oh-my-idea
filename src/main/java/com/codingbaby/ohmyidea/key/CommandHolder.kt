package com.codingbaby.ohmyidea.key

import javax.swing.*
import java.util.HashMap
import java.util.LinkedHashMap

class CommandHolder {

    class CommandMapping(var key: String, val commandNode: CommandNode, val desc: String)


    private val data = LinkedHashMap<String, CommandMapping>()

    fun clear() {
        data.clear()
    }

    fun add(key: String, action: String, desc: String) {
        data.put(key, CommandMapping(key, CommandNode(action), desc))
    }

    operator fun get(stroke: KeyStroke): CommandNode? {
        val keyChar = stroke.keyChar
        val commandMapping = data["" + keyChar] ?: return null
        return commandMapping.commandNode
    }

    operator fun get(key: String): CommandNode? {
        val commandMapping = data[key] ?: return null
        return commandMapping.commandNode
    }

    val commandDesc: Map<String, String>
        get() {
            val map = HashMap<String, String>()
            for (commandMapping in data.values) {
                map.put(commandMapping.key, commandMapping.desc)
            }
            return map
        }
}
