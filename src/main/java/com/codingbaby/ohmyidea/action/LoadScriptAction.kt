package com.codingbaby.ohmyidea.action

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.intellij.openapi.ui.Messages
import com.codingbaby.ohmyidea.script.OhScript

import com.intellij.openapi.diagnostic.Logger;


class LoadScriptAction : EditorAction(object : EditorActionHandler() {

    override fun doExecute(editor: Editor, caret: Caret?, dataContext: DataContext) {
        if (Messages.showYesNoDialog("是否重新加载代码模板?", "通知",
                Messages.getQuestionIcon()) == Messages.YES) {

            OhScript.loadScriptFile()
        }
    }
})

