package com.codingbaby.ohmyidea

import javax.swing.*
import java.util.regex.Pattern

class NumberAction(var count: Int, var key: Char)


class CommandStatus {

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

    fun fistChar():Char {
        return sb[0]
    }

    val stroke: KeyStroke
        get() = KeyStroke.getKeyStroke(sb[0])

    val isForward: Boolean
        get() = sb.toString().startsWith(FORWARD_KEY)

    val forwardChar: Char?
        get() {
            val command = sb.toString()
            if (command.startsWith(FORWARD_KEY) && sb.length == 2) {
                return command.substring(1)[0]
            }
            return null
        }

    val numberAction: NumberAction?
        get() {
            if (numberActionPattern.matcher(sb.toString()).matches()) {
                val countString = sb.substring(0, sb.length - 1)
                val count = Integer.parseInt(countString)
                val key = sb[sb.length - 1]
                val numberAction = NumberAction(count, key)
                return numberAction
            }
            return null
        }


    //比如gj，j生效
    val lastChar: Char?
        get() {
            if (sb.length > 1) {
                return sb[sb.length - 1]
            }
            return null
        }


    fun reset() {
        sb = StringBuffer()
    }

    val command: String
        get() = sb.toString()


}