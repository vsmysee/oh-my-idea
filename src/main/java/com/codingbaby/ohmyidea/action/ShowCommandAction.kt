package com.codingbaby.ohmyidea.action

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.codingbaby.ohmyidea.ui.ExEntryPanel


class ShowCommandAction : EditorAction(object : EditorActionHandler() {

    override fun doExecute(editor: Editor, caret: Caret?, dataContext: DataContext) {
        val panel = ExEntryPanel.getInstance()
        panel.activate(editor, dataContext, ":", "", 1)
    }
})
