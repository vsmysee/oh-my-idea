package com.codingbaby.ohmyidea

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import be.sourcedbvba.shading.com.moandjiezana.toml.Toml

interface SnippetLookup {
    fun getSnippets(project: Project): List<Snippet>
}

class TomlSnippetLookup : SnippetLookup {
    private val toml = Toml()
    private var currentSnippets: Map<String, Any> = mapOf()
    private var modificationStamp: Long = -1

    override fun getSnippets(project: Project): List<Snippet> {
        val snippetFile = getSnippetFile(project)
        if(snippetFile != null) {
            if (fileHasChanged(snippetFile)) {
                updateLastModified(snippetFile)
                loadSnippets(snippetFile)
            }
        }
        return toSnippetList(currentSnippets);
    }

    private fun updateLastModified(snippetFile: VirtualFile) {
        modificationStamp = snippetFile.modificationStamp
    }

    private fun getSnippetFile(project: Project) = project.baseDir.findChild(".project.snippets.toml")

    private fun fileHasChanged(snippetFile: VirtualFile) = modificationStamp < snippetFile.modificationStamp

    private fun toSnippetList(snippets: Map<String, Any>) = snippets.map { Snippet(it.key, it.value.toString()) }.toList()

    private fun loadSnippets(snippetFile: VirtualFile) {
        try {
            currentSnippets = toml.read(snippetFile.inputStream).toMap()
        } catch (e: Exception) {
            currentSnippets =  mapOf()
        }
    }
}