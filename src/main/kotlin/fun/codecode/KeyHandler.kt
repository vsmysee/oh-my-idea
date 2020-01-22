package `fun`.codecode

import com.codingbaby.ohmyidea.CommandNode
import com.codingbaby.ohmyidea.CommandBuffer
import com.codingbaby.ohmyidea.helper.RunnableHelper
import com.codingbaby.ohmyidea.script.RobotHandler
import com.codingbaby.ohmyidea.script.ShortHolder
import com.codingbaby.ohmyidea.ui.RobtHolder
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import javax.swing.KeyStroke


object KeyHandler {

    var toLine: Int = 0

    var toChar: Char? = null


    val statusMap = hashMapOf(
            ' ' to EditorStatus.Insert,
            'i' to EditorStatus.Insert,
            'a' to EditorStatus.Action,
            'V' to EditorStatus.Move,
            'v' to EditorStatus.Select)


    fun handleKey(editor: Editor, charTyped: Char, context: DataContext) {

        if (CommandBuffer.isWaiting) {
            val editorStatus = statusMap[charTyped]
            if (editorStatus != null) {
                PluginStatus.mode(editorStatus)
                return
            }
        }

        CommandBuffer.addChar(charTyped)

        var commandNode: CommandNode? = null
        var composeCommand = false


        var status = PluginStatus.status
        //单键
        if (CommandBuffer.hasStroke()) {
            if (status === EditorStatus.Command) {
                commandNode = ShortHolder.single[CommandBuffer.stroke()]
            }
            if (status === EditorStatus.Select) {
                commandNode = ShortHolder.select[CommandBuffer.stroke()]
            }
            if (status === EditorStatus.Move) {
                commandNode = ShortHolder.movement[CommandBuffer.stroke()]
            }

            if (status === EditorStatus.Action) {
                //最后理解为快捷键映射
                var events = RobotHandler.holder[CommandBuffer.fistChar().toString()]
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
            commandNode = ShortHolder.composite[CommandBuffer.command()]
            if (commandNode != null) {
                composeCommand = true
            }
        }

        //组合模式
        if (commandNode != null) {

            executeAction(commandNode.asAction(), context)
            CommandBuffer.reset()

            //如果是组合命令，执行完回到命令模式
            if (composeCommand && status !== EditorStatus.Command) {
                PluginStatus.mode(EditorStatus.Command)
            }

            return
        }


        //行字符搜索
        if (CommandBuffer.isForward()) {

            if (CommandBuffer.forwardChar() != null) {
                toChar = CommandBuffer.forwardChar()
            }

            if (toChar == null) {
                return
            }

            val project = editor.project
            val cmd = Runnable { executeAction("OH_MotionToMatchChar", context) }
            RunnableHelper.runReadCommand(project!!, cmd, "moveCharInLine", cmd)
            CommandBuffer.reset()
            return
        }

        //最后一个字符
        if (CommandBuffer.lastChar() != null) {
            val keyStroke = KeyStroke.getKeyStroke(CommandBuffer.lastChar()!!)
            val cn = ShortHolder.single[keyStroke]
            if (cn == null) {
                CommandBuffer.reset()
                return
            }
            var action = cn.asAction()
            executeAction(action, context)
        }
    }


    fun executeAction(name: String, context: DataContext) {
        val aMgr = ActionManager.getInstance()
        val action = aMgr.getAction(name)

        if (action != null) {
            executeAction(action, context)
        }
    }


    fun executeAction(action: AnAction?, context: DataContext) {

        if (action != null) {
            ApplicationManager.getApplication().run {
                action.actionPerformed(AnActionEvent(null, context, ActionPlaces.ACTION_SEARCH, action.templatePresentation, ActionManager.getInstance(), 0))
            }
        }

    }

}