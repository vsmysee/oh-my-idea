package com.codingbaby.ohmyidea.action

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.codingbaby.ohmyidea.KeyHandler
import com.codingbaby.ohmyidea.helper.EditorHelper


class MotionAndInsertAction : EditorAction(object : EditorActionHandler() {
    override fun doExecute(editor: Editor, caret: Caret?, dataContext: DataContext) {
        val oldOffset = editor.caretModel.offset
        editor.caretModel.moveToOffset(oldOffset + 1)
        EditorHelper.scrollCaretIntoView(editor)
        KeyHandler.toInsertMod()
    }
})
