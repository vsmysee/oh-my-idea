package com.codingbaby.ohmyidea;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import fun.codecode.KeyHandler;
import org.jetbrains.annotations.NotNull;

public class MyRawTypedHandler extends com.intellij.openapi.editor.impl.EditorFactoryImpl.MyRawTypedHandler {

    public MyRawTypedHandler(TypedActionHandler delegate) {
        super(delegate);
    }

    @Override
    public void execute(@NotNull Editor editor, char charTyped, @NotNull DataContext dataContext) {

        if (!editor.getDocument().isWritable()) {
            KeyHandler.INSTANCE.mode(EditorStatus.Command);

            ApplicationManager.getApplication().runReadAction(() -> {
                KeyHandler.INSTANCE.handleKey(editor, charTyped, dataContext);
            });

            return;
        }

        super.execute(editor, charTyped, dataContext);
    }
}
