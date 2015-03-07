
package com.codingbaby.ohmyidea.ui;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorFontType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.Keymap;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.util.Date;

/**
 * Provides a custom keymap for the text field. The keymap is the VIM Ex command keymapping
 */
public class ExTextField extends JTextField {
  /**
   */
  public ExTextField() {
    Font font = EditorColorsManager.getInstance().getGlobalScheme().getFont(EditorFontType.PLAIN);
    setFont(font);

    // Do not override getActions() method, because it is has side effect: propogates these actions to defaults.
    final Action[] actions = ExEditorKit.getInstance().getActions();
    final ActionMap actionMap = getActionMap();
    for (Action a : actions) {
      actionMap.put(a.getValue(Action.NAME), a);
      //System.out.println("  " + a.getValue(Action.NAME));
    }

    setInputMap(WHEN_FOCUSED, new InputMap());
    Keymap map = addKeymap("ex", getKeymap());
    loadKeymap(map, ExKeyBindings.getBindings(), actions);
    map.setDefaultAction(new ExEditorKit.DefaultExKeyHandler());
    setKeymap(map);
    addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        setCaretPosition(getText().length());
      }

      @Override
      public void focusLost(FocusEvent e) {
      }
    });

    //origCaret = getCaret();
    //blockCaret = new BlockCaret();
  }

  public void setType(@NotNull String type) {
    String hkey = null;
    switch (type.charAt(0)) {
      case '/':
      case '?':
        break;
      case ':':
        break;
    }

    if (hkey != null) {
    }
  }

  public void saveLastEntry() {
    lastEntry = getText();
  }



  void setEditor(Editor editor, DataContext context) {
    this.editor = editor;
    this.context = context;
  }

  public Editor getEditor() {
    return editor;
  }

  public DataContext getContext() {
    return context;
  }

  public void handleKey(@NotNull KeyStroke stroke) {
    if (logger.isDebugEnabled()) logger.debug("stroke=" + stroke);
    final char keyChar = stroke.getKeyChar();
    char c = keyChar;
    final int modifiers = stroke.getModifiers();
    final int keyCode = stroke.getKeyCode();
    if ((modifiers & KeyEvent.CTRL_MASK) != 0) {
      final int codePoint = keyCode - KeyEvent.VK_A + 1;
      if (codePoint > 0) {
        c = Character.toChars(codePoint)[0];
      }
    }
    KeyEvent event = new KeyEvent(this, keyChar != KeyEvent.CHAR_UNDEFINED ? KeyEvent.KEY_TYPED :
                                        (stroke.isOnKeyRelease() ? KeyEvent.KEY_RELEASED : KeyEvent.KEY_PRESSED),
                                  (new Date()).getTime(), modifiers, keyCode, c);

    super.processKeyEvent(event);
  }

  public void updateText(String string) {
    super.setText(string);
  }

  public void setText(String string) {
    super.setText(string);

    saveLastEntry();
  }

  protected void processKeyEvent(KeyEvent e) {
    if (logger.isDebugEnabled()) logger.debug("key=" + e);
    super.processKeyEvent(e);
  }

  /**
   * Creates the default implementation of the model
   * to be used at construction if one isn't explicitly
   * given.  An instance of <code>PlainDocument</code> is returned.
   *
   * @return the default model implementation
   */
  @NotNull
  protected Document createDefaultModel() {
    return new ExDocument();
  }

  public void escape() {
    if (currentAction != null) {
      currentAction = null;
    }
    else {
      ExEntryPanel.getInstance().cancelExEntry();
    }
  }

  public void setCurrentAction(@Nullable Action action) {
    this.currentAction = action;
  }

  @Nullable
  public Action getCurrentAction() {
    return currentAction;
  }

  public void toggleInsertReplace() {
    ExDocument doc = (ExDocument)getDocument();
    doc.toggleInsertReplace();


  }



  private Editor editor;
  private DataContext context;
  private String lastEntry;
  private int histIndex = 0;
  @Nullable private Action currentAction;




  private static final Logger logger = Logger.getInstance(ExTextField.class.getName());
}
