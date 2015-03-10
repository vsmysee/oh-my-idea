package com.codingbaby.ohmyidea.key;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 */
public class MoveShort {

    private static Map<KeyStroke, CommandNode> charShort = new HashMap();

    static {

        charShort.put(KeyStroke.getKeyStroke('h'), new CommandNode("EditorLeft"));
        charShort.put(KeyStroke.getKeyStroke('H'), new CommandNode("EditorLineStart"));

        charShort.put(KeyStroke.getKeyStroke('l'), new CommandNode("EditorRight"));
        charShort.put(KeyStroke.getKeyStroke('L'), new CommandNode("EditorLineEnd"));

        charShort.put(KeyStroke.getKeyStroke('j'), new CommandNode("EditorDown"));
        charShort.put(KeyStroke.getKeyStroke('J'), new CommandNode("MotionLastLine"));

        charShort.put(KeyStroke.getKeyStroke('k'), new CommandNode("EditorUp"));
        charShort.put(KeyStroke.getKeyStroke('K'), new CommandNode("MotionFirstLine"));


        charShort.put(KeyStroke.getKeyStroke('e'), new CommandNode("MoveLineUp"));
        charShort.put(KeyStroke.getKeyStroke('E'), new CommandNode("MoveStatementUp"));

        charShort.put(KeyStroke.getKeyStroke('d'), new CommandNode("MoveLineDown"));
        charShort.put(KeyStroke.getKeyStroke('D'), new CommandNode("MoveStatementDown"));

    }

    public static CommandNode get(KeyStroke keyStroke) {
        return  charShort.get(keyStroke);
    }
}
