package com.codingbaby.ohmyidea

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actions.EscapeAction
import com.codingbaby.ohmyidea.action.RepeatCurrentAction
import com.codingbaby.ohmyidea.helper.RunnableHelper
import com.codingbaby.ohmyidea.key.*
import com.codingbaby.ohmyidea.script.RobotHandler
import com.codingbaby.ohmyidea.ui.RobtHolder
import java.awt.event.KeyEvent


import javax.swing.*


class KeyHandler {

    fun handleKey(editor: Editor, key: KeyStroke, context: DataContext) {

        val oh = OhPlugin.instance

        if (oh.commandStatus.isWaiting) {

            if (key.keyChar == ' ') {
                toInsertMod()
                return
            }

            if (key.keyChar == 'i') {
                toInsertMod()
                return
            }

            if (key.keyChar == 'v') {
                toVisualMod()
                return
            }

            if (key.keyChar == 'V') {
                toMoveMod()
                return
            }

            if (key.keyChar == 'a') {
                toActionMod()
                return
            }


        }

        oh.commandStatus.addChar(key.keyChar)

        var commandNode: CommandNode? = null
        var composeCommand = false

        if (oh.commandStatus.hasStroke()) {
            if (oh.status === EditorStatus.Command) {
                commandNode = SingleShort[oh.commandStatus.stroke]
            }
            if (oh.status === EditorStatus.Visual) {
                commandNode = VisualShort[oh.commandStatus.stroke]
            }
            if (oh.status === EditorStatus.Move) {
                commandNode = MoveShort[oh.commandStatus.stroke]
            }

            if (oh.status === EditorStatus.Action) {
                //最后理解为快捷键映射
                var events = RobotHandler.holder[oh.commandStatus.fistChar().toString()]
                Thread.sleep(1000)

                if (events != null) {
                    for (i in events) {
                        RobtHolder.robot.keyPress(i)
                    }
                    for (i in events) {
                        RobtHolder.robot.keyRelease(i)
                    }
                }

                //todo groovy support outer action

                //VisualShort.commandHolder.add("f", "AceJumpAction", "ACE模式")
                //VisualShort.commandHolder.add("a", "AceJumpLineAction", "ACE快速模式")
            }

        } else {
            commandNode = ComposeShort[oh.commandStatus.command]
            if (commandNode != null) {
                composeCommand = true
            }
        }

        //快捷模式
        if (commandNode != null) {

            KeyHandler.executeAction(commandNode.action, context)
            oh.commandStatus.reset()

            //如果是组合命令，执行完回到命令模式
            if (composeCommand && oh.status !== EditorStatus.Command) {
                KeyHandler.toCommandMod()
            }

            return
        }


        //行字符搜索
        if (oh.commandStatus.isForward) {

            if (oh.commandStatus.forwardChar != null) {
                toChar = oh.commandStatus.forwardChar
            }

            if (toChar == null) {
                return
            }

            val project = editor.project
            val cmd = Runnable { executeAction("MotionToMatchChar", context) }
            RunnableHelper.runReadCommand(project!!, cmd, "moveCharInLine", cmd)
            oh.commandStatus.reset()
            return
        }

        //可以在单字母命令敲入数字加速
        val numberAction = oh.commandStatus.numberAction
        if (numberAction != null) {
            val count = numberAction.count
            for (i in 1..count) {
                KeyHandler.executeAction(SingleShort[KeyStroke.getKeyStroke(numberAction.key)]!!.action, context)
            }
            oh.commandStatus.reset()
            return
        }


        if (oh.commandStatus.lastChar != null) {
            KeyHandler.executeAction(SingleShort[KeyStroke.getKeyStroke(oh.commandStatus.lastChar!!)]!!.action, context)
        }
    }

    companion object {

        private var keyHander: KeyHandler = KeyHandler()

        var toLine: Int = 0

        var toChar: Char? = null

        private var currentAction: AnAction? = null

        private var currentDataContext: DataContext? = null


        fun ohMyHander(): KeyHandler {
            return keyHander
        }

        fun toInsertMod() {
            val oh = OhPlugin.instance
            oh.status = EditorStatus.Insert
            oh.setCursors(false)
            oh.commandStatus.reset()
        }

        fun toVisualMod() {
            val oh = OhPlugin.instance
            oh.status = EditorStatus.Visual
            oh.commandStatus.reset()
        }

        fun toMoveMod() {
            val oh = OhPlugin.instance
            oh.status = EditorStatus.Move
            oh.commandStatus.reset()
        }

        fun toActionMod() {
            val oh = OhPlugin.instance
            oh.status = EditorStatus.Action
            oh.commandStatus.reset()
        }


        fun toCommandMod() {
            val oh = OhPlugin.instance
            oh.setCursors(true)
            oh.status = EditorStatus.Command
            oh.commandStatus.reset()
        }

        fun executeAction(name: String, context: DataContext) {
            val aMgr = ActionManager.getInstance()
            val action = aMgr.getAction(name)
            if (action != null) {
                executeAction(action, context)
            }
        }

        fun executeAction(action: AnAction, context: DataContext) {
            if (action !is EscapeAction && action !is RepeatCurrentAction) {
                currentAction = action
                currentDataContext = context
            }
            action.actionPerformed(AnActionEvent(null, context, "", action.templatePresentation, ActionManager.getInstance(), 0))
        }

        fun repeatCurrentAction() {
            if (currentAction != null && currentDataContext != null) {
                currentAction!!.actionPerformed(AnActionEvent(null, currentDataContext!!, "", currentAction!!.templatePresentation, ActionManager.getInstance(), 0))

            }
        }
    }
}
