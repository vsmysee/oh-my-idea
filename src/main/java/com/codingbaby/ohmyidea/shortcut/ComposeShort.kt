package com.codingbaby.ohmyidea.shortcut

import com.codingbaby.ohmyidea.OhPlugin
import com.codingbaby.ohmyidea.key.CommandHolder
import com.codingbaby.ohmyidea.key.CommandNode

/**
 * 组合命令，必须是两个字符
 */
object ComposeShort {

    var commandHolder = CommandHolder()

    operator fun get(key: String): CommandNode? {
        if (key == "yr" || key == "yf") {
            OhPlugin.setPopWindowOpen(true)
        }
        return commandHolder[key]
    }
}
