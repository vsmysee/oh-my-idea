package com.codingbaby.ohmyidea.action;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;

/**
 *
 *
 */
public class MotionGotoLineLastAction extends EditorAction {

    public MotionGotoLineLastAction() {
        super(new EditorActionHandler() {
            @Override
            public void execute(Editor editor, DataContext dataContext) {
                final int len = editor.getDocument().getTextLength();
                editor.getCaretModel().moveToOffset(len);
            }
        });
    }
}
