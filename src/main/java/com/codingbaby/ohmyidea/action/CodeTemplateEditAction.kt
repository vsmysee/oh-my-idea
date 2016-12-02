package com.codingbaby.ohmyidea.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.ScrollPaneFactory
import com.intellij.ui.components.JBLabel
import com.codingbaby.ohmyidea.script.OhScript


import javax.swing.*


class CodeTemplateEditAction : DumbAwareAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = if (e.project == null) ProjectManager.getInstance().defaultProject else e.project
        val dialog = EditDialog(project, OhScript.loadContent())
        if (dialog.showAndGet()) {
            val text = dialog.myTextArea.text
            OhScript.saveScript(text)
        }
    }

    private class EditDialog(project: Project?, text: String) : DialogWrapper(project, false) {

        val myTextArea: JTextArea

        init {
            myTextArea = JTextArea(30, 100)
            myTextArea.text = text
            title = "编辑代码模板"
            init()
        }


        override fun createNorthPanel(): JComponent? {
            return JBLabel("")
        }


        override fun createCenterPanel(): JComponent? {
            return ScrollPaneFactory.createScrollPane(myTextArea)
        }


        override fun getPreferredFocusedComponent(): JComponent? {
            return myTextArea
        }


    }

}
