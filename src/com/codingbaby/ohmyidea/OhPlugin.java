package com.codingbaby.ohmyidea;


import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import org.jetbrains.annotations.NotNull;

public class OhPlugin implements ApplicationComponent {

    private boolean enabled = true;

    @Override
    public void initComponent() {
        final TypedAction typedAction = EditorActionManager.getInstance().getTypedAction();
        EventFacade.getInstance().setupTypedActionHandler(new OhTypedActionHandler(typedAction.getHandler()));
    }

    @Override
    public void disposeComponent() {
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "Oh My IDEA";
    }


    @NotNull
    private static OhPlugin getInstance() {
        return (OhPlugin) ApplicationManager.getApplication().getComponent("Oh My IDEA");
    }

    public static boolean isEnabled() {
        return getInstance().enabled;
    }
}