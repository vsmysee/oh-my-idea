package com.codingbaby.ohmyidea

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import be.sourcedbvba.shading.com.moandjiezana.toml.Toml

interface WordLookup {
    fun getSnippets(project: Project, selectText: String): List<String>
}

class TomlWordLookup : WordLookup {
    private val toml = Toml()
    private var currentSnippets: Map<String, Any> = mapOf()
    private var modificationStamp: Long = -1

    override fun getSnippets(project: Project, selectText: String): List<String> {
        val snippetFile = getSnippetFile(project)
        if (snippetFile != null) {
            if (fileHasChanged(snippetFile)) {
                updateLastModified(snippetFile)
                loadSnippets(snippetFile)
            }
        }
        val toSnippetList = toSnippetList(currentSnippets);

        if (selectText == "") {
            return toSnippetList
        }

        return toSnippetList.filter { it.startsWith(selectText) }
    }

    private fun updateLastModified(snippetFile: VirtualFile) {
        modificationStamp = snippetFile.modificationStamp
    }

    private fun getSnippetFile(project: Project) = project.baseDir.findChild(".project.word.toml")

    private fun fileHasChanged(snippetFile: VirtualFile) = modificationStamp < snippetFile.modificationStamp

    private fun toSnippetList(snippets: Map<String, Any>) = snippets.map { it.value.toString() }.toList()

    private fun loadSnippets(snippetFile: VirtualFile) {
        try {
            currentSnippets = toml.read(snippetFile.inputStream).toMap()
        } catch (e: Exception) {
            currentSnippets = mapOf()
        }
    }
}