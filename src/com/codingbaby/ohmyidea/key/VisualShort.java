package com.codingbaby.ohmyidea.key;

import javax.swing.*;

/**
 *
 *
 */

public class VisualShort {

    public static CommandHolder commandHolder = new CommandHolder();


    static {
        commandHolder.add("h", "EditorLeftWithSelection", "左选择");
        commandHolder.add("l", "EditorRightWithSelection", "右选择");
        commandHolder.add("j", "EditorDownWithSelection", "上选择");
        commandHolder.add("k", "EditorUpWithSelection", "下选择");

        commandHolder.add("H", "EditorLineStartWithSelection", "选择到行首");
        commandHolder.add("L", "EditorLineEndWithSelection", "选择到行尾");
        commandHolder.add("J", "EditorScrollUp", "编辑器上滚动");
        commandHolder.add("K", "EditorScrollDown", "编辑器下滚动");

        commandHolder.add("p", "$Paste", "粘贴");

        commandHolder.add("U", "EditorToggleCase", "转位大写");
        commandHolder.add("u", "$Undo", "撤销");

        commandHolder.add("/", "CommentByBlockComment", "块注释");
        commandHolder.add("x", "$Delete", "删除字符");
        commandHolder.add("r", "ReformatCode", "格式化代码");
        commandHolder.add("o", "OptimizeImports", "优化导入");

        commandHolder.add("e", "JumpToLastChange", "跳转到最近改动");

        commandHolder.add(">", "$Copy", "复制行");
        commandHolder.add("?", "Replace", "替换");

        commandHolder.add("y", "MoveTabRight", "编辑器右移");
        commandHolder.add("d", "MoveTabDown", "编辑器下移");

        commandHolder.add(".", "RepeatCurrentAction", "重复上一个动作");

        commandHolder.add("f", "AceJumpAction", "ACE模式");
        commandHolder.add("a", "AceJumpLineAction", "ACE快速模式");


        commandHolder.add("c", "RunClass", "运行当前类");
        commandHolder.add("b", "DebugClass", "调试当前类");
        commandHolder.add("t", "ToggleTemporaryLineBreakpoint", "临时断点");
        commandHolder.add("g", "StepOver", "行跳过");
        commandHolder.add("i", "StepOver", "行进入");

    }


    public static CommandNode get(KeyStroke keyStroke) {
        return commandHolder.get(keyStroke);
    }

}