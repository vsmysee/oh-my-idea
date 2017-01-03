package com.codingbaby.ohmyidea.key

import com.codingbaby.ohmyidea.OhPlugin

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
