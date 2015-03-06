package com.codingbaby.ohmyidea;

import javax.swing.*;

/**
 *
 *
 */
public class CommandStatus {

    public static final String CODE_KEY = "'";
    public static final String FORWARD_KEY = ";";
    public static final String LINE_KEY = ":";


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
        if (command.startsWith(CODE_KEY) && sb.length() > 2) {
            return command.substring(1);
        }
        return null;
    }

    public Character getForwardChar() {
        String command = sb.toString();
        if (command.startsWith(FORWARD_KEY) && sb.length() == 2) {
            return command.substring(1).charAt(0);
        }
        return null;
    }

    public String getCommandLineKey() {
        String command = sb.toString();
        if (command.startsWith(LINE_KEY) && command.endsWith(LINE_KEY)  && sb.length() > 1) {
            String line =  command.substring(1);
            return line.substring(0, line.length() - 1);
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