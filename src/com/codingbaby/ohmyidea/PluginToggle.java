package com.codingbaby.ohmyidea;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.project.DumbAware;

/**
 * 通过菜单激活和关闭
 */
public class PluginToggle extends ToggleAction implements DumbAware {

    @Override
    public boolean isSelected(AnActionEvent anActionEvent) {
        return OhPlugin.isEnabled();
    }

    @Override
    public void setSelected(AnActionEvent anActionEvent, boolean b) {
        OhPlugin.setEnabled(b);
    }
}
