
package com.codingbaby.ohmyidea.ui;

import com.codingbaby.ohmyidea.KeyHandler;
import com.codingbaby.ohmyidea.OhPlugin;
import com.codingbaby.ohmyidea.helper.RunnableHelper;
import com.codingbaby.ohmyidea.script.CodeQuick;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 *
 */
public class ExEditorKit extends DefaultEditorKit {
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
    @NotNull
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
        if (logger.isDebugEnabled()) logger.debug("res.length=" + res.length);

        return res;
    }

    /**
     * Creates an uninitialized text storage model
     * that is appropriate for this type of editor.
     *
     * @return the model
     */
    @NotNull
    public Document createDefaultDocument() {
        return new ExDocument();
    }

    @Nullable
    public static KeyStroke convert(@NotNull ActionEvent event) {
        String cmd = event.getActionCommand();
        int mods = event.getModifiers();
        if (cmd != null && cmd.length() > 0) {
            char ch = cmd.charAt(0);
            if (ch < ' ') {
                if (mods == KeyEvent.CTRL_MASK) {
                    return KeyStroke.getKeyStroke(KeyEvent.VK_A + ch - 1, mods);
                }
            } else {
                return KeyStroke.getKeyStroke(new Character(ch), mods);
            }
        }

        return null;
    }

    public static final String DefaultExKey = "default-ex-key";
    public static final String CancelEntry = "cancel-entry";
    public static final String CompleteEntry = "complete-entry";
    public static final String EscapeChar = "escape";
    public static final String DeletePreviousChar = "delete-prev-char";
    public static final String DeletePreviousWord = "delete-prev-word";
    public static final String DeleteToCursor = "delete-to-cursor";
    public static final String DeleteFromCursor = "delete-from-cursor";
    public static final String ToggleInsertReplace = "toggle-insert";
    public static final String InsertRegister = "insert-register";
    public static final String InsertWord = "insert-word";
    public static final String InsertWORD = "insert-WORD";
    public static final String HistoryUp = "history-up";
    public static final String HistoryDown = "history-down";
    public static final String HistoryUpFilter = "history-up-filter";
    public static final String HistoryDownFilter = "history-down-filter";
    public static final String StartDigraph = "start-digraph";

    @NotNull
    protected final Action[] exActions = new Action[]{
            new ExEditorKit.CancelEntryAction(),
            new ExEditorKit.CompleteEntryAction(),
            new ExEditorKit.EscapeCharAction(),
            new ExEditorKit.DeletePreviousCharAction(),
            new ExEditorKit.DeleteToCursorAction(),
            new ExEditorKit.DeleteFromCursorAction(),
            new ExEditorKit.HistoryUpAction(),
            new ExEditorKit.HistoryDownAction(),
            new ExEditorKit.HistoryUpFilterAction(),
            new ExEditorKit.HistoryDownFilterAction(),
            new ExEditorKit.ToggleInsertReplaceAction(),
            new InsertRegisterAction(),
    };

    public static class DefaultExKeyHandler extends DefaultKeyTypedAction {
        public void actionPerformed(@NotNull ActionEvent e) {
            ExTextField target = (ExTextField) getTextComponent(e);
            final Action currentAction = target.getCurrentAction();
            if (currentAction != null) {
                currentAction.actionPerformed(e);
            } else {
                KeyStroke key = convert(e);
                if (key != null) {
                    final char c = key.getKeyChar();
                    if (c > 0) {
                        ActionEvent event = new ActionEvent(e.getSource(), e.getID(), "" + c, e.getWhen(), e.getModifiers());
                        super.actionPerformed(event);
                        target.saveLastEntry();
                    }
                } else {
                    super.actionPerformed(e);

                    target.saveLastEntry();
                }
            }
        }
    }

    public static class HistoryUpAction extends TextAction {
        public HistoryUpAction() {
            super(HistoryUp);
        }

        public void actionPerformed(ActionEvent actionEvent) {
            ExTextField target = (ExTextField) getTextComponent(actionEvent);
        }
    }

    public static class HistoryDownAction extends TextAction {
        public HistoryDownAction() {
            super(HistoryDown);
        }

        public void actionPerformed(ActionEvent actionEvent) {
            ExTextField target = (ExTextField) getTextComponent(actionEvent);
        }
    }

    public static class HistoryUpFilterAction extends TextAction {
        public HistoryUpFilterAction() {
            super(HistoryUpFilter);
        }

        public void actionPerformed(ActionEvent actionEvent) {
            ExTextField target = (ExTextField) getTextComponent(actionEvent);
        }
    }

    public static class HistoryDownFilterAction extends TextAction {
        public HistoryDownFilterAction() {
            super(HistoryDownFilter);
        }

        public void actionPerformed(ActionEvent actionEvent) {
            ExTextField target = (ExTextField) getTextComponent(actionEvent);
        }
    }

    public static class InsertRegisterAction extends TextAction {
        private static enum State {
            SKIP_CTRL_R,
            WAIT_REGISTER,
        }

        @NotNull
        private State state = State.SKIP_CTRL_R;

        public InsertRegisterAction() {
            super(InsertRegister);
        }

        public void actionPerformed(@NotNull ActionEvent e) {
            final ExTextField target = (ExTextField) getTextComponent(e);
            final KeyStroke key = convert(e);
            if (key != null) {
                switch (state) {
                    case SKIP_CTRL_R:
                        state = State.WAIT_REGISTER;
                        target.setCurrentAction(this);
                        break;
                    case WAIT_REGISTER:
                        state = State.SKIP_CTRL_R;
                        target.setCurrentAction(null);
                        final char c = key.getKeyChar();
                }
            }
        }
    }

    public static class CompleteEntryAction extends TextAction {
        public CompleteEntryAction() {
            super(CompleteEntry);
        }

        public void actionPerformed(ActionEvent actionEvent) {
            logger.debug("complete entry");
            KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

            final ExEntryPanel entryPanel = ExEntryPanel.getInstance();
            String text = entryPanel.getText();
            entryPanel.cancelExEntry();

            if (NumberUtils.isNumber(text)) {
                KeyHandler.toLine = Integer.parseInt(entryPanel.getText());
                KeyHandler.executeAction("MotionToLine", entryPanel.getEntry().getContext());
            } else {
                final Editor editor = entryPanel.getEntry().getEditor();
                Project project = editor.getProject();
                final OhPlugin oh = OhPlugin.getInstance();

                final String mapping = CodeQuick.getMapping(text);
                if (mapping != null) {
                    Runnable cmd = new Runnable() {
                        @Override
                        public void run() {
                            int oldOffset = editor.getCaretModel().getOffset();
                            editor.getDocument().insertString(oldOffset, mapping);
                            oh.commandStatus.reset();
                            KeyHandler.executeAction("ReformatCode", entryPanel.getEntry().getContext());
                        }
                    };
                    RunnableHelper.runWriteCommand(project, cmd, "insertCode", cmd);
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
            target.saveLastEntry();

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
            target.saveLastEntry();

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
            target.saveLastEntry();

            Document doc = target.getDocument();
            Caret caret = target.getCaret();
            try {
                doc.remove(caret.getDot(), doc.getLength());
            } catch (BadLocationException ex) {
                // ignore
            }
        }
    }

    public static class ToggleInsertReplaceAction extends TextAction {
        public ToggleInsertReplaceAction() {
            super(ToggleInsertReplace);

            logger.debug("ToggleInsertReplaceAction()");
        }

        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e) {
            logger.debug("actionPerformed");
            ExTextField target = (ExTextField) getTextComponent(e);
            target.toggleInsertReplace();
        }
    }


    private static ExEditorKit instance;

    private static final Logger logger = Logger.getInstance(ExEditorKit.class.getName());
}
