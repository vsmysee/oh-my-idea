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

    {
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('h'), new CommandNode("MotionLeft"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('l'), new CommandNode("MotionRight"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('j'), new CommandNode("MotionDown"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('k'), new CommandNode("MotionUp"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('t'), new CommandNode("MotionNextTab"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('T'), new CommandNode("MotionPreviousTab"));


        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('K'), new CommandNode("MotionFirstLine"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('J'), new CommandNode("MotionLastLine"));
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
            oh.status = CommandStatus.Insert;
            oh.setCursors(false);
            return;
        }

        CommandNode commandNode = keyStrokeCommandNodeMap.get(key);
        if (commandNode == null) {
            return;
        }
        Project project = editor.getProject();
        if (ApplicationManager.getApplication().isDispatchThread()) {
            Runnable action = new ActionRunner(editor, context, commandNode, key);
            String name = commandNode.getAction().getTemplatePresentation().getText();
            RunnableHelper.runReadCommand(project, action, name, action);
        }
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
