package com.codingbaby.ohmyidea;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 */
public class KeyHandler {


    private TypedActionHandler origHandler;

    private static KeyHandler instance;


    @NotNull
    public static KeyHandler getInstance() {
        if (instance == null) {
            instance = new KeyHandler();
        }
        return instance;
    }

    public void handleKey(@NotNull Editor editor, @NotNull KeyStroke key, @NotNull DataContext context) {

    }

    /**
     * Sets the original key handler
     *
     * @param origHandler The original key handler
     */
    public void setOriginalHandler(TypedActionHandler origHandler) {
        this.origHandler = origHandler;
    }

    /**
     * Gets the original key handler
     *
     * @return The original key handler
     */
    public TypedActionHandler getOriginalHandler() {
        return origHandler;
    }
}
