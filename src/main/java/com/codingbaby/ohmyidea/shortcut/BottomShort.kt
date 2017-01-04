package com.codingbaby.ohmyidea.shortcut

import com.codingbaby.ohmyidea.key.CommandHolder
import com.codingbaby.ohmyidea.key.CommandNode
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
