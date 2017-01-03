package com.codingbaby.ohmyidea.key

import javax.swing.*

object MoveShort {

    var commandHolder = CommandHolder()


    operator fun get(keyStroke: KeyStroke): CommandNode? {
        return commandHolder[keyStroke]
    }
}
