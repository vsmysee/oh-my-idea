package com.codingbaby.ohmyidea.action;

import com.codingbaby.ohmyidea.helper.EditorHelper;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.psi.impl.source.tree.injected.InjectedLanguageUtil;

public class MotionLeftAction extends EditorAction {

    public MotionLeftAction() {

        super(new EditorActionHandler() {
            @Override
            public void execute(Editor editor, DataContext dataContext) {
                final AnAction anAction = ActionManager.getInstance().getAction("EditorLeft");
                final AnActionEvent e = new AnActionEvent(null, dataContext, "", new Presentation(), ActionManager.getInstance(), 0);
                anAction.actionPerformed(e);
            }

        });
    }
}
