package `fun`.codecode

import com.codingbaby.ohmyidea.EditorStatus
import com.codingbaby.ohmyidea.KeyHandler
import com.codingbaby.ohmyidea.OhTypedActionHandler
import com.codingbaby.ohmyidea.ShortcutKeyAction
import com.codingbaby.ohmyidea.helper.EditorHelper
import com.codingbaby.ohmyidea.script.OhScript
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.CommonShortcuts
import com.intellij.openapi.application.Application
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.actionSystem.EditorActionManager
import com.intellij.openapi.editor.event.EditorFactoryAdapter
import com.intellij.openapi.editor.event.EditorFactoryEvent


class OhPlugin(private val myApp: Application) : ApplicationComponent {

    val ACTION_ID = "OH_ShortcutKeyAction"

    var status = EditorStatus.Command

    var active = true;


    override fun initComponent() {

        //替换系统的TypedActionHandler
        val typedAction = EditorActionManager.getInstance().typedAction
        typedAction.setupHandler(OhTypedActionHandler(typedAction.handler))

        EditorFactory.getInstance().addEditorFactoryListener(object : EditorFactoryAdapter() {

            override fun editorCreated(event: EditorFactoryEvent) {
                val editor = event.editor

                var action = ShortcutKeyAction()
                ActionManager.getInstance().registerAction(ACTION_ID, action)
                action.registerCustomShortcutSet(CommonShortcuts.ESCAPE, editor.component)

                if (EditorHelper.isFileEditor(editor) && active) {
                    KeyHandler.mode(EditorStatus.Command)
                } else {
                    KeyHandler.mode(EditorStatus.Insert)
                }
            }

        }, myApp)

        //on idea has groovy lib
        OhScript.loadGroovyScriptFile()
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

        fun active(flag: Boolean) {
            instance.active = flag
            if (!flag) {
                KeyHandler.mode(EditorStatus.Insert)
            }
        }
    }


}