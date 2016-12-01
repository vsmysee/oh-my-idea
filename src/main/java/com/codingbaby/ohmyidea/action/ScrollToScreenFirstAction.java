package com.codingbaby.ohmyidea.action;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.codingbaby.ohmyidea.helper.EditorHelper;


public class ScrollToScreenFirstAction extends EditorAction {

    public ScrollToScreenFirstAction(){
        super(new EditorActionHandler() {
            @Override
            protected void doExecute(Editor editor,  Caret caret, DataContext dataContext) {
                EditorHelper.scrollLineToScreenLine(editor);
            }
        });
    }
}
