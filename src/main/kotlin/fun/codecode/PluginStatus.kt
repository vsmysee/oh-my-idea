package `fun`.codecode

import com.codingbaby.ohmyidea.CommandBuffer
import com.intellij.openapi.editor.EditorFactory

object PluginStatus {

    var status = EditorStatus.Command

    var active = false

    fun active(flag: Boolean) {
        active = flag
        if (flag) {
            mode(EditorStatus.Command)
        } else {
            mode(EditorStatus.Insert)
        }
    }

    fun mode(s: EditorStatus) {
        status = s

        setCursors(status != EditorStatus.Insert)
        CommandBuffer.reset()
    }

    fun setCursors(isBlock: Boolean) {
        val editors = EditorFactory.getInstance().allEditors
        for (editor in editors) {
            editor.settings.isBlockCursor = isBlock
        }
    }
}