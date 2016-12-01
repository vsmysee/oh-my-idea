package com.codingbaby.ohmyidea.action;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.codingbaby.ohmyidea.KeyHandler;
import com.codingbaby.ohmyidea.helper.EditorHelper;


public class MotionToMatchCharAction extends EditorAction {

    public MotionToMatchCharAction() {

        super(new EditorActionHandler() {
            @Override
            protected void doExecute(Editor editor,  Caret caret, DataContext dataContext) {
                int offset = EditorHelper.findNextCharacterOnLine(editor, KeyHandler.toChar);
                editor.getCaretModel().moveToOffset(offset);
                EditorHelper.scrollCaretIntoView(editor);
            }
        });
    }
}
