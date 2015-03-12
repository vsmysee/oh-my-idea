package com.codingbaby.ohmyidea.key;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 */
public class ComposeShort {

    private static Map<String, CommandNode> shortMap = new HashMap();

    static {

        shortMap.put("gd", new CommandNode("GotoDeclaration"));
        shortMap.put("gm", new CommandNode("GotoImplementation"));
        shortMap.put("gu", new CommandNode("GotoSuperMethod"));
        shortMap.put("gg", new CommandNode("SmartTypeCompletion"));

        shortMap.put("ge", new CommandNode("Generate"));
        shortMap.put("gv", new CommandNode("IntroduceVariable"));
        shortMap.put("gs", new CommandNode("SurroundWith"));
        shortMap.put("gy", new CommandNode("EditorDeleteLine"));


        shortMap.put("gln", new CommandNode("EditorJoinLines"));
        shortMap.put("gll", new CommandNode("LoadScriptAction"));

        shortMap.put("gh", new CommandNode("HideAllWindows"));
        shortMap.put("gp", new CommandNode("NextProjectWindow"));
        shortMap.put("gw", new CommandNode("JumpToLastWindow"));


        shortMap.put("gc", new CommandNode("GotoClass"));
        shortMap.put("gn", new CommandNode("Inline"));
        shortMap.put("gf", new CommandNode("GotoFile"));


        shortMap.put("gts", new CommandNode("ActivateStructureToolWindow"));
        shortMap.put("gtp", new CommandNode("ActivateProjectToolWindow"));
        shortMap.put("gtt", new CommandNode("ActivateTerminalToolWindow"));
        shortMap.put("gtr", new CommandNode("ActivateRunToolWindow"));
        shortMap.put("gtd", new CommandNode("ActivateDebugToolWindow"));

        shortMap.put("gbk", new CommandNode("ToggleLineBreakpoint"));

        //意图
        shortMap.put("gi", new CommandNode("ShowIntentionActions"));

        shortMap.put("gk", new CommandNode("EditorCodeBlockEnd"));
        shortMap.put("gj", new CommandNode("EditorCodeBlockStart"));

        shortMap.put("yy", new CommandNode("$Copy"));
        shortMap.put("yc", new CommandNode("$Cut"));
        shortMap.put("yd", new CommandNode("EditorDuplicate"));
        shortMap.put("yi", new CommandNode("ImplementMethods"));
        shortMap.put("yb", new CommandNode("CompileDirty"));


        shortMap.put("yhc", new CommandNode("CallHierarchy"));
        shortMap.put("yht", new CommandNode("TypeHierarchy"));
        shortMap.put("yhm", new CommandNode("MethodHierarchy"));

    }


    public static CommandNode get(String key) {
        return shortMap.get(key);
    }
}
