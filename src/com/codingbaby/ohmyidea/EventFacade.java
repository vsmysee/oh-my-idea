package com.codingbaby.ohmyidea;

import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
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


}
