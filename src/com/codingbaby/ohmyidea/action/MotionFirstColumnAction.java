package com.codingbaby.ohmyidea.action;

import com.codingbaby.ohmyidea.helper.EditorHelper;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;

/**
 *
 *
 */
public class MotionFirstColumnAction extends EditorAction {

    public MotionFirstColumnAction() {
        super(new EditorActionHandler() {
            @Override
            public void execute(Editor editor, DataContext dataContext) {
                editor.getCaretModel().moveToOffset(EditorHelper.moveCaretToLineStart(editor));
                EditorHelper.scrollCaretIntoView(editor);
            }
        });
    }
}
