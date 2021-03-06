package `fun`.codecode.action

import `fun`.codecode.KeyHandler
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction

class ShowUIToggleActions : DumbAwareAction() {

    override fun actionPerformed(e: AnActionEvent) {
        KeyHandler.executeAction("ViewToolBar", e.getDataContext());
        KeyHandler.executeAction("ViewToolButtons", e.getDataContext());
        KeyHandler.executeAction("ViewStatusBar", e.getDataContext());
    }
}