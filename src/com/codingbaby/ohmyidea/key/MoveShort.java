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

    private static Map<Character,String> mapping = new HashMap<Character, String>();


    static {

        mapping.put('h',"EditorLeft");
        mapping.put('H',"EditorLineStart");

        mapping.put('l',"EditorRight");
        mapping.put('L',"EditorLineEnd");

        mapping.put('j',"EditorDown");
        mapping.put('J',"MotionLastLine");

        mapping.put('k',"EditorUp");
        mapping.put('K',"MotionFirstLine");


        mapping.put('e',"MoveLineUp");
        mapping.put('E',"MoveStatementUp");

        mapping.put('d',"MoveLineDown");
        mapping.put('D',"MoveStatementDown");

        mapping.put('u',"$Undo");

    }


    static {

        for (Map.Entry<Character, String> entry : mapping.entrySet()) {
            charShort.put(KeyStroke.getKeyStroke(entry.getKey()),new CommandNode(entry.getValue()));
        }

    }

    public static CommandNode get(KeyStroke keyStroke) {
        return  charShort.get(keyStroke);
    }
}
