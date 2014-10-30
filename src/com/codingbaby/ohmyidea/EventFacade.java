package com.codingbaby.ohmyidea;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class EventFacade {

    @NotNull
    private static final EventFacade ourInstance = new EventFacade();

    private EventFacade() {
    }

    @NotNull
    public static EventFacade getInstance() {
        return ourInstance;
    }

    public void setupTypedActionHandler(@NotNull TypedActionHandler handler) {
        final TypedAction typedAction = EditorActionManager.getInstance().getTypedAction();
        typedAction.setupHandler(handler);
    }

    public void addEditorFactoryListener(@NotNull EditorFactoryListener listener, @NotNull Disposable parentDisposable) {
        EditorFactory.getInstance().addEditorFactoryListener(listener, parentDisposable);
    }



}
