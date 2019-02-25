package com.codingbaby.ohmyidea

import com.intellij.codeInsight.actions.FileInEditorProcessor
import com.intellij.codeInsight.actions.LastRunReformatCodeOptionsProvider
import com.intellij.codeInsight.actions.TextRangeType
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile

class SnippetCompletionContributor(

        private val snippetLookup: SnippetLookup = TomlSnippetLookup(),
        private val psiFileLookup: PsiFileLookup = PsiDocumentManagerPsiFileLookup(),
        private val editorFileFormatter: EditorFileFormatter = DefaultEditorFileFormatter()) : CompletionContributor() {

    override fun fillCompletionVariants(parameters: CompletionParameters, result: CompletionResultSet) {
        if (parameters.completionType == CompletionType.BASIC) {
            getSnippets(parameters.editor).forEach { result.addElement(it) }
        }
    }

    private fun getSnippets(editor: Editor) = snippetLookup.getSnippets(editor.project!!)
            .map { createLookupElementFromSnippet(it, editor) }
            .toList()

    private fun createLookupElementFromSnippet(snippet: Snippet, editor: Editor) =
            LookupElementBuilder
                    .create(snippet.code)
                    .withPresentableText(snippet.key)
                    .withTypeText("custom snippet")
                    .withLookupString(snippet.key)
                    .withInsertHandler({ insertionContext, _ ->
                        //removeLastInsertedCharacter(insertionContext)
                        doReformat(editor)
                    })

    private fun removeLastInsertedCharacter(insertionContext: InsertionContext) =
            removeCharacterBeforeOffset(insertionContext, getCurrentCaretPosition(insertionContext))

    private fun removeCharacterBeforeOffset(insertionContext: InsertionContext, offset: Int) =
            insertionContext.document.deleteString(offset - 1, offset)

    private fun getCurrentCaretPosition(insertionContext: InsertionContext) =
            insertionContext.editor.caretModel.offset

    private fun doReformat(editor: Editor) =
            editorFileFormatter.format(getCurrentEditorFile(editor),
                    editor)

    private fun getCurrentEditorFile(editor: Editor): PsiFile =
            psiFileLookup.lookup(editor.project!!, editor.document)!!
}

interface PsiFileLookup {
    fun lookup(project: Project, document: Document): PsiFile?
}

class PsiDocumentManagerPsiFileLookup : PsiFileLookup {
    override fun lookup(project: Project, document: Document) =
            PsiDocumentManager.getInstance(project).getPsiFile(document)
}

interface EditorFileFormatter {
    fun format(file: PsiFile, editor: Editor)
}

class DefaultEditorFileFormatter : EditorFileFormatter {

    override fun format(file: PsiFile, editor: Editor) =
            createFileInEditorProcessor(file, editor).processCode()


    private fun createFileInEditorProcessor(file: PsiFile, editor: Editor): FileInEditorProcessor {
        val provider = LastRunReformatCodeOptionsProvider(PropertiesComponent.getInstance())
        val currentRunOptions = provider.getLastRunOptions(file)
        currentRunOptions.setProcessingScope(TextRangeType.WHOLE_FILE)
        return FileInEditorProcessor(file, editor, currentRunOptions)
    }

}