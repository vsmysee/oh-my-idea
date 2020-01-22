package com.codingbaby.ohmyidea;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.ActionPlan;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.editor.actionSystem.TypedActionHandlerEx;
import fun.codecode.EditorStatus;
import fun.codecode.KeyHandler;
import fun.codecode.PluginStatus;
import org.jetbrains.annotations.NotNull;

public class MyRawTypedHandler implements TypedActionHandlerEx {

    private final TypedActionHandler myDelegate;

    public MyRawTypedHandler(TypedActionHandler delegate) {
        myDelegate = delegate;
    }

    @Override
    public void beforeExecute(@NotNull Editor editor, char c, @NotNull DataContext context, @NotNull ActionPlan plan) {
        if (myDelegate instanceof TypedActionHandlerEx)
            ((TypedActionHandlerEx) myDelegate).beforeExecute(editor, c, context, plan);
    }


    @Override
    public void execute(@NotNull Editor editor, char charTyped, @NotNull DataContext dataContext) {

        if (!editor.getDocument().isWritable()) {

            PluginStatus.INSTANCE.mode(EditorStatus.Command);

            ApplicationManager.getApplication().runReadAction(() -> {
                KeyHandler.INSTANCE.handleKey(editor, charTyped, dataContext);
            });

            return;
        }

        myDelegate.execute(editor, charTyped, dataContext);

    }
}
