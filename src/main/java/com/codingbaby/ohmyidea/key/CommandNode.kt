package com.codingbaby.ohmyidea.key

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction

class CommandNode(var actionId: String) {
    fun asAction(): AnAction {
        return ActionManager.getInstance().getAction(actionId)
    }
}
