package com.codingbaby.ohmyidea.action;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.codingbaby.ohmyidea.helper.EditorHelper;
import org.jetbrains.annotations.Nullable;

/**
 *
 *
 */
public class ScrollToScreenFirstAction extends EditorAction {

    public ScrollToScreenFirstAction(){
        super(new EditorActionHandler() {
            @Override
            protected void doExecute(Editor editor, @Nullable Caret caret, DataContext dataContext) {
                EditorHelper.scrollLineToScreenLine(editor);
            }
        });
    }
}
