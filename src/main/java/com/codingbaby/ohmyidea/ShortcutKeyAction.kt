package com.codingbaby.ohmyidea


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

    var timeRange = 100

    override fun actionPerformed(anActionEvent: AnActionEvent) {

        val keyStroke = getKeyStroke(anActionEvent)

        //当ESC键速度很快的时候改变插件的状态
        if (System.currentTimeMillis() - OhPlugin.instance.controlTime < timeRange) {
            OhPlugin.active(false)
        } else {
            OhPlugin.active(true)
        }

        OhPlugin.instance.controlTime = System.currentTimeMillis()


        if (keyStroke != null && keyStroke.keyCode == KeyEvent.VK_ESCAPE && OhPlugin.instance.active) {

            if (OhPlugin.instance.status !== EditorStatus.Command) {
                KeyHandler.mode(EditorStatus.Command)
            }

            //将esc传递到IDE
            KeyHandler.executeAction("EditorEscape", anActionEvent.dataContext)
            KeyHandler.toChar = null

            CommandStatus.reset()
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

}
