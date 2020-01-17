package com.codingbaby.ohmyidea

import javax.swing.*


object CommandStatus {

    val FORWARD_KEY = "f"

    //command buffer
    private var sb = StringBuffer()


    fun addChar(c: Char) {
        sb.append(c)
    }

    val isWaiting: Boolean
        get() = sb.length == 0

    fun hasStroke(): Boolean {
        return sb.length == 1
    }

    fun fistChar(): Char {
        return sb[0]
    }

    fun stroke(): KeyStroke {
        return KeyStroke.getKeyStroke(sb[0])
    }

    fun isForward(): Boolean {
        return sb.toString().startsWith(FORWARD_KEY)
    }

    fun forwardChar(): Char? {
        val command = sb.toString()
        if (command.startsWith(FORWARD_KEY) && sb.length == 2) {
            return command.substring(1)[0]
        }
        return null
    }


    //比如gj，j生效
    fun lastChar(): Char? {
        if (sb.length > 1) {
            return sb[sb.length - 1]
        }
        return null
    }


    fun reset() {
        sb = StringBuffer()
    }

    fun command(): String {
        return sb.toString()
    }

}