package com.codingbaby.ohmyidea.key;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 */

public class VisualShort {

    private static Map<KeyStroke, CommandNode> charShort = new HashMap();


    static {

        charShort.put(KeyStroke.getKeyStroke('h'), new CommandNode("EditorLeftWithSelection"));
        charShort.put(KeyStroke.getKeyStroke('l'), new CommandNode("EditorRightWithSelection"));
        charShort.put(KeyStroke.getKeyStroke('j'), new CommandNode("EditorDownWithSelection"));
        charShort.put(KeyStroke.getKeyStroke('k'), new CommandNode("EditorUpWithSelection"));

        charShort.put(KeyStroke.getKeyStroke('L'), new CommandNode("EditorLineEndWithSelection"));
        charShort.put(KeyStroke.getKeyStroke('H'), new CommandNode("EditorLineStartWithSelection"));

        charShort.put(KeyStroke.getKeyStroke('p'), new CommandNode("$Paste"));

        charShort.put(KeyStroke.getKeyStroke('U'), new CommandNode("EditorToggleCase"));

        charShort.put(KeyStroke.getKeyStroke('/'), new CommandNode("CommentByBlockComment"));
        charShort.put(KeyStroke.getKeyStroke('x'), new CommandNode("$Delete"));
        charShort.put(KeyStroke.getKeyStroke('r'), new CommandNode("ReformatCode"));
        charShort.put(KeyStroke.getKeyStroke('o'), new CommandNode("OptimizeImports"));

        charShort.put(KeyStroke.getKeyStroke('e'), new CommandNode("JumpToLastChange"));

        charShort.put(KeyStroke.getKeyStroke('>'), new CommandNode("$Copy"));


    }

    public static CommandNode get(KeyStroke keyStroke) {
        return charShort.get(keyStroke);
    }

}