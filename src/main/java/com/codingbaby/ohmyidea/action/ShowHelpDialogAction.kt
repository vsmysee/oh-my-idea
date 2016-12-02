package com.codingbaby.ohmyidea.action

import com.codingbaby.ohmyidea.action.ui.HelpDialog
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction

/**
 * Created by baby on 15/3/7.
 */

class ShowHelpDialogAction : DumbAwareAction() {

    override fun actionPerformed(anActionEvent: AnActionEvent) {
        if (helpDialog != null && helpDialog!!.isVisible) {
            helpDialog!!.dispose()
        }
        helpDialog = HelpDialog()
        helpDialog!!.show()
    }

    companion object {

        private var helpDialog: HelpDialog? = null
    }


}