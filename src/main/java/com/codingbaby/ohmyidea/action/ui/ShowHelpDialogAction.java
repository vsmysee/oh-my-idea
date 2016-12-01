package com.codingbaby.ohmyidea.action.ui;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;

/**
 * Created by baby on 15/3/7.
 */

public class ShowHelpDialogAction extends DumbAwareAction {

    private static HelpDialog helpDialog;

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        if (helpDialog != null && helpDialog.isVisible()) {
            helpDialog.dispose();
        }
        helpDialog = new HelpDialog();
        helpDialog.show();
    }


}
