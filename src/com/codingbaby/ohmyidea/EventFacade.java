package com.codingbaby.ohmyidea;

import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 */
public class EventFacade {

    @NotNull
    private static final EventFacade ourInstance = new EventFacade();

    @Nullable
    private TypedActionHandler myOriginalTypedActionHandler;

    private EventFacade() {
    }

    @NotNull
    public static EventFacade getInstance() {
        return ourInstance;
    }


    public void setupTypedActionHandler(@NotNull TypedActionHandler handler) {
        final TypedAction typedAction = getTypedAction();
        myOriginalTypedActionHandler = typedAction.getHandler();
        typedAction.setupHandler(handler);
    }

    @NotNull
    private TypedAction getTypedAction() {
        return EditorActionManager.getInstance().getTypedAction();
    }


}
