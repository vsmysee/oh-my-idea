package com.codingbaby.ohmyidea.action.ui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.wm.ex.WindowManagerEx;
import com.intellij.ui.components.JBTabbedPane;
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
        JTabbedPane tabs = new JBTabbedPane();
        tabs.add("单击",new HelpPanel());
        tabs.add("组合",new HelpPanel());
        tabs.add("选择",new HelpPanel());
        tabs.add("底行",new HelpPanel());
        return tabs;
    }


    public void dispose(){
        super.dispose();
    }

}
