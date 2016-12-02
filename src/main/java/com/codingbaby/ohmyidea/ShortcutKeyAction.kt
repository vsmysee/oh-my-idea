package com.codingbaby.ohmyidea


import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.DumbAware
import java.awt.event.KeyEvent
import javax.swing.KeyStroke

/**

 * 绑定到编辑组件上的控制键行为
 */
class ShortcutKeyAction : AnAction(), DumbAware {


    override fun actionPerformed(anActionEvent: AnActionEvent) {

        val keyStroke = getKeyStroke(anActionEvent)
        if (keyStroke != null && keyStroke.keyCode == KeyEvent.VK_ESCAPE && OhPlugin.isEnabled) {

            if (OhPlugin.instance.status !== EditorStatus.Command) {
                KeyHandler.toCommandMod()
                OhPlugin.setPopWindowOpen(false)
            }

            //将esc传递到IDE
            KeyHandler.executeAction("EditorEscape", anActionEvent.dataContext)
            KeyHandler.toChar = null

            OhPlugin.instance.commandStatus.reset()
        }
    }


    override fun update(e: AnActionEvent?) {
        val editor = e!!.getData(PlatformDataKeys.EDITOR)
        if (editor == null) {
            e.presentation.isEnabled = false
        } else {
            val keyStroke = getKeyStroke(e)
            e.presentation.isEnabled = keyStroke != null
        }

    }


    private fun getKeyStroke(e: AnActionEvent): KeyStroke? {
        val inputEvent = e.inputEvent
        if (inputEvent is KeyEvent) {
            return KeyStroke.getKeyStrokeForEvent(inputEvent)
        }
        return null
    }

    companion object {

        private val ACTION_ID = "ShortcutKeyAction"

        val instance: AnAction
            get() = ActionManager.getInstance().getAction(ACTION_ID)
    }

}
