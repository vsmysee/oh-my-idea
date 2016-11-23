package com.codingbaby.ohmyidea.action;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import org.jetbrains.annotations.Nullable;

/**
 * Created by baby on 16/7/9.
 */
public class DeleteToFileEndAction extends EditorAction {


    public DeleteToFileEndAction() {
        super(new EditorActionHandler() {
            @Override
            protected void doExecute(Editor editor, @Nullable Caret caret, DataContext dataContext) {
                final int startPos = editor.getCaretModel().getOffset();
                final int endPos = editor.getDocument().getTextLength();
                editor.getDocument().deleteString(startPos, endPos);
            }
        });
    }

}
