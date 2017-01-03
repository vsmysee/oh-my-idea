package com.codingbaby.ohmyidea.key

import javax.swing.*

/**
 * 底行快捷键
 */
object BottomShort {

    var commandHolder = CommandHolder()


    operator fun get(keyStroke: KeyStroke): CommandNode? {
        return commandHolder[keyStroke]
    }

}
