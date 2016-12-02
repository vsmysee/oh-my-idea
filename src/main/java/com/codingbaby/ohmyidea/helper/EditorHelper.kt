package com.codingbaby.ohmyidea.helper

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.LogicalPosition
import com.intellij.openapi.editor.VisualPosition
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.testFramework.LightVirtualFile


import java.awt.*

object EditorHelper {


    fun getVirtualFile(editor: Editor): VirtualFile? {
        return FileDocumentManager.getInstance().getFile(editor.document)
    }

    fun isFileEditor(editor: Editor): Boolean {
        val virtualFile = getVirtualFile(editor)
        return virtualFile != null && virtualFile !is LightVirtualFile
    }

    fun getLineStartOffset(editor: Editor, line: Int): Int {
        if (line < 0) {
            return 0
        } else if (line >= getLineCount(editor)) {
            return editor.document.textLength
        } else {
            return editor.document.getLineStartOffset(line)
        }
    }

    fun getLineEndOffset(editor: Editor, line: Int): Int {
        if (line < 0) {
            return 0
        } else if (line >= getLineCount(editor)) {
            return editor.document.textLength
        } else {
            return editor.document.getLineEndOffset(line)
        }
    }


    fun getVisualLineAtTopOfScreen(editor: Editor): Int {
        val lh = editor.lineHeight
        return (editor.scrollingModel.verticalScrollOffset + lh - 1) / lh
    }

    fun getScreenHeight(editor: Editor): Int {
        val lh = editor.lineHeight
        val height = editor.scrollingModel.visibleArea.y + editor.scrollingModel.visibleArea.height - getVisualLineAtTopOfScreen(editor) * lh
        return height / lh
    }

    fun getVisualColumnAtLeftOfScreen(editor: Editor): Int {
        val cw = getColumnWidth(editor)
        if (cw == 0) return 0
        return (editor.scrollingModel.horizontalScrollOffset + cw - 1) / cw
    }


    fun getScreenWidth(editor: Editor): Int {
        val rect = editor.scrollingModel.visibleArea
        val pt = Point(rect.width, 0)
        val vp = editor.xyToVisualPosition(pt)
        return vp.column
    }

    fun scrollCaretIntoView(editor: Editor) {

        moveToTop(editor)
        moveToLeft(editor)

    }

    fun scrollLineToScreenLine(editor: Editor) {
        val visualLine = editor.caretModel.visualPosition.line
        scrollLineToTopOfScreen(editor, EditorHelper.normalizeVisualLine(editor, visualLine))
    }

    fun normalizeVisualLine(editor: Editor, line: Int): Int {
        return Math.max(0, Math.min(line, getVisualLineCount(editor) - 1))
    }

    private fun moveToLeft(editor: Editor) {
        val caretColumn = editor.caretModel.visualPosition.column
        val visualColumn = EditorHelper.getVisualColumnAtLeftOfScreen(editor)
        val width = EditorHelper.getScreenWidth(editor)

        var scrollOffset = 0
        var sjSize = width / 2

        var visualLeft = visualColumn + scrollOffset
        var visualRight = visualColumn + width - scrollOffset
        if (scrollOffset >= width / 2) {
            scrollOffset = width / 2
            visualLeft = visualColumn + scrollOffset
            visualRight = visualColumn + width - scrollOffset
            if (visualLeft == visualRight) {
                visualRight++
            }
        }

        sjSize = Math.min(sjSize, width / 2 - scrollOffset)
        var diff: Int

        if (caretColumn < visualLeft) {
            diff = caretColumn - visualLeft + 1
            sjSize = -sjSize
        } else {
            diff = caretColumn - visualRight + 1
            if (diff < 0) {
                diff = 0
            }
        }

        if (diff != 0) {
            var col: Int
            if (Math.abs(diff) > width / 2) {
                col = caretColumn - width / 2 - 1
            } else {
                col = visualColumn + diff + sjSize
            }

            col = Math.max(0, col)
            scrollColumnToLeftOfScreen(editor, col)
        }
    }

    private fun moveToTop(editor: Editor) {
        val cline = editor.caretModel.visualPosition.line
        val visualLine = EditorHelper.getVisualLineAtTopOfScreen(editor)

        var scrollOffset = 0
        var sjSize = 0

        val height = getScreenHeight(editor)

        var visualTop = visualLine + scrollOffset
        var visualBottom = visualLine + height - scrollOffset
        if (scrollOffset >= height / 2) {
            scrollOffset = height / 2
            visualTop = visualLine + scrollOffset
            visualBottom = visualLine + height - scrollOffset
            if (visualTop == visualBottom) {
                visualBottom++
            }
        }

        var diff: Int
        if (cline < visualTop) {
            diff = cline - visualTop
            sjSize = -sjSize
        } else {
            diff = cline - visualBottom + 1
            if (diff < 0) {
                diff = 0
            }
        }

        if (diff != 0) {
            var line: Int
            // If we need to move the top line more than a half screen worth then we just center the cursor line
            if (Math.abs(diff) > height / 2) {
                line = cline - height / 2 - 1
            } else {
                line = visualLine + diff + sjSize
            }// Otherwise put the new cursor line "scrolljump" lines from the top/bottom

            line = Math.min(line, EditorHelper.getVisualLineCount(editor) - height)
            line = Math.max(0, line)
            scrollLineToTopOfScreen(editor, line)
        }
    }

    private fun scrollColumnToLeftOfScreen(editor: Editor, column: Int) {
        editor.scrollingModel.scrollHorizontally(column * EditorHelper.getColumnWidth(editor))
    }

    fun getColumnWidth(editor: Editor): Int {
        val rect = editor.scrollingModel.visibleArea
        if (rect.width == 0) return 0
        val pt = Point(rect.width, 0)
        val vp = editor.xyToVisualPosition(pt)
        if (vp.column == 0) return 0

        return rect.width / vp.column
    }

    private fun scrollLineToTopOfScreen(editor: Editor, line: Int): Boolean {
        val pos = line * editor.lineHeight
        val verticalPos = editor.scrollingModel.verticalScrollOffset
        editor.scrollingModel.scrollVertically(pos)

        return verticalPos != editor.scrollingModel.verticalScrollOffset
    }

    fun getLineCount(editor: Editor): Int {
        var len = editor.document.lineCount
        if (editor.document.textLength > 0 && editor.document.charsSequence[editor.document.textLength - 1] == '\n') {
            len--
        }

        return len
    }

    fun getVisualLineCount(editor: Editor): Int {
        val count = getLineCount(editor)
        return if (count == 0) 0 else logicalLineToVisualLine(editor, count - 1) + 1
    }

    fun logicalLineToVisualLine(editor: Editor, line: Int): Int {
        return editor.logicalToVisualPosition(LogicalPosition(line, 0)).line
    }


    fun findNextCharacterOnLine(editor: Editor, ch: Char?): Int {
        if (ch == null) {
            return 0
        }

        val line = editor.caretModel.logicalPosition.line
        val start = EditorHelper.getLineStartOffset(editor, line)
        val end = EditorHelper.getLineEndOffset(editor, line)
        val chars = editor.document.charsSequence
        val current = editor.caretModel.offset
        var pos = current + 1
        var find = false
        while (pos >= start && pos < end && pos >= 0 && pos < chars.length) {

            if (chars[pos] == ch) {
                find = true
                break
            }

            if (Character.isLowerCase(ch) && chars[pos] == Character.toUpperCase(ch)) {
                find = true
                break
            }

            pos += 1
        }
        return if (find) pos else current
    }

}
