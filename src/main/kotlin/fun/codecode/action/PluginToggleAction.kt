package `fun`.codecode.action

import `fun`.codecode.PluginStatus
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareToggleAction

class PluginToggleAction : DumbAwareToggleAction() {

    override fun isSelected(e: AnActionEvent): Boolean {
        return PluginStatus.active
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        PluginStatus.active(state)
    }
}