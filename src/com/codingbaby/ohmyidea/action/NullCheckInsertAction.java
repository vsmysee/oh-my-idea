package com.codingbaby.ohmyidea.action;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import org.jetbrains.annotations.Nullable;

/**
 *
 *
 */

public class NullCheckInsertAction extends EditorAction {

    public NullCheckInsertAction() {

        super(new EditorActionHandler() {
            @Override
            protected void doExecute(Editor editor, @Nullable Caret caret, DataContext dataContext) {
                int oldOffset = editor.getCaretModel().getOffset();
                editor.getDocument().insertString(oldOffset, " == null");
            }

        });

    }
}

