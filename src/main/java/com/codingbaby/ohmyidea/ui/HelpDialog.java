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
        setTitle("KeyHelp");
        setHorizontalStretch(2f);
        setVerticalStretch(2f);
        init();
    }



    @Override
    protected JComponent createCenterPanel() {
        JTabbedPane tabs = new JBTabbedPane();
        tabs.add("Single", new HelpPanel(ShortHolder.INSTANCE.getSingle().getCommandDesc()));
        tabs.add("Compose", new HelpPanel(ShortHolder.INSTANCE.getComposite().getCommandDesc()));
        tabs.add("Select", new HelpPanel(ShortHolder.INSTANCE.getSelect().getCommandDesc()));
        tabs.add("Bottom", new HelpPanel(ShortHolder.INSTANCE.getBottom().getCommandDesc()));
        tabs.add("Move", new HelpPanel(ShortHolder.INSTANCE.getMovement().getCommandDesc()));
        return tabs;
    }


    public void dispose() {
        super.dispose();
    }

}
