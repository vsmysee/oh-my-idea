package com.codingbaby.ohmyidea.action

import com.codingbaby.ohmyidea.OhPlugin
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction

class ControlPluginAction : DumbAwareAction() {

    override fun actionPerformed(e: AnActionEvent) {
        OhPlugin.active()
    }
}