package com.codingbaby.ohmyidea.ui;

import com.codingbaby.ohmyidea.CommandNode;
import fun.codecode.KeyHandler;
import com.codingbaby.ohmyidea.script.ShortHolder;
import org.apache.commons.lang.math.NumberUtils;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.ActionEvent;

public class ExEditorKit extends DefaultEditorKit {

    private static ExEditorKit instance;


    public static ExEditorKit getInstance() {
        if (instance == null) {
            instance = new ExEditorKit();
        }

        return instance;
    }

    /**
     * Gets the MIME type of the data that this
     * kit represents support for.
     *
     * @return the type
     */

    public String getContentType() {
        return "text/ohmyidea";
    }

    /**
     * Fetches the set of commands that can be used
     * on a text component that is using a model and
     * view produced by this kit.
     *
     * @return the set of actions
     */
    public Action[] getActions() {
        Action[] res = TextAction.augmentList(super.getActions(), this.exActions);
        return res;
    }


    public static final String CancelEntry = "cancel-entry";
    public static final String CompleteEntry = "complete-entry";
    public static final String EscapeChar = "escape";

    public static final String DeletePreviousChar = "delete-prev-char";
    public static final String DeletePreviousWord = "delete-prev-word";

    public static final String DeleteToCursor = "delete-to-cursor";
    public static final String DeleteFromCursor = "delete-from-cursor";


    protected final Action[] exActions = new Action[]{

            new ExEditorKit.CancelEntryAction(),

            new ExEditorKit.CompleteEntryAction(),

            new ExEditorKit.EscapeCharAction(),

            new ExEditorKit.DeletePreviousCharAction(),

            new ExEditorKit.DeleteToCursorAction(),

            new ExEditorKit.DeleteFromCursorAction(),
    };


    public static class CompleteEntryAction extends TextAction {
        public CompleteEntryAction() {
            super(CompleteEntry);
        }

        public void actionPerformed(ActionEvent actionEvent) {

            final ExEntryPanel entryPanel = ExEntryPanel.getInstance();
            String text = entryPanel.getText();
            entryPanel.cancelExEntry();

            //如果是数字理解为行号
            if (NumberUtils.isNumber(text)) {
                KeyHandler.INSTANCE.setToLine(Integer.parseInt(entryPanel.getText()));
                KeyHandler.INSTANCE.executeAction("OH_MotionToLine", entryPanel.getEntry().getContext());
            } else {
                //如果是单字符理解单字命令
                if (text.length() == 1) {
                    CommandNode commandNode = ShortHolder.INSTANCE.getBottom().get(KeyStroke.getKeyStroke(text.charAt(0)));
                    if (commandNode != null) {
                        KeyHandler.INSTANCE.executeAction(commandNode.asAction(), entryPanel.getEntry().getContext());
                        return;
                    }
                }

            }
        }
    }

    public static class CancelEntryAction extends TextAction {
        public CancelEntryAction() {
            super(CancelEntry);
        }

        public void actionPerformed(ActionEvent e) {
            ExEntryPanel.getInstance().cancelExEntry();

        }
    }

    public static class EscapeCharAction extends TextAction {
        public EscapeCharAction() {
            super(EscapeChar);
        }

        public void actionPerformed(ActionEvent e) {
            ExTextField target = (ExTextField) getTextComponent(e);
            target.escape();
        }
    }

    public static class DeletePreviousCharAction extends TextAction {
        public DeletePreviousCharAction() {
            super(DeletePreviousChar);
        }

        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e) {
            ExTextField target = (ExTextField) getTextComponent(e);

            try {
                Document doc = target.getDocument();
                Caret caret = target.getCaret();
                int dot = caret.getDot();
                int mark = caret.getMark();
                if (dot != mark) {
                    doc.remove(Math.min(dot, mark), Math.abs(dot - mark));
                } else if (dot > 0) {
                    int delChars = 1;

                    if (dot > 1) {
                        String dotChars = doc.getText(dot - 2, 2);
                        char c0 = dotChars.charAt(0);
                        char c1 = dotChars.charAt(1);

                        if (c0 >= '\uD800' && c0 <= '\uDBFF' &&
                                c1 >= '\uDC00' && c1 <= '\uDFFF') {
                            delChars = 2;
                        }
                    }

                    doc.remove(dot - delChars, delChars);
                } else {
                    ExEntryPanel.getInstance().cancelExEntry();
                }
            } catch (BadLocationException bl) {
                // ignore
            }
        }
    }


    public static class DeleteToCursorAction extends TextAction {
        public DeleteToCursorAction() {
            super(DeleteToCursor);
        }

        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e) {
            ExTextField target = (ExTextField) getTextComponent(e);

            Document doc = target.getDocument();
            Caret caret = target.getCaret();
            try {
                doc.remove(0, caret.getDot());
            } catch (BadLocationException ex) {
                // ignore
            }
        }
    }

    public static class DeleteFromCursorAction extends TextAction {
        public DeleteFromCursorAction() {
            super(DeleteFromCursor);
        }

        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e) {
            ExTextField target = (ExTextField) getTextComponent(e);

            Document doc = target.getDocument();
            Caret caret = target.getCaret();
            try {
                doc.remove(caret.getDot(), doc.getLength());
            } catch (BadLocationException ex) {
                // ignore
            }
        }
    }


}
