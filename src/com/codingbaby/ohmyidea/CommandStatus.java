package com.codingbaby.ohmyidea;

import org.apache.commons.lang.math.NumberUtils;

import javax.swing.*;
import java.util.regex.Pattern;

/**
 *
 *
 */
public class CommandStatus {

    public static final String FORWARD_KEY = ";";

    private Pattern numberActionPattern = Pattern.compile("^\\d+[a-z]$");

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

    public boolean isForward() {
        return sb.toString().startsWith(FORWARD_KEY);
    }

    public Character getForwardChar() {
        String command = sb.toString();
        if (command.startsWith(FORWARD_KEY) && sb.length() == 2) {
            return command.substring(1).charAt(0);
        }
        return null;
    }

    public NumberAction getNumberAction() {
        if (numberActionPattern.matcher(sb.toString()).matches()) {
            String countString = sb.substring(0,sb.length() - 1);
            int count = Integer.parseInt(countString);
            char key = sb.charAt(sb.length()-1);
            NumberAction numberAction = new NumberAction();
            numberAction.setCount(count);
            numberAction.setKey(key);
            return numberAction;
        }
        return null;
    }


    //比如gj，j生效
    public Character getLastChar() {
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