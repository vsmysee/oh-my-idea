package com.codingbaby.ohmyidea.key

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction

class CommandNode(protected val actionId: String) {

    val action: AnAction

    init {
        val aMgr = ActionManager.getInstance()
        this.action = aMgr.getAction(actionId)
    }
}
