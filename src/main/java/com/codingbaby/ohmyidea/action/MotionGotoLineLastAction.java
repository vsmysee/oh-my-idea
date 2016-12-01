package com.codingbaby.ohmyidea.action;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.codingbaby.ohmyidea.helper.EditorHelper;


public class MotionGotoLineLastAction extends EditorAction {

    public MotionGotoLineLastAction() {
        super(new EditorActionHandler() {
            @Override
            protected void doExecute(Editor editor,  Caret caret, DataContext dataContext) {
                int len = editor.getDocument().getTextLength();
                if (editor.getDocument().getCharsSequence().charAt(editor.getDocument().getTextLength() - 1) == '\n') {
                    len = len -1;
                }
                editor.getCaretModel().moveToOffset(len);
                EditorHelper.scrollCaretIntoView(editor);
            }
        });
    }
}
