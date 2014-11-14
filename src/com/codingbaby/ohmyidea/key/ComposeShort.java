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
        stringShortCommandNodeMap.put("gg", new CommandNode("SmartTypeCompletion"));

        stringShortCommandNodeMap.put("gen", new CommandNode("Generate"));
        stringShortCommandNodeMap.put("gv", new CommandNode("IntroduceVariable"));
        stringShortCommandNodeMap.put("gs", new CommandNode("SurroundWith"));
        stringShortCommandNodeMap.put("gy", new CommandNode("EditorDeleteLine"));

        stringShortCommandNodeMap.put("glv", new CommandNode("SplitVertically"));
        stringShortCommandNodeMap.put("glh", new CommandNode("SplitHorizontally"));
        stringShortCommandNodeMap.put("gln", new CommandNode("EditorJoinLines"));

        stringShortCommandNodeMap.put("gh", new CommandNode("HideAllWindows"));
        stringShortCommandNodeMap.put("gp", new CommandNode("NextProjectWindow"));

        stringShortCommandNodeMap.put("gn", new CommandNode("GotoClass"));
        stringShortCommandNodeMap.put("gf", new CommandNode("GotoFile"));

        stringShortCommandNodeMap.put("gtc", new CommandNode("ActivateChangesToolWindow"));
        stringShortCommandNodeMap.put("gts", new CommandNode("ActivateStructureToolWindow"));
        stringShortCommandNodeMap.put("gtp", new CommandNode("ActivateProjectToolWindow"));
        stringShortCommandNodeMap.put("gtt", new CommandNode("ActivateTerminalToolWindow"));


        stringShortCommandNodeMap.put("gk", new CommandNode("EditorCodeBlockEnd"));
        stringShortCommandNodeMap.put("gj", new CommandNode("EditorCodeBlockStart"));




        stringShortCommandNodeMap.put("yy", new CommandNode("$Copy"));
        stringShortCommandNodeMap.put("yc", new CommandNode("$Cut"));
        stringShortCommandNodeMap.put("yd", new CommandNode("EditorDuplicate"));

    }


    public static CommandNode get(String key) {
        return stringShortCommandNodeMap.get(key);
    }
}
