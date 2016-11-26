package com.codingbaby.ohmyidea.action

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.intellij.openapi.editor.actionSystem.EditorActionHandler

open class DeleteToFileEndAction : EditorAction {

    constructor() : super(object : EditorActionHandler() {
        override fun doExecute(editor: Editor, caret: Caret?, dataContext: DataContext) {
            var startPos = editor.getCaretModel().offset
            var endPos = editor.getDocument().textLength
            editor.getDocument().deleteString(startPos, endPos)
        }

    })
}

