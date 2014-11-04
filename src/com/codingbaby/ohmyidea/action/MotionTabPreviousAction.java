package com.codingbaby.ohmyidea.action;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;

/**
 *
 *
 */
public class MotionTabPreviousAction extends EditorAction {

    public MotionTabPreviousAction() {

        super(new EditorActionHandler() {
            @Override
            public void execute(Editor editor, DataContext dataContext) {
                final AnAction anAction = ActionManager.getInstance().getAction("PreviousTab");
                final AnActionEvent e = new AnActionEvent(null, dataContext, "", new Presentation(), ActionManager.getInstance(), 0);
                anAction.actionPerformed(e);
            }
        });
    }

}
