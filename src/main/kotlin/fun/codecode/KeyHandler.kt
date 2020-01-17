package `fun`.codecode

import com.codingbaby.ohmyidea.CommandNode
import com.codingbaby.ohmyidea.CommandStatus
import com.codingbaby.ohmyidea.EditorStatus
import com.codingbaby.ohmyidea.Oh
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


    val statusMap = hashMapOf(' ' to EditorStatus.Insert,
            'i' to EditorStatus.Insert,
            'a' to EditorStatus.Action,
            'V' to EditorStatus.Move,
            'v' to EditorStatus.Select)

    fun mode(status: EditorStatus?) {
        if (status == null) {
            return
        }
        val oh = Oh.get()
        oh.status = status

        oh.setCursors(status != EditorStatus.Insert)
        CommandStatus.reset()
    }


    fun handleKey(editor: Editor, charTyped: Char, context: DataContext) {

        val oh = Oh.get()

        if (CommandStatus.isWaiting) {
            if (statusMap[charTyped] != null) {
                mode(statusMap[charTyped])
                return
            }
        }

        CommandStatus.addChar(charTyped)

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

        //组合模式
        if (commandNode != null) {

            executeAction(commandNode.asAction(), context)
            CommandStatus.reset()

            //如果是组合命令，执行完回到命令模式
            if (composeCommand && oh.status !== EditorStatus.Command) {
                mode(EditorStatus.Command)
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



        if (CommandStatus.lastChar() != null) {
            val keyStroke = KeyStroke.getKeyStroke(CommandStatus.lastChar()!!)
            val cn = ShortHolder.single[keyStroke]
            if (cn == null) {
                CommandStatus.reset()
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