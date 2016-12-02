package com.codingbaby.ohmyidea.action

import com.intellij.openapi.editor.actionSystem.EditorAction
import com.intellij.openapi.editor.actionSystem.EditorActionHandler

class DeleteToFileEndAction : EditorAction(object : EditorActionHandler() {
    override fun doExecute(editor: com.intellij.openapi.editor.Editor, caret: com.intellij.openapi.editor.Caret?, dataContext: com.intellij.openapi.actionSystem.DataContext) {
        var startPos = editor.getCaretModel().offset
        var endPos = editor.getDocument().textLength
        editor.getDocument().deleteString(startPos, endPos)
    }

})

