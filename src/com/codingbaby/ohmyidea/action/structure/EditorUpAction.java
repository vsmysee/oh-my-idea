package com.codingbaby.ohmyidea.action.structure;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;

/**
 *
 *
 */
public class EditorUpAction extends EditorAction {

    public EditorUpAction() {
        super(new EditorActionHandler() {
            @Override
            public void execute(Editor editor, DataContext dataContext) {
                final AnAction nextTab = ActionManager.getInstance().getAction("MethodUp");
                final AnActionEvent e = new AnActionEvent(null, dataContext, "", new Presentation(), ActionManager.getInstance(), 0);
                nextTab.actionPerformed(e);
            }

        });
    }
}