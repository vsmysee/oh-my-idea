package com.codingbaby.ohmyidea.key;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 */
public class VisualShort {

    private static Map<KeyStroke, CommandNode> keyStrokeCommandNodeMap = new HashMap();


    static {

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('h'), new CommandNode("EditorLeftWithSelection"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('l'), new CommandNode("EditorRightWithSelection"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('j'), new CommandNode("EditorDownWithSelection"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('k'), new CommandNode("EditorUpWithSelection"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('p'), new CommandNode("$Paste"));

    }

    public static CommandNode get(KeyStroke keyStroke) {
        return  keyStrokeCommandNodeMap.get(keyStroke);
    }

}
