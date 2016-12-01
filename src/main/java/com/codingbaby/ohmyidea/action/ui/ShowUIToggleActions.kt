package com.codingbaby.ohmyidea.action.ui

import com.codingbaby.ohmyidea.KeyHandler
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction

/**
 * Created by baby on 16/12/1.
 */
class ShowUIToggleActions : DumbAwareAction() {

    override fun actionPerformed(e: AnActionEvent) {
        KeyHandler.executeAction("ViewToolBar", e.getDataContext());
        KeyHandler.executeAction("ViewToolButtons", e.getDataContext());
        KeyHandler.executeAction("ViewStatusBar", e.getDataContext());
    }
}