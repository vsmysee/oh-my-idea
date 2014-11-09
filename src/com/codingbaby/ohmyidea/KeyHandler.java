package com.codingbaby.ohmyidea;

import com.codingbaby.ohmyidea.helper.RunnableHelper;
import com.codingbaby.ohmyidea.key.CommandNode;
import com.codingbaby.ohmyidea.script.CodeQuick;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class KeyHandler {

    private static KeyHandler instance;

    private Map<KeyStroke, CommandNode> keyStrokeCommandNodeMap = new HashMap();
    private Map<String, CommandNode> stringShortCommandNodeMap = new HashMap();

    {
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('h'), new CommandNode("EditorLeft"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('H'), new CommandNode("EditorLineStart"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('l'), new CommandNode("EditorRight"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('L'), new CommandNode("EditorLineEnd"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('j'), new CommandNode("EditorDown"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('J'), new CommandNode("MotionLastLine"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('t'), new CommandNode("NextTab"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('T'), new CommandNode("PreviousTab"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('s'), new CommandNode("NextSplitter"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('S'), new CommandNode("PrevSplitter"));


        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('k'), new CommandNode("EditorUp"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('K'), new CommandNode("MotionFirstLine"));


        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('m'), new CommandNode("MethodDown"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('M'), new CommandNode("MethodUp"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('w'), new CommandNode("EditorNextWord"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('W'), new CommandNode("EditorPreviousWord"));


        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('b'), new CommandNode("JumpToLastChange"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('B'), new CommandNode("Back"));


        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('u'), new CommandNode("$Undo"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('r'), new CommandNode("$Redo"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('x'), new CommandNode("$Delete"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('o'), new CommandNode("EditorStartNewLine"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('c'), new CommandNode("CloseContent"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('p'), new CommandNode("$Paste"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('e'), new CommandNode("EditorSelectWord"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('E'), new CommandNode("EditorUnSelectWord"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('q'), new CommandNode("FindUsages"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('f'), new CommandNode("FindWordAtCaret"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('n'), new CommandNode("FindNext"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('N'), new CommandNode("FindPrevious"));


        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('a'), new CommandNode("MotionAndInsert"));

    }

    {
        stringShortCommandNodeMap.put("gd", new CommandNode("GotoDeclaration"));
        stringShortCommandNodeMap.put("gm", new CommandNode("GotoImplementation"));
        stringShortCommandNodeMap.put("gu", new CommandNode("GotoSuperMethod"));
        stringShortCommandNodeMap.put("gen", new CommandNode("Generate"));
        stringShortCommandNodeMap.put("gv", new CommandNode("IntroduceVariable"));

        stringShortCommandNodeMap.put("yy", new CommandNode("$Copy"));
        stringShortCommandNodeMap.put("yc", new CommandNode("$Cut"));
        stringShortCommandNodeMap.put("dd", new CommandNode("EditorDeleteLine"));

    }

    @NotNull
    public static KeyHandler getInstance() {
        if (instance == null) {
            instance = new KeyHandler();
        }
        return instance;
    }

    public void handleKey(@NotNull final Editor editor, @NotNull KeyStroke key, @NotNull final DataContext context) {

        if (KeyStroke.getKeyStroke('i') == key || KeyStroke.getKeyStroke('I') == key) {
            toInsertMod();
            return;
        }

        final OhPlugin oh = OhPlugin.getInstance();

        oh.commandStatus.addChar(key.getKeyChar());

        CommandNode commandNode;
        if (oh.commandStatus.hasStroke()) {
            commandNode = keyStrokeCommandNodeMap.get(oh.commandStatus.getStroke());
        } else {
            commandNode = stringShortCommandNodeMap.get(oh.commandStatus.getCommand());
        }

        if (commandNode != null) {
            Project project = editor.getProject();
            if (ApplicationManager.getApplication().isDispatchThread()) {
                Runnable action = new ActionRunner(context, commandNode);
                String name = commandNode.getAction().getTemplatePresentation().getText();
                RunnableHelper.runReadCommand(project, action, name, action);
            }
            oh.commandStatus.reset();
            return;
        }

        if (oh.commandStatus.getCodeKey() != null) {
            Project project = editor.getProject();
            final String mapping = CodeQuick.getMapping(oh.commandStatus.getCodeKey());
            if (mapping != null) {
                Runnable cmd = new Runnable() {
                    @Override
                    public void run() {
                        int oldOffset = editor.getCaretModel().getOffset();
                        editor.getDocument().insertString(oldOffset, mapping);
                        oh.commandStatus.reset();

                        executeAction("ReformatCode",context);
                    }
                };
                RunnableHelper.runWriteCommand(project, cmd, "insertCode", cmd);
            }
        }
    }

    public static void toInsertMod() {
        final OhPlugin oh = OhPlugin.getInstance();
        oh.status = EditorStatus.Insert;
        oh.setCursors(false);
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

    static class ActionRunner implements Runnable {
        public ActionRunner(DataContext context, CommandNode cmd) {
            this.context = context;
            this.cmd = cmd;
        }

        public void run() {
            executeAction(cmd.getAction(), context);
        }

        private final DataContext context;
        private final CommandNode cmd;
    }


}
