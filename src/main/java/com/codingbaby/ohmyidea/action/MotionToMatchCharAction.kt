package com.codingbaby.ohmyidea.action

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.codingbaby.ohmyidea.KeyHandler
import com.codingbaby.ohmyidea.helper.EditorHelper


class MotionToMatchCharAction : EditorAction(object : EditorActionHandler() {
    override fun doExecute(editor: Editor, caret: Caret?, dataContext: DataContext) {
        val offset = EditorHelper.findNextCharacterOnLine(editor, KeyHandler.toChar)
        if (offset != 0) {
            editor.caretModel.moveToOffset(offset)
            EditorHelper.scrollCaretIntoView(editor)
        }
    }
})
