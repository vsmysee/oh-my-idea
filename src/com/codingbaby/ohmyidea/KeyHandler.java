package com.codingbaby.ohmyidea;

import com.codingbaby.ohmyidea.helper.RunnableHelper;
import com.codingbaby.ohmyidea.key.*;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 */
public class KeyHandler {

    private static KeyHandler instance;

    public static int toLine;

    public static Character toChar;

    @NotNull
    public static KeyHandler getInstance() {
        if (instance == null) {
            instance = new KeyHandler();
        }
        return instance;
    }

    public void handleKey(@NotNull final Editor editor, @NotNull KeyStroke key, @NotNull final DataContext context) {

        final OhPlugin oh = OhPlugin.getInstance();

        if (oh.commandStatus.isWaiting()) {

            if (key.getKeyChar() == ' ') {
                toInsertMod();
                return;
            }

            if (key.getKeyChar() == 'i') {
                toInsertMod();
                return;
            }

            if (key.getKeyChar() == 'v') {
                toVisualMod();
                return;
            }

            if (key.getKeyChar() == 'V') {
                toMoveMod();
                return;
            }

        }

        oh.commandStatus.addChar(key.getKeyChar());

        CommandNode commandNode = null;
        boolean composeCommand = false;

        if (oh.commandStatus.hasStroke()) {
            if (oh.status == EditorStatus.Command) {
                commandNode = SingleShort.get(oh.commandStatus.getStroke());
            }
            if (oh.status == EditorStatus.Visual) {
                commandNode = VisualShort.get(oh.commandStatus.getStroke());
            }
            if (oh.status == EditorStatus.Move) {
                commandNode = MoveShort.get(oh.commandStatus.getStroke());
            }
        } else {
            commandNode = ComposeShort.get(oh.commandStatus.getCommand());
            if (commandNode != null) {
                composeCommand = true;
            }
        }

        //快捷模式
        if (commandNode != null) {

            KeyHandler.executeAction(commandNode.getAction(), context);
            oh.commandStatus.reset();

            //如果是组合命令，执行完回到命令模式
            if (composeCommand && oh.status != EditorStatus.Command) {
                KeyHandler.toCommandMod();
            }

            return;
        }


        //行字符搜索
        if (oh.commandStatus.isForward()) {

            if (oh.commandStatus.getForwardChar() != null) {
                toChar = oh.commandStatus.getForwardChar();
            }

            if (toChar == null) {
                return;
            }

            Project project = editor.getProject();
            Runnable cmd = new Runnable() {
                @Override
                public void run() {
                    executeAction("MotionToMatchChar", context);
                }
            };
            RunnableHelper.runReadCommand(project, cmd, "moveCharInLine", cmd);
            oh.commandStatus.reset();
            return;
        }

        //可以在单字母命令敲入数字加速
        NumberAction numberAction = oh.commandStatus.getNumberAction();
        if (numberAction != null) {
            int count = numberAction.getCount();
            for (int i = 1; i <= count; i++) {
                KeyHandler.executeAction(SingleShort.get(KeyStroke.getKeyStroke(numberAction.getKey())).getAction(), context);
            }
            oh.commandStatus.reset();
            return;
        }


        if (oh.commandStatus.getLastChar() != null) {
            KeyHandler.executeAction(SingleShort.get(KeyStroke.getKeyStroke(oh.commandStatus.getLastChar())).getAction(), context);
        }
    }

    public static void toInsertMod() {
        final OhPlugin oh = OhPlugin.getInstance();
        oh.status = EditorStatus.Insert;
        oh.setCursors(false);
        oh.commandStatus.reset();
    }

    public static void toVisualMod() {
        final OhPlugin oh = OhPlugin.getInstance();
        oh.status = EditorStatus.Visual;
        oh.commandStatus.reset();
    }

    public static void toMoveMod() {
        final OhPlugin oh = OhPlugin.getInstance();
        oh.status = EditorStatus.Move;
        oh.commandStatus.reset();
    }

    public static void toCommandMod() {
        final OhPlugin oh = OhPlugin.getInstance();
        oh.getInstance().setCursors(true);
        oh.getInstance().status = EditorStatus.Command;
        oh.commandStatus.reset();
    }

    public static void executeAction(@NotNull String name, @NotNull DataContext context) {
        ActionManager aMgr = ActionManager.getInstance();
        AnAction action = aMgr.getAction(name);
        if (action != null) {
            executeAction(action, context);
        }
    }

    public static void executeAction(@NotNull AnAction action, @NotNull DataContext context) {
        action.actionPerformed(new AnActionEvent(null, context, "", action.getTemplatePresentation(), ActionManager.getInstance(), 0));
    }

}
