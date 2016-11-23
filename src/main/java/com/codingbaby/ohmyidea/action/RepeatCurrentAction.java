package com.codingbaby.ohmyidea.action;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.codingbaby.ohmyidea.KeyHandler;
import org.jetbrains.annotations.Nullable;

/**
 *
 *
 */

public class RepeatCurrentAction extends EditorAction {

    public RepeatCurrentAction() {

        super(new EditorActionHandler() {
            @Override
            protected void doExecute(Editor editor, @Nullable Caret caret, DataContext dataContext) {
                KeyHandler.repeatCurrentAction();
            }

        });

    }
}

