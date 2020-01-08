package com.codingbaby.ohmyidea.action

import `fun`.codecode.OhPlugin
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareToggleAction

class PluginToggleAction : DumbAwareToggleAction() {

    override fun isSelected(e: AnActionEvent): Boolean {
        return OhPlugin.instance.active
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        OhPlugin.active(state)
    }
}