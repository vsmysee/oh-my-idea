package com.codingbaby.ohmyidea


import com.codingbaby.ohmyidea.helper.EditorHelper
import com.codingbaby.ohmyidea.script.OhScript
import com.intellij.openapi.actionSystem.CustomShortcutSet
import com.intellij.openapi.application.Application
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.actionSystem.EditorActionManager
import com.intellij.openapi.editor.event.EditorFactoryAdapter
import com.intellij.openapi.editor.event.EditorFactoryEvent
import javax.swing.KeyStroke

class OhPlugin(private val myApp: Application) : ApplicationComponent {

    //打开的小窗口特殊处理
    var openPopWindow = false

    var status = EditorStatus.Command

    var commandStatus = CommandStatus()

    override fun initComponent() {

        //替换系统的TypedActionHandler
        val typedAction = EditorActionManager.getInstance().typedAction
        typedAction.setupHandler(OhTypedActionHandler(typedAction.handler))

        EditorFactory.getInstance().addEditorFactoryListener(object : EditorFactoryAdapter() {
            override fun editorCreated(event: EditorFactoryEvent) {
                val editor = event.editor
                ShortcutKeyAction.instance.registerCustomShortcutSet(CustomShortcutSet(KeyStroke.getKeyStroke(27, 0, false)), editor.component)
                if (EditorHelper.isFileEditor(editor) && !openPopWindow) {
                    KeyHandler.toCommandMod()
                } else {
                    KeyHandler.toInsertMod()
                }

            }

            override fun editorReleased(event: EditorFactoryEvent) {
            }
        }, myApp)

        OhScript.loadScriptFile()
    }

    override fun disposeComponent() {
    }


    override fun getComponentName(): String {
        return COMPONENT_NAME
    }


    fun setCursors(isBlock: Boolean) {
        val editors = EditorFactory.getInstance().allEditors
        for (editor in editors) {
            editor.settings.isBlockCursor = isBlock
        }
    }

    companion object {

        private val COMPONENT_NAME = "Oh My IDEA"

        val instance: OhPlugin
            get() = ApplicationManager.getApplication().getComponent(COMPONENT_NAME) as OhPlugin

        fun setPopWindowOpen(open: Boolean) {
            instance.openPopWindow = open
        }
    }


}