package com.codingbaby.ohmyidea.action.ui;

import com.codingbaby.ohmyidea.ui.ExEntryPanel;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;

/**
 * Created by baby on 15/3/7.
 */
public class ShowSearchAction extends EditorAction {

    public ShowSearchAction() {

        super(new EditorActionHandler() {

            @Override
            public void execute(Editor editor, DataContext dataContext) {
                ExEntryPanel panel = ExEntryPanel.getInstance();
                panel.activate(editor, dataContext, "/", "", 1);
            }
        });
    }
}
