package com.codingbaby.ohmyidea.script

import java.util.HashMap

object CodeSnippet {

    var desc: HashMap<String, String> = HashMap()
    var code: HashMap<String, String> = HashMap()

    fun clear() {
        desc.clear()
        code.clear()
    }

}
