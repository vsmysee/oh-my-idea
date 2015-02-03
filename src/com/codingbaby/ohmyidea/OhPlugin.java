package com.codingbaby.ohmyidea;


import com.codingbaby.ohmyidea.helper.EditorHelper;
import com.codingbaby.ohmyidea.script.OhScript;
import com.intellij.openapi.actionSystem.CustomShortcutSet;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.editor.event.EditorFactoryAdapter;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class OhPlugin implements ApplicationComponent {

    private static final String COMPONENT_NAME = "Oh My IDEA";

    private boolean enabled = true;

    private final Application myApp;

    public EditorStatus status = EditorStatus.Command;

    public CommandStatus commandStatus = new CommandStatus();

    public OhPlugin(Application application) {
        this.myApp = application;
    }

    @Override
    public void initComponent() {

        //替换系统的TypedActionHandler
        final TypedAction typedAction = EditorActionManager.getInstance().getTypedAction();
        typedAction.setupHandler(new OhTypedActionHandler(typedAction.getHandler()));

        EditorFactory.getInstance().addEditorFactoryListener(new EditorFactoryAdapter() {
            @Override
            public void editorCreated(@NotNull EditorFactoryEvent event) {
                final Editor editor = event.getEditor();
                if (OhPlugin.isEnabled() && EditorHelper.isFileEditor(editor) && editor.getDocument().isWritable()) {
                    ShortcutKeyAction.getInstance().registerCustomShortcutSet(new CustomShortcutSet(KeyStroke.getKeyStroke(27, 0, false)), editor.getComponent());
                    KeyHandler.toCommandMod();
                } else {
                    KeyHandler.toInsertMod();
                }

            }

            @Override
            public void editorReleased(@NotNull EditorFactoryEvent event) {
            }
        }, myApp);

        OhScript.parseScriptFile();
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
    public static OhPlugin getInstance() {
        return (OhPlugin) ApplicationManager.getApplication().getComponent(COMPONENT_NAME);
    }

    public static boolean isEnabled() {
        return getInstance().enabled;
    }


    public static void setEnabled(final boolean enabled) {
        if (!enabled) {
            getInstance().turnOffPlugin();
        }

        getInstance().enabled = enabled;

        if (enabled) {
            getInstance().turnOnPlugin();
        }
    }

    private void turnOnPlugin() {
        status = EditorStatus.Command;
        setCursors(true);
    }

    private void turnOffPlugin() {
        setCursors(false);
        status = EditorStatus.Insert;
    }


    public void setCursors(boolean isBlock) {
        Editor[] editors = EditorFactory.getInstance().getAllEditors();
        for (Editor editor : editors) {
            editor.getSettings().setBlockCursor(isBlock);
        }
    }


}