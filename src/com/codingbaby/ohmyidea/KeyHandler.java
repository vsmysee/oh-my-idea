package com.codingbaby.ohmyidea;

import com.codingbaby.ohmyidea.helper.RunnableHelper;
import com.codingbaby.ohmyidea.key.CommandNode;
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
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('B'), new CommandNode("JumpToNextChange"));


        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('u'), new CommandNode("$Undo"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('r'), new CommandNode("$Redo"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('x'), new CommandNode("$Delete"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('o'), new CommandNode("EditorStartNewLine"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('c'), new CommandNode("CloseContent"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('p'), new CommandNode("$Paste"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('e'), new CommandNode("EditorSelectWord"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('q'), new CommandNode("FindUsages"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('f'), new CommandNode("FindWordAtCaret"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('n'), new CommandNode("FindNext"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('N'), new CommandNode("FindPrevious"));

    }

    {
        stringShortCommandNodeMap.put("gd", new CommandNode("GotoDeclaration"));
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

    public void handleKey(@NotNull Editor editor, @NotNull KeyStroke key, @NotNull DataContext context) {
        OhPlugin oh = OhPlugin.getInstance();

        if (KeyStroke.getKeyStroke('i') == key) {
            oh.status = EditorStatus.Insert;
            oh.setCursors(false);
            oh.commandStatus.reset();
            return;
        }

        oh.commandStatus.addChar(key.getKeyChar());

        CommandNode commandNode;
        if (oh.commandStatus.hasStroke()) {
            commandNode = keyStrokeCommandNodeMap.get(oh.commandStatus.getStroke());
        } else {
            commandNode = stringShortCommandNodeMap.get(oh.commandStatus.getCommand());
        }

        if (commandNode == null) {
            oh.commandStatus.reset();
            return;
        }
        Project project = editor.getProject();
        if (ApplicationManager.getApplication().isDispatchThread()) {
            Runnable action = new ActionRunner(editor, context, commandNode, key);
            String name = commandNode.getAction().getTemplatePresentation().getText();
            RunnableHelper.runReadCommand(project, action, name, action);
        }

        oh.commandStatus.reset();
    }


    public static void executeAction(@NotNull AnAction action, @NotNull DataContext context) {
        action.actionPerformed(new AnActionEvent(null, context, "", action.getTemplatePresentation(), ActionManager.getInstance(), 0));
    }

    static class ActionRunner implements Runnable {
        public ActionRunner(Editor editor, DataContext context, CommandNode cmd, KeyStroke key) {
            this.editor = editor;
            this.context = context;
            this.cmd = cmd;
            this.key = key;
        }

        public void run() {
            executeAction(cmd.getAction(), context);
        }

        private final Editor editor;
        private final DataContext context;
        private final CommandNode cmd;
        private final KeyStroke key;
    }


}
