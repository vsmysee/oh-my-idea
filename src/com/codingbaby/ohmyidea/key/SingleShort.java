package com.codingbaby.ohmyidea.key;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 */
public class SingleShort {

    private static Map<KeyStroke, CommandNode> charShort = new HashMap();


    static {

        charShort.put(KeyStroke.getKeyStroke('h'), new CommandNode("EditorLeft"));
        charShort.put(KeyStroke.getKeyStroke('H'), new CommandNode("EditorLineStart"));

        charShort.put(KeyStroke.getKeyStroke('l'), new CommandNode("EditorRight"));
        charShort.put(KeyStroke.getKeyStroke('L'), new CommandNode("EditorLineEnd"));

        charShort.put(KeyStroke.getKeyStroke('j'), new CommandNode("EditorDown"));
        charShort.put(KeyStroke.getKeyStroke('J'), new CommandNode("MotionLastLine"));
        charShort.put(KeyStroke.getKeyStroke('G'), new CommandNode("MotionLastLine"));

        charShort.put(KeyStroke.getKeyStroke('k'), new CommandNode("EditorUp"));
        charShort.put(KeyStroke.getKeyStroke('K'), new CommandNode("MotionFirstLine"));

        charShort.put(KeyStroke.getKeyStroke('t'), new CommandNode("NextTab"));
        charShort.put(KeyStroke.getKeyStroke('T'), new CommandNode("PreviousTab"));

        charShort.put(KeyStroke.getKeyStroke('s'), new CommandNode("NextSplitter"));
        charShort.put(KeyStroke.getKeyStroke('S'), new CommandNode("PrevSplitter"));


        charShort.put(KeyStroke.getKeyStroke('m'), new CommandNode("MethodDown"));
        charShort.put(KeyStroke.getKeyStroke('M'), new CommandNode("MethodUp"));

        charShort.put(KeyStroke.getKeyStroke('w'), new CommandNode("EditorNextWord"));
        charShort.put(KeyStroke.getKeyStroke('W'), new CommandNode("EditorPreviousWord"));


        charShort.put(KeyStroke.getKeyStroke('B'), new CommandNode("Forward"));
        charShort.put(KeyStroke.getKeyStroke('b'), new CommandNode("Back"));


        charShort.put(KeyStroke.getKeyStroke('u'), new CommandNode("$Undo"));
        charShort.put(KeyStroke.getKeyStroke('U'), new CommandNode("EditorToggleCase"));


        charShort.put(KeyStroke.getKeyStroke('r'), new CommandNode("$Redo"));

        charShort.put(KeyStroke.getKeyStroke('x'), new CommandNode("$Delete"));
        charShort.put(KeyStroke.getKeyStroke('X'), new CommandNode("CloseContent"));

        charShort.put(KeyStroke.getKeyStroke('O'), new CommandNode("EditorStartNewLineBefore"));
        charShort.put(KeyStroke.getKeyStroke('o'), new CommandNode("EditorStartNewLine"));

        charShort.put(KeyStroke.getKeyStroke('p'), new CommandNode("$Paste"));

        charShort.put(KeyStroke.getKeyStroke('e'), new CommandNode("EditorSelectWord"));
        charShort.put(KeyStroke.getKeyStroke('E'), new CommandNode("EditorUnSelectWord"));

        charShort.put(KeyStroke.getKeyStroke('q'), new CommandNode("FindUsages"));

        charShort.put(KeyStroke.getKeyStroke('/'), new CommandNode("Find"));
        charShort.put(KeyStroke.getKeyStroke('?'), new CommandNode("Replace"));

        charShort.put(KeyStroke.getKeyStroke('f'), new CommandNode("SmartTypeCompletion"));
        charShort.put(KeyStroke.getKeyStroke('F'), new CommandNode("FindWordAtCaret"));

        charShort.put(KeyStroke.getKeyStroke('n'), new CommandNode("FindNext"));
        charShort.put(KeyStroke.getKeyStroke('N'), new CommandNode("FindPrevious"));


        charShort.put(KeyStroke.getKeyStroke('a'), new CommandNode("MotionAndInsert"));

        charShort.put(KeyStroke.getKeyStroke('d'), new CommandNode("EditorPageDown"));
        charShort.put(KeyStroke.getKeyStroke('D'), new CommandNode("EditorPageUp"));

        charShort.put(KeyStroke.getKeyStroke('.'), new CommandNode("EditorCompleteStatement"));
        charShort.put(KeyStroke.getKeyStroke('>'), new CommandNode("$Copy"));



        charShort.put(KeyStroke.getKeyStroke('<'), new CommandNode("GotoPreviousError"));
        charShort.put(KeyStroke.getKeyStroke(','), new CommandNode("GotoNextError"));


        charShort.put(KeyStroke.getKeyStroke('c'), new CommandNode("ActivateChangesToolWindow"));

        charShort.put(KeyStroke.getKeyStroke(':'), new CommandNode("ShowCommandAction"));

    }

    public static CommandNode get(KeyStroke keyStroke) {
        return  charShort.get(keyStroke);
    }

}
