package com.codingbaby.ohmyidea.action.ui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.wm.ex.WindowManagerEx;
import com.intellij.ui.components.JBTabbedPane;
import com.codingbaby.ohmyidea.key.*;
import com.codingbaby.ohmyidea.script.OhScript;
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
        setHorizontalStretch(2.33f);
        setVerticalStretch(1.5f);
        init();
    }


    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JTabbedPane tabs = new JBTabbedPane();
        tabs.add("单击", new HelpPanel(SingleShort.commandHolder.getCommandDesc()));
        tabs.add("组合", new HelpPanel(ComposeShort.commandHolder.getCommandDesc()));
        tabs.add("选择", new HelpPanel(VisualShort.commandHolder.getCommandDesc()));
        tabs.add("底行", new HelpPanel(BottomShort.commandHolder.getCommandDesc()));
        tabs.add("移动", new HelpPanel(MoveShort.commandHolder.getCommandDesc()));
        tabs.add("模板", new HelpPanel(OhScript.getHelpDesc()));
        return tabs;
    }


    public void dispose() {
        super.dispose();
    }

}
