package com.codingbaby.ohmyidea.action;

import com.codingbaby.ohmyidea.KeyHandler;
import com.codingbaby.ohmyidea.helper.EditorHelper;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;

/**
 *
 *
 */
public class MotionToMatchCharAction extends EditorAction {

    public MotionToMatchCharAction() {

        super(new EditorActionHandler() {
            @Override
            public void execute(Editor editor, DataContext dataContext) {
                int offset = EditorHelper.findNextCharacterOnLine(editor, KeyHandler.toChar);
                editor.getCaretModel().moveToOffset(offset);
                EditorHelper.scrollCaretIntoView(editor);
            }
        });
    }
}
