package com.codingbaby.ohmyidea

import `fun`.codecode.OhPlugin
import com.codingbaby.ohmyidea.helper.RunnableHelper
import com.codingbaby.ohmyidea.script.RobotHandler
import com.codingbaby.ohmyidea.script.ShortHolder
import com.codingbaby.ohmyidea.ui.RobtHolder
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.popup.JBPopupFactory
import javax.swing.KeyStroke
import javax.swing.SwingUtilities


object KeyHandler {

    var toLine: Int = 0

    var toChar: Char? = null


    val statusMap = hashMapOf(' ' to EditorStatus.Insert,
            'i' to EditorStatus.Insert,
            'a' to EditorStatus.Action,
            'V' to EditorStatus.Move,
            'v' to EditorStatus.Select)

    fun mode(status: EditorStatus?) {
        if (status == null) {
            return
        }
        val oh = OhPlugin.instance
        oh.status = status

        oh.setCursors(status != EditorStatus.Insert)
        CommandStatus.reset()
    }


    fun handleKey(editor: Editor, key: KeyStroke, context: DataContext) {

        val oh = OhPlugin.instance

        if (CommandStatus.isWaiting) {
            if (statusMap[key.keyChar] != null) {
                mode(statusMap[key.keyChar])
                return
            }
        }

        CommandStatus.addChar(key.keyChar)

        var commandNode: CommandNode? = null
        var composeCommand = false


        //single char
        if (CommandStatus.hasStroke()) {
            if (oh.status === EditorStatus.Command) {
                commandNode = ShortHolder.single[CommandStatus.stroke()]
            }
            if (oh.status === EditorStatus.Select) {
                commandNode = ShortHolder.select[CommandStatus.stroke()]
            }
            if (oh.status === EditorStatus.Move) {
                commandNode = ShortHolder.movement[CommandStatus.stroke()]
            }

            if (oh.status === EditorStatus.Action) {
                //最后理解为快捷键映射
                var events = RobotHandler.holder[CommandStatus.fistChar().toString()]
                Thread.sleep(500)

                if (events != null) {
                    for (i in events) {
                        RobtHolder.robot.keyPress(i)
                    }
                    for (i in events) {
                        RobtHolder.robot.keyRelease(i)
                    }
                }
            }

        } else {
            commandNode = ShortHolder.compose[CommandStatus.command()]
            if (commandNode != null) {
                composeCommand = true
            }
        }

        //快捷模式
        if (commandNode != null) {

            KeyHandler.executeAction(commandNode.asAction(), context)
            CommandStatus.reset()

            //如果是组合命令，执行完回到命令模式
            if (composeCommand && oh.status !== EditorStatus.Command) {
                KeyHandler.mode(EditorStatus.Command)
            }

            return
        }


        //行字符搜索
        if (CommandStatus.isForward()) {

            if (CommandStatus.forwardChar() != null) {
                toChar = CommandStatus.forwardChar()
            }

            if (toChar == null) {
                return
            }

            val project = editor.project
            val cmd = Runnable { executeAction("OH_MotionToMatchChar", context) }
            RunnableHelper.runReadCommand(project!!, cmd, "moveCharInLine", cmd)
            CommandStatus.reset()
            return
        }

        //可以在单字母命令敲入数字加速
        val numberAction = CommandStatus.numberAction()
        if (numberAction != null) {
            val count = numberAction.count
            for (i in 1..count) {
                KeyHandler.executeAction(ShortHolder.single[KeyStroke.getKeyStroke(numberAction.key)]!!.asAction(), context)
            }
            CommandStatus.reset()
            return
        }


        if (CommandStatus.lastChar() != null) {
            KeyHandler.executeAction(ShortHolder.single[KeyStroke.getKeyStroke(CommandStatus.lastChar()!!)]!!.asAction(), context)
        }
    }


    fun executeAction(name: String, context: DataContext) {
        val aMgr = ActionManager.getInstance()
        val action = aMgr.getAction(name)
        if (action != null) {
            executeAction(action, context)
        }
    }


    //copy from ideavim

    fun executeAction(action: AnAction, context: DataContext): Boolean {
        val event = AnActionEvent(null, context, ActionPlaces.ACTION_SEARCH, action.templatePresentation,
                ActionManager.getInstance(), 0)

        if (action is ActionGroup && !action.canBePerformed(context)) {
            // Some of the AcitonGroups should not be performed, but shown as a popup
            val popup = JBPopupFactory.getInstance()
                    .createActionGroupPopup(event.presentation.text, action, context, false, null, -1)

            val component = context.getData(PlatformDataKeys.CONTEXT_COMPONENT)
            if (component != null) {
                val window = SwingUtilities.getWindowAncestor(component)
                if (window != null) {
                    popup.showInCenterOf(window)
                }
                return true
            }
            popup.showInFocusCenter()
            return true
        } else {
            // beforeActionPerformedUpdate should be called to update the action. It fixes some rider-specific problems
            //   because rider use async update method. See VIM-1819
            action.beforeActionPerformedUpdate(event)
            if (event.presentation.isEnabled) {
                action.actionPerformed(event)
                return true
            }
        }
        return false
    }


}
