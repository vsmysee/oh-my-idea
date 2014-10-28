package com.codingbaby.ohmyidea.action;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;

/**
 *
 *
 */
public class MotionDownAction extends EditorAction {

    public MotionDownAction() {

        super(new EditorActionHandler() {
            @Override
            public void execute(Editor editor, DataContext dataContext) {
                VisualPosition pos = editor.getCaretModel().getVisualPosition();
                LogicalPosition logicalPosition = editor.visualToLogicalPosition(new VisualPosition(pos.getLine() + 1, pos.getColumn()));
                int offset = editor.logicalPositionToOffset(logicalPosition);
                editor.getCaretModel().moveToOffset(offset);
            }

        });
    }
}
