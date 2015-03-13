package com.codingbaby.ohmyidea.key;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 */
public class BottomShort {

    private static Map<KeyStroke, CommandNode> charShort = new HashMap();


    static {

        charShort.put(KeyStroke.getKeyStroke('v'), new CommandNode("SplitVertically"));
        charShort.put(KeyStroke.getKeyStroke('h'), new CommandNode("SplitHorizontally"));
        charShort.put(KeyStroke.getKeyStroke('q'), new CommandNode("CloseContent"));
        charShort.put(KeyStroke.getKeyStroke('s'), new CommandNode("ActivateStructureToolWindow"));
        charShort.put(KeyStroke.getKeyStroke('p'), new CommandNode("ActivateProjectToolWindow"));
        charShort.put(KeyStroke.getKeyStroke('t'), new CommandNode("ActivateTerminalToolWindow"));

    }

    public static CommandNode get(KeyStroke keyStroke) {
        return  charShort.get(keyStroke);
    }

}
