package com.codingbaby.ohmyidea;


import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import org.jetbrains.annotations.NotNull;

public class OhPlugin implements ApplicationComponent {

    private static final String COMPONENT_NAME = "Oh My IDEA";

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
        return COMPONENT_NAME;
    }


    @NotNull
    private static OhPlugin getInstance() {
        return (OhPlugin) ApplicationManager.getApplication().getComponent(COMPONENT_NAME);
    }

    public static boolean isEnabled() {
        return getInstance().enabled;
    }
}