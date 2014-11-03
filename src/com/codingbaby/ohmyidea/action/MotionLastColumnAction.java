package com.codingbaby.ohmyidea.action;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;

/**
 *
 *
 */
public class MotionLastColumnAction extends EditorAction {

    public MotionLastColumnAction() {

        super(new EditorActionHandler() {
            @Override
            public void execute(Editor editor, DataContext dataContext) {

            }
        });
    }
}
