package com.codingbaby.ohmyidea.action;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.psi.impl.source.tree.injected.InjectedLanguageUtil;

public class MotionRightAction extends EditorAction {

    public MotionRightAction() {

        super(new EditorActionHandler() {
            @Override
            public void execute(Editor editor, DataContext dataContext) {
                editor = InjectedLanguageUtil.getTopLevelEditor(editor);
                int oldOffset = editor.getCaretModel().getOffset();
                editor.getCaretModel().moveToOffset(oldOffset + 1);
            }

        });
    }
}
