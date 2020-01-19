package com.codingbaby.ohmyidea.ui;

import com.codingbaby.ohmyidea.script.ShortHolder;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.wm.ex.WindowManagerEx;
import com.intellij.ui.components.JBTabbedPane;

import javax.swing.*;

public class HelpDialog extends DialogWrapper {


    public HelpDialog() {
        super(WindowManagerEx.getInstanceEx().findVisibleFrame(), true);
        setModal(false);
        setTitle("快捷键帮助");
        setHorizontalStretch(2f);
        setVerticalStretch(2f);
        init();
    }



    @Override
    protected JComponent createCenterPanel() {
        JTabbedPane tabs = new JBTabbedPane();
        tabs.add("单击", new HelpPanel(ShortHolder.INSTANCE.getSingle().getCommandDesc()));
        tabs.add("组合", new HelpPanel(ShortHolder.INSTANCE.getComposite().getCommandDesc()));
        tabs.add("选择", new HelpPanel(ShortHolder.INSTANCE.getSelect().getCommandDesc()));
        tabs.add("底行", new HelpPanel(ShortHolder.INSTANCE.getBottom().getCommandDesc()));
        tabs.add("移动", new HelpPanel(ShortHolder.INSTANCE.getMovement().getCommandDesc()));
        return tabs;
    }


    public void dispose() {
        super.dispose();
    }

}
