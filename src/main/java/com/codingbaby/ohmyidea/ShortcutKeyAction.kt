package com.codingbaby.ohmyidea


import `fun`.codecode.EditorStatus
import `fun`.codecode.KeyHandler
import `fun`.codecode.OhPlugin
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import java.awt.event.KeyEvent
import javax.swing.KeyStroke


/**
 * 绑定到编辑组件上的控制键行为
 */
class ShortcutKeyAction : AnAction(), DumbAware {

    override fun actionPerformed(anActionEvent: AnActionEvent) {

        if (!Oh.get().active) {
            return
        }

        val keyStroke = getKeyStroke(anActionEvent)

        if (keyStroke != null && keyStroke.keyCode == KeyEvent.VK_ESCAPE) {

            if (Oh.get().status !== EditorStatus.Command) {
                OhPlugin.mode(EditorStatus.Command)
            }

            //将esc传递到IDE
            KeyHandler.executeAction("EditorEscape", anActionEvent.dataContext)
            KeyHandler.toChar = null

            CommandBuffer.reset()
        }

    }


    private fun getKeyStroke(e: AnActionEvent): KeyStroke? {
        val inputEvent = e.inputEvent
        if (inputEvent is KeyEvent) {
            return KeyStroke.getKeyStrokeForEvent(inputEvent)
        }
        return null
    }

}
