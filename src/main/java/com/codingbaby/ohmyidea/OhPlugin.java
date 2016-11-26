package com.codingbaby.ohmyidea;


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
import com.codingbaby.ohmyidea.helper.EditorHelper;
import com.codingbaby.ohmyidea.script.OhScript;


import javax.swing.*;

public class OhPlugin implements ApplicationComponent {

    private static final String COMPONENT_NAME = "Oh My IDEA";

    private boolean enabled = true;

    //打开的小窗口特殊处理
    public boolean openPopWindow = false;

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
            public void editorCreated( EditorFactoryEvent event) {
                final Editor editor = event.getEditor();
                ShortcutKeyAction.getInstance().registerCustomShortcutSet(new CustomShortcutSet(KeyStroke.getKeyStroke(27, 0, false)), editor.getComponent());
                if (OhPlugin.isEnabled() && EditorHelper.isFileEditor(editor) && !openPopWindow) {
                    KeyHandler.toCommandMod();
                } else {
                    KeyHandler.toInsertMod();
                }

            }

            @Override
            public void editorReleased( EditorFactoryEvent event) {
            }
        }, myApp);

        OhScript.loadScriptFile();
    }

    @Override
    public void disposeComponent() {
    }


    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }



    public static OhPlugin getInstance() {
        return (OhPlugin) ApplicationManager.getApplication().getComponent(COMPONENT_NAME);
    }

    public static boolean isEnabled() {
        return getInstance().enabled;
    }

    public static void setPopWindowOpen(boolean open) {
        getInstance().openPopWindow = true;
    }



    public void setCursors(boolean isBlock) {
        Editor[] editors = EditorFactory.getInstance().getAllEditors();
        for (Editor editor : editors) {
            editor.getSettings().setBlockCursor(isBlock);
        }
    }


}