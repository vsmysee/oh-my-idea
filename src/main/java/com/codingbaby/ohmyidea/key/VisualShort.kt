package com.codingbaby.ohmyidea.key

import javax.swing.*


object VisualShort {

    var commandHolder = CommandHolder()


    operator fun get(keyStroke: KeyStroke): CommandNode? {
        return commandHolder[keyStroke]
    }

}