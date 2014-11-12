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

    private int timeout = 1500;

    public void addChar(char c) {
        if (firstType == 0) {
            if (c == ':') {
                timeout = 2500;
            }
            firstType = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - firstType > timeout) {
            reset();
        }
        sb.append(c);
    }

    public boolean isWaiting() {
        return sb.length() == 0;
    }

    public boolean hasStroke() {
        return sb.length() == 1;
    }

    public KeyStroke getStroke() {
        return KeyStroke.getKeyStroke(sb.charAt(0));
    }

    public String getCodeKey() {
        String command = sb.toString();
        if (command.startsWith(";") && sb.length() > 2) {
            return command.substring(1);
        }
        return null;
    }

    public String getCommandLineKey() {
        String command = sb.toString();
        if (command.startsWith(":")  && sb.length() > 1) {
            return command.substring(1);
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