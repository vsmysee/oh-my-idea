package `fun`.codecode.action

import `fun`.codecode.PluginStatus
import com.codingbaby.ohmyidea.script.OhScript
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ToggleAction

class PluginToggleAction : ToggleAction() {

    override fun isSelected(e: AnActionEvent): Boolean {
        return PluginStatus.active
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {

        if (state) {
            OhScript.loadGroovyScriptFile()
        }

        PluginStatus.active(state)
    }
}
