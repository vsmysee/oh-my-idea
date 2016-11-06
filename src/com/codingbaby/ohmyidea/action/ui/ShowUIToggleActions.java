package com.codingbaby.ohmyidea.action.ui;

import com.codingbaby.ohmyidea.KeyHandler;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;

/**
 * Created by baby on 16/11/6.
 */
public class ShowUIToggleActions extends AnAction implements DumbAware {


    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        KeyHandler.executeAction("ViewToolBar",anActionEvent.getDataContext());
        KeyHandler.executeAction("ViewToolButtons",anActionEvent.getDataContext());
        KeyHandler.executeAction("ViewStatusBar",anActionEvent.getDataContext());
    }
}
