package com.codingbaby.ohmyidea;

import com.intellij.codeInsight.lookup.Lookup;
import com.intellij.codeInsight.lookup.LookupManager;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;


import javax.swing.*;

/**
 * 当编辑器发生文本输入的时候，由这个action来响应
 * 此action内部维护了系统的handler，当自己不处理的时候就交给系统的handler
 */
public class OhTypedActionHandler implements TypedActionHandler {

    private final TypedActionHandler origHandler;


    private final KeyHandler handler;

    public OhTypedActionHandler(TypedActionHandler origHandler) {
        this.origHandler = origHandler;
        handler = KeyHandler.getInstance();
    }


    @Override
    public void execute( final Editor editor, final char charTyped,  final DataContext dataContext) {

        if (isEnabled(editor) && (OhPlugin.getInstance().status != EditorStatus.Insert)) {

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

    private boolean isEnabled( Editor editor) {
        if (OhPlugin.isEnabled()) {
            final Lookup lookup = LookupManager.getActiveLookup(editor);
            return lookup == null || !lookup.isFocused();
        }
        return false;
    }
}
