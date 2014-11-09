package com.codingbaby.ohmyidea;

import javax.swing.*;

/**
 *
 *
 */
public class CommandStatus {

    private StringBuffer sb = new StringBuffer();
    //用于过期未击中的命令
    private long firstType = 0;

    public void addChar(char c) {
        if (firstType == 0) {
            firstType = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - firstType > 1500) {
            reset();
        }
        sb.append(c);
    }

    public boolean hasStroke() {
        return sb.length() == 1;
    }

    public KeyStroke getStroke() {
        return KeyStroke.getKeyStroke(sb.charAt(0));
    }

    public String getCodeKey() {
        String command = sb.toString();
        int p = command.indexOf(";");
        if (p != -1 && sb.length() > 2) {
            return command.substring(p + 1);
        }
        return null;
    }

    public void reset() {
        sb = new StringBuffer();
        firstType = 0;
    }

    public String getCommand() {
        return sb.toString();
    }
}