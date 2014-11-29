package com.codingbaby.ohmyidea;

import javax.swing.*;
import javax.swing.text.ChangedCharSetException;

/**
 *
 *
 */
public class CommandStatus {

    private StringBuffer sb = new StringBuffer();

    //用于过期未击中的命令
    private long firstTypeTime = 0;

    private int timeout = 1500;

    public void addChar(char c) {
        if (firstTypeTime == 0) {
            if (c == ':') {
                timeout = 2500;
            }
            firstTypeTime = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - firstTypeTime > timeout) {
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

    public Character getForwardChar() {
        String command = sb.toString();
        if (command.startsWith(";") && sb.length() == 2) {
            return command.substring(1).charAt(0);
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
        firstTypeTime = 0;
    }

    public String getCommand() {
        return sb.toString();
    }
}