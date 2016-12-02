package com.codingbaby.ohmyidea.ui


import javax.swing.*
import javax.swing.text.JTextComponent.KeyBinding
import java.awt.event.KeyEvent

object ExKeyBindings {

    val bindings = arrayOf(

            KeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), ExEditorKit.EscapeChar),

            KeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK), ExEditorKit.CancelEntry),

            KeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), ExEditorKit.CompleteEntry),


            KeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_MASK), ExEditorKit.beginLineAction), KeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_HOME, 0), ExEditorKit.beginLineAction),

            KeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_MASK), ExEditorKit.endLineAction), KeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_END, 0), ExEditorKit.endLineAction),


            KeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), ExEditorKit.DeletePreviousChar),

            KeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), ExEditorKit.deleteNextCharAction),

            KeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_MASK), ExEditorKit.DeletePreviousWord),

            KeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_MASK), ExEditorKit.DeleteToCursor),


            KeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), ExEditorKit.backwardAction),

            KeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, KeyEvent.SHIFT_MASK), ExEditorKit.previousWordAction), KeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, KeyEvent.CTRL_MASK), ExEditorKit.previousWordAction),

            KeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), ExEditorKit.forwardAction),

            KeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.SHIFT_MASK), ExEditorKit.nextWordAction), KeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.CTRL_MASK), ExEditorKit.nextWordAction),

            KeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.META_MASK), ExEditorKit.pasteAction))


}
