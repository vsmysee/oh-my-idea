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
    
    private static Map<Character,String> mapping = new HashMap<Character, String>();
    
    static {
        mapping.put('h', "EditorLeftWithSelection");
        mapping.put('l',"EditorRightWithSelection");
        mapping.put('j',"EditorDownWithSelection");
        mapping.put('k',"EditorUpWithSelection");

        mapping.put('L',"EditorLineEndWithSelection");
        mapping.put('H',"EditorLineStartWithSelection");

        mapping.put('p',"$Paste");

        mapping.put('U',"EditorToggleCase");

        mapping.put('/',"CommentByBlockComment");
        mapping.put('x',"$Delete");
        mapping.put('r',"ReformatCode");
        mapping.put('o',"OptimizeImports");

        mapping.put('e',"JumpToLastChange");

        mapping.put('>',"$Copy");
        mapping.put('?',"Replace");

        mapping.put('u',"$Undo");

    }


    static {

        for (Map.Entry<Character, String> entry : mapping.entrySet()) {
            charShort.put(KeyStroke.getKeyStroke(entry.getKey()),new CommandNode(entry.getValue()));
        }

    }

    public static CommandNode get(KeyStroke keyStroke) {
        return charShort.get(keyStroke);
    }

}