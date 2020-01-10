package com.codingbaby.ohmyidea

import javax.swing.*
import java.util.regex.Pattern

class NumberAction(var count: Int, var key: Char)


object CommandStatus {

    private val numberActionPattern = Pattern.compile("^\\d+[a-z]$")

    val FORWARD_KEY = ";"

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