package com.codingbaby.ohmyidea;

import javax.swing.*;

/**
 *
 *
 */
public class CommandStatus {

    public static final String FORWARD_KEY = ";";

    //command buffer
    private StringBuffer sb = new StringBuffer();

    public void addChar(char c) {
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

    public Character getForwardChar() {
        String command = sb.toString();
        if (command.startsWith(FORWARD_KEY) && sb.length() == 2) {
            return command.substring(1).charAt(0);
        }
        return null;
    }


    //比如gj，j生效
    public  Character getLastChar() {
        if (sb.length() > 1) {
            return sb.charAt(sb.length() - 1);
        }
        return null;
    }


    public void reset() {
        sb = new StringBuffer();
    }

    public String getCommand() {
        return sb.toString();
    }
}