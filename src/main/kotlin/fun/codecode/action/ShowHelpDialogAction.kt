package `fun`.codecode.action

import com.codingbaby.ohmyidea.ui.HelpDialog
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction

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
