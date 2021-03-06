package `fun`.codecode.action

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import `fun`.codecode.KeyHandler
import com.codingbaby.ohmyidea.helper.EditorHelper


class MotionToLineAction : EditorAction(object : EditorActionHandler() {
    override fun doExecute(editor: Editor, caret: Caret?, dataContext: DataContext) {
        val offset = EditorHelper.getLineStartOffset(editor, KeyHandler.toLine - 1)
        editor.caretModel.moveToOffset(offset)
        EditorHelper.scrollCaretIntoView(editor)
    }
})
