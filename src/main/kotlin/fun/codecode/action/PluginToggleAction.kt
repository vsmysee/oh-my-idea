package `fun`.codecode.action

import `fun`.codecode.OhPlugin
import com.codingbaby.ohmyidea.Oh
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareToggleAction

class PluginToggleAction : DumbAwareToggleAction() {

    override fun isSelected(e: AnActionEvent): Boolean {
        return Oh.get().active
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        OhPlugin.active(state)
    }
}