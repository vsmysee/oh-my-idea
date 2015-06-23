package com.codingbaby.ohmyidea.action;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;

/**
 *
 *
 */

public class NotQuickInsertAction extends EditorAction {

    public NotQuickInsertAction() {

        super(new EditorActionHandler() {
            @Override
            public void execute(Editor editor, DataContext dataContext) {
                int oldOffset = editor.getCaretModel().getOffset();
                editor.getDocument().insertString(oldOffset, "!");
            }
        });
    }
}

