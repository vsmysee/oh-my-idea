package com.codingbaby.ohmyidea.action.ui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.wm.ex.WindowManagerEx;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by baby on 15/6/23.
 */
public class HelpDialog extends DialogWrapper {


    public HelpDialog() {
        super(WindowManagerEx.getInstanceEx().findVisibleFrame(), true);
        setModal(false);
        setTitle("快捷键帮助");
        setHorizontalStretch(1.33f);
        setVerticalStretch(1.25f);
        init();
    }



    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return new HelpPanel();
    }


    public void dispose(){
        super.dispose();
    }

}
