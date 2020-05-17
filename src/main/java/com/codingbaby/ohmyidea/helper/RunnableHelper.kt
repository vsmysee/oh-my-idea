package com.codingbaby.ohmyidea.helper

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.project.Project

object RunnableHelper {

    fun runReadCommand(project: Project, cmd: Runnable, name: String, groupId: Any) {
        CommandProcessor.getInstance().executeCommand(project, ReadAction(cmd), name, groupId)
    }

    internal class ReadAction(private val cmd: Runnable) : Runnable {
        override fun run() {
            ApplicationManager.getApplication().runReadAction(cmd)
        }
    }

}
