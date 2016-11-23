package com.codingbaby.ohmyidea;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.DumbAware;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 *
 * 绑定到编辑组件上的控制键行为
 */
public class ShortcutKeyAction extends AnAction implements DumbAware  {

    private static final String ACTION_ID = "ShortcutKeyAction";


    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

        KeyStroke keyStroke = getKeyStroke(anActionEvent);
        if (keyStroke != null && keyStroke.getKeyCode() == KeyEvent.VK_ESCAPE && OhPlugin.isEnabled()) {

            if (OhPlugin.getInstance().status != EditorStatus.Command) {
                KeyHandler.toCommandMod();
                OhPlugin.setPopWindowOpen(false);
            }

            //将esc传递到IDE
            KeyHandler.executeAction("EditorEscape",anActionEvent.getDataContext());
            KeyHandler.toChar = null;

            OhPlugin.getInstance().commandStatus.reset();
        }
    }



    @Override
    public void update(@NotNull AnActionEvent e) {
        final Editor editor =  e.getData(PlatformDataKeys.EDITOR);
        if(editor == null){
            e.getPresentation().setEnabled(false);
        }else{
            final KeyStroke keyStroke = getKeyStroke(e);
            e.getPresentation().setEnabled(keyStroke != null);
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
