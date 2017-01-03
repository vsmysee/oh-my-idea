package com.codingbaby.ohmyidea.key

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction

class CommandNode(actionId: String) {

    val id: String

    init {
        this.id = actionId
    }

    fun asAction(): AnAction {
        return ActionManager.getInstance().getAction(id)
    }


}
