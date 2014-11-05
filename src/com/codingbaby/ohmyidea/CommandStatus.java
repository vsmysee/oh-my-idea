package com.codingbaby.ohmyidea;

import javax.swing.*;

/**
 *
 *
 */
public class CommandStatus {

    private StringBuffer sb = new StringBuffer();

    public void addChar(char c) {
        sb.append(c);
    }

    public boolean hasStroke() {
        return sb.length() == 1;
    }

    public KeyStroke getStroke() {
        return KeyStroke.getKeyStroke(sb.charAt(0));
    }

    public void reset() {
        sb = new StringBuffer();
    }

    public String getCommand() {
        return sb.toString();
    }
}