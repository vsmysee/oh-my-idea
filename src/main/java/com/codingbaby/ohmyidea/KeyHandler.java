package com.codingbaby.ohmyidea;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actions.EscapeAction;
import com.intellij.openapi.project.Project;
import com.codingbaby.ohmyidea.action.RepeatCurrentAction;
import com.codingbaby.ohmyidea.helper.RunnableHelper;
import com.codingbaby.ohmyidea.key.*;


import javax.swing.*;


public class KeyHandler {

    private static KeyHandler instance;

    public static int toLine;

    public static Character toChar;

    private static AnAction currentAction;

    private static DataContext currentDataContext;


    public static KeyHandler getInstance() {
        if (instance == null) {
            instance = new KeyHandler();
        }
        return instance;
    }

    public void handleKey(final Editor editor, KeyStroke key, final DataContext context) {

        final OhPlugin oh = OhPlugin.Companion.getInstance();

        if (oh.getCommandStatus().isWaiting()) {

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

        oh.getCommandStatus().addChar(key.getKeyChar());

        CommandNode commandNode = null;
        boolean composeCommand = false;

        if (oh.getCommandStatus().hasStroke()) {
            if (oh.getStatus() == EditorStatus.Command) {
                commandNode = SingleShort.INSTANCE.get(oh.getCommandStatus().getStroke());
            }
            if (oh.getStatus() == EditorStatus.Visual) {
                commandNode = VisualShort.INSTANCE.get(oh.getCommandStatus().getStroke());
            }
            if (oh.getStatus() == EditorStatus.Move) {
                commandNode = MoveShort.INSTANCE.get(oh.getCommandStatus().getStroke());
            }
        } else {
            commandNode = ComposeShort.INSTANCE.get(oh.getCommandStatus().getCommand());
            if (commandNode != null) {
                composeCommand = true;
            }
        }

        //快捷模式
        if (commandNode != null) {

            KeyHandler.executeAction(commandNode.getAction(), context);
            oh.getCommandStatus().reset();

            //如果是组合命令，执行完回到命令模式
            if (composeCommand && oh.getStatus() != EditorStatus.Command) {
                KeyHandler.toCommandMod();
            }

            return;
        }


        //行字符搜索
        if (oh.getCommandStatus().isForward()) {

            if (oh.getCommandStatus().getForwardChar() != null) {
                toChar = oh.getCommandStatus().getForwardChar();
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
            RunnableHelper.INSTANCE.runReadCommand(project, cmd, "moveCharInLine", cmd);
            oh.getCommandStatus().reset();
            return;
        }

        //可以在单字母命令敲入数字加速
        NumberAction numberAction = oh.getCommandStatus().getNumberAction();
        if (numberAction != null) {
            int count = numberAction.getCount();
            for (int i = 1; i <= count; i++) {
                KeyHandler.executeAction(SingleShort.INSTANCE.get(KeyStroke.getKeyStroke(numberAction.getKey())).getAction(), context);
            }
            oh.getCommandStatus().reset();
            return;
        }


        if (oh.getCommandStatus().getLastChar() != null) {
            KeyHandler.executeAction(SingleShort.INSTANCE.get(KeyStroke.getKeyStroke(oh.getCommandStatus().getLastChar())).getAction(), context);
        }
    }

    public static void toInsertMod() {
        final OhPlugin oh = OhPlugin.Companion.getInstance();
        oh.setStatus(EditorStatus.Insert);
        oh.setCursors(false);
        oh.getCommandStatus().reset();
    }

    public static void toVisualMod() {
        final OhPlugin oh = OhPlugin.Companion.getInstance();
        oh.setStatus(EditorStatus.Visual);
        oh.getCommandStatus().reset();
    }

    public static void toMoveMod() {
        final OhPlugin oh = OhPlugin.Companion.getInstance();
        oh.setStatus(EditorStatus.Move);
        oh.getCommandStatus().reset();
    }

    public static void toCommandMod() {
        final OhPlugin oh = OhPlugin.Companion.getInstance();
        oh.Companion.getInstance().setCursors(true);
        oh.Companion.getInstance().setStatus(EditorStatus.Command);
        oh.getCommandStatus().reset();
    }

    public static void executeAction(String name, DataContext context) {
        ActionManager aMgr = ActionManager.getInstance();
        AnAction action = aMgr.getAction(name);
        if (action != null) {
            executeAction(action, context);
        }
    }

    public static void executeAction(AnAction action, DataContext context) {
        if (!(action instanceof EscapeAction) && !(action instanceof RepeatCurrentAction)) {
            currentAction = action;
            currentDataContext = context;
        }
        action.actionPerformed(new AnActionEvent(null, context, "", action.getTemplatePresentation(), ActionManager.getInstance(), 0));
    }

    public static void repeatCurrentAction() {
        if (currentAction != null && currentDataContext != null) {
            currentAction.actionPerformed(new AnActionEvent(null, currentDataContext, "", currentAction.getTemplatePresentation(), ActionManager.getInstance(), 0));

        }
    }
}
