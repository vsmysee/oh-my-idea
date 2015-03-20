package com.codingbaby.ohmyidea.key;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 组合命令，必须是两个字符
 */
public class ComposeShort {

    private static Map<String, CommandNode> shortMap = new HashMap();

    static {

        shortMap.put("gd", new CommandNode("GotoDeclaration"));
        shortMap.put("gm", new CommandNode("GotoImplementation"));
        shortMap.put("gu", new CommandNode("GotoSuperMethod"));

        shortMap.put("gg", new CommandNode("MotionFirstLine"));

        shortMap.put("ge", new CommandNode("Generate"));
        shortMap.put("gv", new CommandNode("IntroduceVariable"));
        shortMap.put("gs", new CommandNode("SurroundWith"));
        shortMap.put("gy", new CommandNode("EditorDeleteLine"));


        shortMap.put("gh", new CommandNode("HideAllWindows"));
        shortMap.put("gp", new CommandNode("NextProjectWindow"));
        shortMap.put("gw", new CommandNode("JumpToLastWindow"));


        shortMap.put("gc", new CommandNode("GotoClass"));
        shortMap.put("gn", new CommandNode("Inline"));
        shortMap.put("gf", new CommandNode("GotoFile"));


        //意图
        shortMap.put("gi", new CommandNode("ShowIntentionActions"));


        shortMap.put("yy", new CommandNode("$Copy"));
        shortMap.put("yc", new CommandNode("$Cut"));
        shortMap.put("yd", new CommandNode("EditorDuplicate"));
        shortMap.put("yi", new CommandNode("ImplementMethods"));
        shortMap.put("yb", new CommandNode("CompileDirty"));


        shortMap.put("zc", new CommandNode("CallHierarchy"));
        shortMap.put("zt", new CommandNode("TypeHierarchy"));
        shortMap.put("zm", new CommandNode("MethodHierarchy"));

        shortMap.put("zj", new CommandNode("EditorJoinLines"));
        shortMap.put("zs", new CommandNode("LoadScriptAction"));


        shortMap.put("zf", new CommandNode("ExpandRegion"));
        shortMap.put("zv", new CommandNode("CollapseRegion"));


    }


    public static CommandNode get(String key) {
        return shortMap.get(key);
    }
}
