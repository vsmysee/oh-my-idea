package com.codingbaby.ohmyidea.action.ui;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.codingbaby.ohmyidea.ui.ExEntryPanel;
import org.jetbrains.annotations.Nullable;

/**
 * Created by baby on 15/3/7.
 */
public class ShowCommandAction extends EditorAction {

    public ShowCommandAction() {

        super(new EditorActionHandler() {

            @Override
            protected void doExecute(Editor editor, @Nullable Caret caret, DataContext dataContext) {
                ExEntryPanel panel = ExEntryPanel.getInstance();
                panel.activate(editor, dataContext, ":", "", 1);
            }
        });
    }
}
