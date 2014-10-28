package com.codingbaby.ohmyidea;


import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.editor.event.EditorFactoryAdapter;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import org.jetbrains.annotations.NotNull;

public class OhPlugin implements ApplicationComponent {

    private static final String COMPONENT_NAME = "Oh My IDEA";

    private boolean enabled = true;

    private final Application myApp;

    public OhPlugin(Application application) {
        this.myApp = application;
    }

    @Override
    public void initComponent() {

        final TypedAction typedAction = EditorActionManager.getInstance().getTypedAction();
        EventFacade eventFacade = EventFacade.getInstance();
        eventFacade.setupTypedActionHandler(new OhTypedActionHandler(typedAction.getHandler()));

        eventFacade.addEditorFactoryListener(new EditorFactoryAdapter() {
            @Override
            public void editorCreated(@NotNull EditorFactoryEvent event) {
                final Editor editor = event.getEditor();
                if (OhPlugin.isEnabled()) {
                    editor.getSettings().setBlockCursor(true);
                }
            }

            @Override
            public void editorReleased(@NotNull EditorFactoryEvent event) {
            }
        }, myApp);
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