package com.codingbaby.ohmyidea.key;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 */
public class ComposeShort {

    private static Map<String, CommandNode> stringShortCommandNodeMap = new HashMap();

    static {
        stringShortCommandNodeMap.put("gd", new CommandNode("GotoDeclaration"));
        stringShortCommandNodeMap.put("gm", new CommandNode("GotoImplementation"));
        stringShortCommandNodeMap.put("gu", new CommandNode("GotoSuperMethod"));
        stringShortCommandNodeMap.put("gen", new CommandNode("Generate"));
        stringShortCommandNodeMap.put("gv", new CommandNode("IntroduceVariable"));
        stringShortCommandNodeMap.put("gs", new CommandNode("SurroundWith"));
        stringShortCommandNodeMap.put("gy", new CommandNode("EditorDeleteLine"));
        stringShortCommandNodeMap.put("gpv", new CommandNode("SplitVertically"));
        stringShortCommandNodeMap.put("gph", new CommandNode("SplitHorizontally"));

        stringShortCommandNodeMap.put("yy", new CommandNode("$Copy"));
        stringShortCommandNodeMap.put("yc", new CommandNode("$Cut"));
    }


    public static CommandNode get(String key) {
        return stringShortCommandNodeMap.get(key);
    }
}
