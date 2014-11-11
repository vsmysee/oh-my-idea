package com.codingbaby.ohmyidea;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 *
 *
 */
public class ShortcutKeyAction extends AnAction implements DumbAware  {

    private static final String ACTION_ID = "ShortcutKeyAction";


    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        KeyStroke keyStroke = getKeyStroke(anActionEvent);
        if (keyStroke != null && keyStroke.getKeyCode() == KeyEvent.VK_ESCAPE && OhPlugin.isEnabled()) {
            if (OhPlugin.getInstance().status == EditorStatus.Insert || OhPlugin.getInstance().status == EditorStatus.Visual) {
                OhPlugin.getInstance().setCursors(true);
                OhPlugin.getInstance().status = EditorStatus.Command;
            }
            OhPlugin.getInstance().commandStatus.reset();
        }
    }


    public static AnAction getInstance() {
        return ActionManager.getInstance().getAction(ACTION_ID);
    }

    @Nullable
    private KeyStroke getKeyStroke(@NotNull AnActionEvent e) {
        final InputEvent inputEvent = e.getInputEvent();
        if (inputEvent instanceof KeyEvent) {
            final KeyEvent keyEvent = (KeyEvent)inputEvent;
            return KeyStroke.getKeyStrokeForEvent(keyEvent);
        }
        return null;
    }

}
