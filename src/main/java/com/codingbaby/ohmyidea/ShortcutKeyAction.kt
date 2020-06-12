package com.codingbaby.ohmyidea

import `fun`.codecode.EditorStatus
import `fun`.codecode.KeyHandler
import `fun`.codecode.PluginStatus
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware


/**
 * 绑定到编辑组件上的控制键行为
 */
class ShortcutKeyAction : AnAction(), DumbAware {

    override fun actionPerformed(anActionEvent: AnActionEvent) {

        if (!PluginStatus.active) {
            return
        }

        if (PluginStatus.status !== EditorStatus.Command) {
            PluginStatus.mode(EditorStatus.Command)
        }

        //将esc传递到IDE
        KeyHandler.executeAction("EditorEscape", anActionEvent.dataContext)
        KeyHandler.toChar = null

        CommandBuffer.reset()

    }

}
