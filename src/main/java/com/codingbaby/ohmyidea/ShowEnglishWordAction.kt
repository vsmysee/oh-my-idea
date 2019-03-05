package com.codingbaby.ohmyidea

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.codeInsight.lookup.LookupManager
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.intellij.openapi.editor.actionSystem.EditorActionHandler


class ShowEnglishWordAction : EditorAction(object : EditorActionHandler() {


    private val wordLookup: WordLookup = TomlWordLookup()


    override fun doExecute(editor: Editor, caret: Caret?, dataContext: DataContext) {


        var project = editor.project ?: return

        val selectedText = editor.selectionModel.getSelectedText(true) ?: ""

        val snippets = wordLookup.getSnippets(project, selectedText)

        val lookupElements = snippets.map(LookupElementBuilder::create).toTypedArray()


        val lookup = LookupManager.getInstance(project).showLookup(editor, *lookupElements) ?: return

    }

})

