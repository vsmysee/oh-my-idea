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

    private static Map<Character,String> mapping = new HashMap<Character, String>();


    static {

        mapping.put('h',"EditorLeft");
        mapping.put('H',"EditorLineStart");
        mapping.put('0',"EditorLineStart");

        mapping.put('l',"EditorRight");
        mapping.put('L',"EditorLineEnd");
        mapping.put('9',"EditorLineEnd");

        mapping.put('j',"EditorDown");
        mapping.put('J',"MotionLastLine");
        mapping.put('G',"MotionLastLine");

        mapping.put('k',"EditorUp");
        mapping.put('K',"MotionFirstLine");

        mapping.put('t',"NextTab");
        mapping.put('T',"PreviousTab");

        mapping.put('s',"NextSplitter");
        mapping.put('S',"PrevSplitter");


        mapping.put('m',"MethodDown");
        mapping.put('M',"MethodUp");

        mapping.put('w',"EditorNextWord");
        mapping.put('W',"EditorPreviousWord");


        mapping.put('B',"Forward");
        mapping.put('b',"Back");


        mapping.put('u',"$Undo");
        mapping.put('U',"EditorToggleCase");


        mapping.put('r',"$Redo");
        mapping.put('R',"CloseContent");

        mapping.put('x',"$Delete");

        mapping.put('O',"EditorStartNewLineBefore");
        mapping.put('o',"EditorStartNewLine");

        mapping.put('p',"$Paste");

        mapping.put('e',"EditorSelectWord");
        mapping.put('E',"EditorUnSelectWord");

        mapping.put('q',"FindUsages");

        mapping.put('/',"Find");
        mapping.put('?',"Replace");

        mapping.put('f',"SmartTypeCompletion");
        mapping.put('F',"FindWordAtCaret");

        mapping.put('n',"FindNext");
        mapping.put('N',"FindPrevious");


        mapping.put('a',"MotionAndInsert");

        mapping.put('d',"EditorPageDown");
        mapping.put('D',"EditorPageUp");

        mapping.put('.',"EditorCompleteStatement");
        mapping.put('>',"$Copy");



        mapping.put('<',"GotoPreviousError");
        mapping.put(',',"GotoNextError");


        mapping.put('c',"ActivateChangesToolWindow");

        mapping.put(':',"ShowCommandAction");

        mapping.put('I',"NotQuickInsert");

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
