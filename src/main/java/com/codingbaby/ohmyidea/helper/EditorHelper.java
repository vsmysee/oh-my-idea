package com.codingbaby.ohmyidea.helper;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.LightVirtualFile;

import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class EditorHelper {

    @Nullable
    public static VirtualFile getVirtualFile( Editor editor) {
        return FileDocumentManager.getInstance().getFile(editor.getDocument());
    }

    public static boolean isFileEditor( Editor editor) {
        final VirtualFile virtualFile = getVirtualFile(editor);
        return virtualFile != null && !(virtualFile instanceof LightVirtualFile);
    }

    public static int getLineStartOffset( final Editor editor, final int line) {
        if (line < 0) {
            return 0;
        } else if (line >= getLineCount(editor)) {
            return editor.getDocument().getTextLength();
        } else {
            return editor.getDocument().getLineStartOffset(line);
        }
    }

    public static int getLineEndOffset( final Editor editor, final int line) {
        if (line < 0) {
            return 0;
        } else if (line >= getLineCount(editor)) {
            return editor.getDocument().getTextLength();
        } else {
            return editor.getDocument().getLineEndOffset(line);
        }
    }


    public static int getVisualLineAtTopOfScreen( final Editor editor) {
        int lh = editor.getLineHeight();
        return (editor.getScrollingModel().getVerticalScrollOffset() + lh - 1) / lh;
    }

    public static int getScreenHeight( final Editor editor) {
        int lh = editor.getLineHeight();
        int height = editor.getScrollingModel().getVisibleArea().y +
                editor.getScrollingModel().getVisibleArea().height -
                getVisualLineAtTopOfScreen(editor) * lh;
        return height / lh;
    }

    public static int getVisualColumnAtLeftOfScreen( final Editor editor) {
        int cw = getColumnWidth(editor);
        if (cw == 0) return 0;
        return (editor.getScrollingModel().getHorizontalScrollOffset() + cw - 1) / cw;
    }


    public static int getScreenWidth( final Editor editor) {
        Rectangle rect = editor.getScrollingModel().getVisibleArea();
        Point pt = new Point(rect.width, 0);
        VisualPosition vp = editor.xyToVisualPosition(pt);
        return vp.column;
    }

    public static void scrollCaretIntoView( Editor editor) {

        moveToTop(editor);
        moveToLeft(editor);

    }

    public static void scrollLineToScreenLine( Editor editor) {
        int visualLine = editor.getCaretModel().getVisualPosition().line ;
        scrollLineToTopOfScreen(editor, EditorHelper.normalizeVisualLine(editor, visualLine));
    }

    public static int normalizeVisualLine( final Editor editor, final int line) {
        return Math.max(0, Math.min(line, getVisualLineCount(editor) - 1));
    }

    private static void moveToLeft(Editor editor) {
        int caretColumn = editor.getCaretModel().getVisualPosition().column;
        int visualColumn = EditorHelper.getVisualColumnAtLeftOfScreen(editor);
        int width = EditorHelper.getScreenWidth(editor);

        int scrollOffset = 0;
        int sjSize = width / 2;

        int visualLeft = visualColumn + scrollOffset;
        int visualRight = visualColumn + width - scrollOffset;
        if (scrollOffset >= width / 2) {
            scrollOffset = width / 2;
            visualLeft = visualColumn + scrollOffset;
            visualRight = visualColumn + width - scrollOffset;
            if (visualLeft == visualRight) {
                visualRight++;
            }
        }

        sjSize = Math.min(sjSize, width / 2 - scrollOffset);
        int diff;

        if (caretColumn < visualLeft) {
            diff = caretColumn - visualLeft + 1;
            sjSize = -sjSize;
        } else {
            diff = caretColumn - visualRight + 1;
            if (diff < 0) {
                diff = 0;
            }
        }

        if (diff != 0) {
            int col;
            if (Math.abs(diff) > width / 2) {
                col = caretColumn - width / 2 - 1;
            } else {
                col = visualColumn + diff + sjSize;
            }

            col = Math.max(0, col);
            scrollColumnToLeftOfScreen(editor, col);
        }
    }

    private static void moveToTop(Editor editor) {
        int cline = editor.getCaretModel().getVisualPosition().line;
        int visualLine = EditorHelper.getVisualLineAtTopOfScreen(editor);

        int scrollOffset = 0;
        int sjSize = 0;

        int height = getScreenHeight(editor);

        int visualTop = visualLine + scrollOffset;
        int visualBottom = visualLine + height - scrollOffset;
        if (scrollOffset >= height / 2) {
            scrollOffset = height / 2;
            visualTop = visualLine + scrollOffset;
            visualBottom = visualLine + height - scrollOffset;
            if (visualTop == visualBottom) {
                visualBottom++;
            }
        }

        int diff;
        if (cline < visualTop) {
            diff = cline - visualTop;
            sjSize = -sjSize;
        } else {
            diff = cline - visualBottom + 1;
            if (diff < 0) {
                diff = 0;
            }
        }

        if (diff != 0) {
            int line;
            // If we need to move the top line more than a half screen worth then we just center the cursor line
            if (Math.abs(diff) > height / 2) {
                line = cline - height / 2 - 1;
            }
            // Otherwise put the new cursor line "scrolljump" lines from the top/bottom
            else {
                line = visualLine + diff + sjSize;
            }

            line = Math.min(line, EditorHelper.getVisualLineCount(editor) - height);
            line = Math.max(0, line);
            scrollLineToTopOfScreen(editor, line);
        }
    }

    private static void scrollColumnToLeftOfScreen( Editor editor, int column) {
        editor.getScrollingModel().scrollHorizontally(column * EditorHelper.getColumnWidth(editor));
    }

    public static int getColumnWidth( final Editor editor) {
        Rectangle rect = editor.getScrollingModel().getVisibleArea();
        if (rect.width == 0) return 0;
        Point pt = new Point(rect.width, 0);
        VisualPosition vp = editor.xyToVisualPosition(pt);
        if (vp.column == 0) return 0;

        return rect.width / vp.column;
    }

    private static boolean scrollLineToTopOfScreen( Editor editor, int line) {
        int pos = line * editor.getLineHeight();
        int verticalPos = editor.getScrollingModel().getVerticalScrollOffset();
        editor.getScrollingModel().scrollVertically(pos);

        return verticalPos != editor.getScrollingModel().getVerticalScrollOffset();
    }

    public static int getLineCount( final Editor editor) {
        int len = editor.getDocument().getLineCount();
        if (editor.getDocument().getTextLength() > 0 &&
                editor.getDocument().getCharsSequence().charAt(editor.getDocument().getTextLength() - 1) == '\n') {
            len--;
        }

        return len;
    }

    public static int getVisualLineCount( final Editor editor) {
        int count = getLineCount(editor);
        return count == 0 ? 0 : logicalLineToVisualLine(editor, count - 1) + 1;
    }

    public static int logicalLineToVisualLine( final Editor editor, final int line) {
        return editor.logicalToVisualPosition(new LogicalPosition(line, 0)).line;
    }


    public static int findNextCharacterOnLine( Editor editor, char ch) {
        int line = editor.getCaretModel().getLogicalPosition().line;
        int start = EditorHelper.getLineStartOffset(editor, line);
        int end = EditorHelper.getLineEndOffset(editor, line);
        CharSequence chars = editor.getDocument().getCharsSequence();
        int current = editor.getCaretModel().getOffset();
        int pos = current + 1;
        boolean find = false;
        while (pos >= start && pos < end && pos >= 0 && pos < chars.length()) {

            if (chars.charAt(pos) == ch) {
                find = true;
                break;
            }

            if (Character.isLowerCase(ch) && chars.charAt(pos) == Character.toUpperCase(ch)) {
                find = true;
                break;
            }

            pos += 1;
        }
        return find ? pos : current;
    }

}
