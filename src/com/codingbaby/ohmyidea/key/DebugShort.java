package com.codingbaby.ohmyidea.key;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 */
public class DebugShort {

    private static Map<KeyStroke, CommandNode> charShort = new HashMap();

    static {

        charShort.put(KeyStroke.getKeyStroke('j'), new CommandNode("StepOver"));
        charShort.put(KeyStroke.getKeyStroke('J'), new CommandNode("StepOut"));
        charShort.put(KeyStroke.getKeyStroke('k'), new CommandNode("StepInto"));
        charShort.put(KeyStroke.getKeyStroke('g'), new CommandNode("Resume"));

    }

    public static CommandNode get(KeyStroke keyStroke) {
        return charShort.get(keyStroke);
    }

}

