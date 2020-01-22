package `fun`.codecode.action

import com.codingbaby.ohmyidea.script.OhScript
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.intellij.openapi.ui.Messages


class LoadScriptAction : EditorAction(object : EditorActionHandler() {

    override fun doExecute(editor: Editor, caret: Caret?, dataContext: DataContext) {
        if (Messages.showYesNoDialog("Reload the config?", "info",
                        Messages.getQuestionIcon()) == Messages.YES) {

            val loadGroovyScriptFile = OhScript.loadGroovyScriptFile()

            if (loadGroovyScriptFile) {
                Messages.showInfoMessage("Load Success", "info");
            } else {
                Messages.showInfoMessage("Load Fail", "info");
            }


        }
    }
})

