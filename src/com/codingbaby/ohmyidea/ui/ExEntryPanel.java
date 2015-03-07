package com.codingbaby.ohmyidea.ui;

/**
 * Created by baby on 15/3/7.
 */

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * This is used to enter ex commands such as searches and "colon" commands
 */
public class ExEntryPanel extends JPanel {

    public boolean cancelExEntry() {
        ExEntryPanel panel = ExEntryPanel.getInstance();
        panel.deactivate(true);
        return true;
    }

    public static ExEntryPanel getInstance() {
        if (instance == null) {
            instance = new ExEntryPanel();
        }

        return instance;
    }

    private ExEntryPanel() {
        setBorder(BorderFactory.createEtchedBorder());
        label = new JLabel(" ");
        entry = new ExTextField();
        entry.setBorder(null);

        setFontForElements();

        setForeground(entry.getForeground());
        setBackground(entry.getBackground());

        label.setForeground(entry.getForeground());
        label.setBackground(entry.getBackground());

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        setLayout(layout);
        gbc.gridx = 0;
        layout.setConstraints(this.label, gbc);
        add(this.label);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        layout.setConstraints(entry, gbc);
        add(entry);
        setBorder(BorderFactory.createEtchedBorder());

        adapter = new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                positionPanel();
            }
        };
    }

    private void setFontForElements() {
        final Font font = getEditorFont();
        label.setFont(font);
        entry.setFont(font);
    }

    public static Font getEditorFont() {
        final EditorColorsScheme scheme = EditorColorsManager.getInstance().getGlobalScheme();
        return new Font(scheme.getEditorFontName(), Font.PLAIN, scheme.getEditorFontSize());
    }

    /**
     * Turns on the ex entry field for the given editor
     *
     * @param editor   The editor to use for display
     * @param context  The data context
     * @param label    The label for the ex entry (i.e. :, /, or ?)
     * @param initText The initial text for the entry
     * @param count    A holder for the ex entry count
     */
    public void activate(@NotNull Editor editor, DataContext context, @NotNull String label, String initText, int count) {
        entry.setEditor(editor, context);
        this.label.setText(label);
        this.count = count;
        setFontForElements();
        entry.setDocument(entry.createDefaultModel());
        entry.setText(initText);
        entry.setType(label);
        parent = editor.getContentComponent();
        if (!ApplicationManager.getApplication().isUnitTestMode()) {
            JRootPane root = SwingUtilities.getRootPane(parent);
            oldGlass = (JComponent)root.getGlassPane();
            oldLayout = oldGlass.getLayout();
            wasOpaque = oldGlass.isOpaque();
            oldGlass.setLayout(null);
            oldGlass.setOpaque(false);
            oldGlass.add(this);
            oldGlass.addComponentListener(adapter);
            positionPanel();
            oldGlass.setVisible(true);
            entry.requestFocusInWindow();
        }
        active = true;
    }

    /**
     * Gets the label for the ex entry. This should be one of ":", "/", or "?"
     *
     * @return The ex entry label
     */
    public String getLabel() {
        return label.getText();
    }

    /**
     * Gets the count given during activation
     *
     * @return The count
     */
    public int getCount() {
        return count;
    }

    /**
     * Pass the keystroke on to the text edit for handling
     *
     * @param stroke The keystroke
     */
    public void handleKey(@NotNull KeyStroke stroke) {
        entry.handleKey(stroke);
    }

    private void positionPanel() {
        if (parent == null) return;

        Container scroll = SwingUtilities.getAncestorOfClass(JScrollPane.class, parent);
        int height = (int)getPreferredSize().getHeight();
        if (scroll != null) {
            Rectangle bounds = scroll.getBounds();
            bounds.translate(0, scroll.getHeight() - height);
            bounds.height = height;
            Point pos = SwingUtilities.convertPoint(scroll.getParent(), bounds.getLocation(), oldGlass);
            bounds.setLocation(pos);
            setBounds(bounds);
            repaint();
        }
    }

    /**
     * Gets the text entered by the user. This includes any initial text but does not include the label
     *
     * @return The user entered text
     */
    public String getText() {
        return entry.getText();
    }

    @NotNull
    public ExTextField getEntry() {
        return entry;
    }

    /**
     * Turns off the ex entry field and optionally puts the focus back to the original component
     */
    public void deactivate(boolean refocusOwningEditor) {
        logger.info("deactivate");
        if (!active) return;
        active = false;
        if (!ApplicationManager.getApplication().isUnitTestMode()) {
            if (refocusOwningEditor && parent != null) {
                parent.requestFocus();
            }

            oldGlass.removeComponentListener(adapter);
            oldGlass.setVisible(false);
            oldGlass.remove(this);
            oldGlass.setOpaque(wasOpaque);
            oldGlass.setLayout(oldLayout);
        }
        parent = null;
    }

    /**
     * Checks if the ex entry panel is currently active
     *
     * @return true if active, false if not
     */
    public boolean isActive() {
        return active;
    }

    @Nullable private JComponent parent;
    @NotNull private final JLabel label;
    @NotNull private final ExTextField entry;
    private JComponent oldGlass;
    private LayoutManager oldLayout;
    private boolean wasOpaque;
    @NotNull private final ComponentAdapter adapter;
    private int count;

    private boolean active;

    private static ExEntryPanel instance;

    private static final Logger logger = Logger.getInstance(ExEntryPanel.class.getName());
}
