package com.codingbaby.ohmyidea

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import be.sourcedbvba.shading.com.moandjiezana.toml.Toml
import java.io.StringWriter
import java.io.InputStreamReader
import java.io.BufferedReader


interface WordLookup {
    fun getSnippets(project: Project, selectText: String): List<String>
}

class TomlWordLookup : WordLookup {
    private val toml = Toml()
    private var currentSnippets: List<String> = listOf()
    private var modificationStamp: Long = -1

    override fun getSnippets(project: Project, selectText: String): List<String> {
        val snippetFile = getSnippetFile(project)
        if (snippetFile != null) {
            if (fileHasChanged(snippetFile)) {
                updateLastModified(snippetFile)
                loadSnippets(snippetFile)
            }
        }

        if (selectText == "") {
            return currentSnippets
        }

        return currentSnippets.filter { it.startsWith(selectText) }
    }

    private fun updateLastModified(snippetFile: VirtualFile) {
        modificationStamp = snippetFile.modificationStamp
    }

    private fun getSnippetFile(project: Project) = project.baseDir.findChild(".project.word.toml")

    private fun fileHasChanged(snippetFile: VirtualFile) = modificationStamp < snippetFile.modificationStamp


    private fun loadSnippets(snippetFile: VirtualFile) {
        try {
            val inputAsString = snippetFile.inputStream.bufferedReader().use { it.readText() }
            currentSnippets = inputAsString.split("\n").toList()
        } catch (e: Exception) {
            currentSnippets = listOf()
        }
    }
}