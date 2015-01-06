package com.codingbaby.ohmyidea.key;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 */
public class SingleShort {

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

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('t'), new CommandNode("NextTab"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('T'), new CommandNode("PreviousTab"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('s'), new CommandNode("NextSplitter"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('S'), new CommandNode("PrevSplitter"));


        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('m'), new CommandNode("MethodDown"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('M'), new CommandNode("MethodUp"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('w'), new CommandNode("EditorNextWord"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('W'), new CommandNode("EditorPreviousWord"));


        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('b'), new CommandNode("JumpToLastChange"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('B'), new CommandNode("Back"));


        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('u'), new CommandNode("$Undo"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('U'), new CommandNode("EditorToggleCase"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('r'), new CommandNode("$Redo"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('x'), new CommandNode("$Delete"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('o'), new CommandNode("EditorStartNewLine"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('p'), new CommandNode("$Paste"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('e'), new CommandNode("EditorSelectWord"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('E'), new CommandNode("EditorUnSelectWord"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('q'), new CommandNode("FindUsages"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('f'), new CommandNode("FindWordAtCaret"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('n'), new CommandNode("FindNext"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('N'), new CommandNode("FindPrevious"));


        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('a'), new CommandNode("MotionAndInsert"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('d'), new CommandNode("EditorPageDown"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('D'), new CommandNode("EditorPageUp"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('.'), new CommandNode("EditorCompleteStatement"));
        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke(','), new CommandNode("GotoNextError"));


        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('z'), new CommandNode("CloseContent"));

        keyStrokeCommandNodeMap.put(KeyStroke.getKeyStroke('c'), new CommandNode("ActivateChangesToolWindow"));

    }

    public static CommandNode get(KeyStroke keyStroke) {
        return  keyStrokeCommandNodeMap.get(keyStroke);
    }

}
