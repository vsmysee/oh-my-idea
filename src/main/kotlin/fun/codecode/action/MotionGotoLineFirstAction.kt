package `fun`.codecode.action

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.codingbaby.ohmyidea.helper.EditorHelper


class MotionGotoLineFirstAction : EditorAction(object : EditorActionHandler() {
    override fun doExecute(editor: Editor, caret: Caret?, dataContext: DataContext) {
        editor.caretModel.moveToOffset(0)
        EditorHelper.scrollCaretIntoView(editor)
    }
})
