package com.codingbaby.ohmyidea;

import com.intellij.codeInsight.lookup.Lookup;
import com.intellij.codeInsight.lookup.LookupManager;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 */
public class OhTypedActionHandler implements TypedActionHandler {

    private final TypedActionHandler origHandler;
    @NotNull
    private final KeyHandler handler;

    public OhTypedActionHandler(TypedActionHandler origHandler) {
        this.origHandler = origHandler;
        handler = KeyHandler.getInstance();
    }


    @Override
    public void execute(@NotNull final Editor editor, final char charTyped, @NotNull final DataContext dataContext) {

        if (isEnabled(editor)) {
            // Run key handler outside of the key typed command for creating our own undoable commands
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        handler.handleKey(editor, KeyStroke.getKeyStroke(charTyped), dataContext);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            origHandler.execute(editor, charTyped, dataContext);
        }
    }

    private boolean isEnabled(@NotNull Editor editor) {
        if (OhPlugin.isEnabled()) {
            final Lookup lookup = LookupManager.getActiveLookup(editor);
            return lookup == null || !lookup.isFocused();
        }
        return false;
    }
}
