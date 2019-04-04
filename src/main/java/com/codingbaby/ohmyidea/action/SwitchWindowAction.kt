package com.codingbaby.ohmyidea.action

import com.codingbaby.ohmyidea.KeyHandler
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.intellij.openapi.editor.actionSystem.EditorActionHandler


class SwitchWindowAction : EditorAction(object : EditorActionHandler() {

    override fun doExecute(editor: Editor, caret: Caret?, dataContext: DataContext) {
        KeyHandler.executeAction("NextProjectWindow", dataContext)
    }

})

