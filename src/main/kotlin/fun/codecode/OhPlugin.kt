package `fun`.codecode


import com.codingbaby.ohmyidea.ShortcutKeyAction
import com.codingbaby.ohmyidea.helper.EditorHelper
import com.codingbaby.ohmyidea.script.OhScript
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.CommonShortcuts
import com.intellij.openapi.actionSystem.CustomShortcutSet
import com.intellij.openapi.application.Application
import com.intellij.openapi.components.BaseComponent
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.actionSystem.EditorActionManager
import com.intellij.openapi.editor.event.EditorFactoryEvent
import com.intellij.openapi.editor.event.EditorFactoryListener


class OhPlugin(private val myApp: Application) : BaseComponent {

    val ACTION_ID = "OH_ShortcutKeyAction"

    val COMPONENT_NAME = "Oh My IDEA"


    override fun initComponent() {

        //替换系统的TypedActionHandler
        val typedAction = EditorActionManager.getInstance().typedAction
        typedAction.setupHandler(OhTypedActionHandler(typedAction.handler))

        EditorFactory.getInstance().addEditorFactoryListener(object : EditorFactoryListener {

            override fun editorCreated(event: EditorFactoryEvent) {
                val editor = event.editor

                var action = ActionManager.getInstance().getAction(ACTION_ID)

                if (action == null) {
                    action = ShortcutKeyAction()
                    ActionManager.getInstance().registerAction(ACTION_ID, action)
                }

                val shortcutSet = CustomShortcutSet.fromString("ctrl 9")
                action.registerCustomShortcutSet(shortcutSet, editor.component)

                if (EditorHelper.isFileEditor(editor) && PluginStatus.active) {
                    PluginStatus.mode(EditorStatus.Command)
                } else {
                    PluginStatus.mode(EditorStatus.Insert)
                }
            }

            override fun editorReleased(event: EditorFactoryEvent) {

            }

        }, myApp)

        //on idea has groovy lib
        val load = OhScript.loadGroovyScriptFile()
        PluginStatus.active = load
    }

    override fun disposeComponent() {
    }


    override fun getComponentName(): String {
        return COMPONENT_NAME
    }


}