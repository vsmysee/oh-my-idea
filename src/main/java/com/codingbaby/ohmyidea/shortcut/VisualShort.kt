package com.codingbaby.ohmyidea.shortcut

import com.codingbaby.ohmyidea.key.CommandHolder
import com.codingbaby.ohmyidea.key.CommandNode
import javax.swing.*


object VisualShort {

    var commandHolder = CommandHolder()


    operator fun get(keyStroke: KeyStroke): CommandNode? {
        return commandHolder[keyStroke]
    }

}