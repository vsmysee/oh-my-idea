package com.codingbaby.ohmyidea.key;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 */
public class MoveShort {

    private static Map<KeyStroke, CommandNode> keyStrokeCommandNodeMap = new HashMap();

    static {

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('h'), new CommandNode("EditorLeft"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('H'), new CommandNode("EditorLineStart"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('l'), new CommandNode("EditorRight"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('L'), new CommandNode("EditorLineEnd"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('j'), new CommandNode("EditorDown"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('J'), new CommandNode("MotionLastLine"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('k'), new CommandNode("EditorUp"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('K'), new CommandNode("MotionFirstLine"));


        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('e'), new CommandNode("MoveLineUp"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('E'), new CommandNode("MoveStatementUp"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('d'), new CommandNode("MoveLineDown"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('D'), new CommandNode("MoveStatementDown"));

    }

    public static CommandNode get(KeyStroke keyStroke) {
        return  keyStrokeCommandNodeMap.get(keyStroke);
    }
}
