package com.codingbaby.ohmyidea


import `fun`.codecode.OhPlugin
import com.intellij.codeInsight.lookup.LookupManager
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.TypedActionHandler
import javax.swing.KeyStroke
import javax.swing.SwingUtilities

/**
 * 当编辑器发生文本输入的时候，由这个action来响应
 * 此action内部维护了系统的handler，当自己不处理的时候就交给系统的handler
 */
class OhTypedActionHandler(private val origHandler: TypedActionHandler) : TypedActionHandler {

    override fun execute(editor: Editor, charTyped: Char, dataContext: DataContext) {

        if (!OhPlugin.instance.active) {
            origHandler.execute(editor, charTyped, dataContext)
            return
        }

        if (!isEnabled(editor)) {
            origHandler.execute(editor, charTyped, dataContext)
            return
        }


        if (OhPlugin.instance.status !== EditorStatus.Insert) {
            SwingUtilities.invokeLater {
                KeyHandler.handleKey(editor, KeyStroke.getKeyStroke(charTyped), dataContext)
            }
            return
        }

        origHandler.execute(editor, charTyped, dataContext)

    }

    private fun isEnabled(editor: Editor): Boolean {
        val lookup = LookupManager.getActiveLookup(editor)
        return lookup == null || !lookup.isFocused
    }
}
