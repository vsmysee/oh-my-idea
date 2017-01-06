package com.codingbaby.ohmyidea


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

    private val handler = KeyHandler.ohMyHander()

    override fun execute(editor: Editor, charTyped: Char, dataContext: DataContext) {

        if (isEnabled(editor) && OhPlugin.instance.status !== EditorStatus.Insert) {
            SwingUtilities.invokeLater {
                handler.handleKey(editor, KeyStroke.getKeyStroke(charTyped), dataContext)
            }
        } else {
            origHandler.execute(editor, charTyped, dataContext)
        }
    }

    private fun isEnabled(editor: Editor): Boolean {
        val lookup = LookupManager.getActiveLookup(editor)
        return lookup == null || !lookup.isFocused
    }
}
