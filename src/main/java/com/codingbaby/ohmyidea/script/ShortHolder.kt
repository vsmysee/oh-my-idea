package com.codingbaby.ohmyidea.script

import com.codingbaby.ohmyidea.CommandHolder


object ShortHolder {

    var bottom = CommandHolder()
    var composite = CommandHolder()
    var movement = CommandHolder()
    var single = CommandHolder()
    var select = CommandHolder()

    fun clear() {
        bottom.clear()
        composite.clear()
        movement.clear()
        single.clear()
        select.clear()
    }

}