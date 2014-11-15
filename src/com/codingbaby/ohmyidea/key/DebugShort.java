package com.codingbaby.ohmyidea.key;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 */
public class DebugShort {

    private static Map<KeyStroke, CommandNode> keyStrokeCommandNodeMap = new HashMap();

    static {

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('j'), new CommandNode("StepOver"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('J'), new CommandNode("StepOut"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('k'), new CommandNode("StepInto"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('g'), new CommandNode("Resume"));

    }

    public static CommandNode get(KeyStroke keyStroke) {
        return keyStrokeCommandNodeMap.get(keyStroke);
    }

}

