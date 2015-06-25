
package com.codingbaby.ohmyidea.ui;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorFontType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.text.Keymap;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

/**
 * Provides a custom keymap for the text field. The keymap is the VIM Ex command keymapping
 */

public class ExTextField extends JTextField {

    private Editor editor;

    private DataContext context;

    @Nullable
    private Action currentAction;



    /**
     */
    public ExTextField() {
        Font font = EditorColorsManager.getInstance().getGlobalScheme().getFont(EditorFontType.PLAIN);
        setFont(font);

        final Action[] actions = ExEditorKit.getInstance().getActions();
        final ActionMap actionMap = getActionMap();
        for (Action a : actions) {
            actionMap.put(a.getValue(Action.NAME), a);
        }

        setInputMap(WHEN_FOCUSED, new InputMap());
        Keymap map = addKeymap("ex", getKeymap());
        loadKeymap(map, ExKeyBindings.getBindings(), actions);
        setKeymap(map);
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                setCaretPosition(getText().length());
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
    }


    void setEditor(Editor editor, DataContext context) {
        this.editor = editor;
        this.context = context;
    }

    public Editor getEditor() {
        return editor;
    }

    public DataContext getContext() {
        return context;
    }


    public void setText(String string) {
        super.setText(string);
    }

    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);
    }


    public void escape() {
        if (currentAction != null) {
            currentAction = null;
        } else {
            ExEntryPanel.getInstance().cancelExEntry();
        }
    }

}
