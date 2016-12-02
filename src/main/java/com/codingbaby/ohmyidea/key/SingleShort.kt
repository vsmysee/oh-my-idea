package com.codingbaby.ohmyidea.key

import javax.swing.*

object SingleShort {

    var commandHolder = CommandHolder()

    init {

        commandHolder.add("h", "EditorLeft", "左移动")
        commandHolder.add("H", "EditorLineStart", "左开始")

        commandHolder.add("0", "EditorLineStart", "左开始")

        commandHolder.add("l", "EditorRight", "右移动")
        commandHolder.add("L", "EditorLineEnd", "右结束")
        commandHolder.add("9", "EditorLineEnd", "右结束")

        commandHolder.add("j", "EditorDown", "下移动")
        commandHolder.add("J", "MotionLastLine", "最后一行")

        commandHolder.add("G", "MotionLastLine", "最后一行")

        commandHolder.add("k", "EditorUp", "上移动")
        commandHolder.add("K", "MotionFirstLine", "第一行")

        commandHolder.add("t", "NextTab", "下一个选项卡")
        commandHolder.add("T", "PreviousTab", "前一个选项卡")

        commandHolder.add("s", "NextSplitter", "下一个拆分窗口")
        commandHolder.add("S", "PrevSplitter", "钱一个拆分窗口")


        commandHolder.add("m", "MethodDown", "下一个方法声明")
        commandHolder.add("M", "MethodUp", "前一个方法声明")

        commandHolder.add("w", "EditorNextWord", "下一个单词")
        commandHolder.add("W", "EditorPreviousWord", "前一个单词")


        commandHolder.add("B", "Forward", "回退")
        commandHolder.add("b", "Back", "向前")


        commandHolder.add("u", "\$Undo", "撤销")
        commandHolder.add("U", "EditorToggleCase", "转为大写")


        commandHolder.add("r", "\$Redo", "重做")
        commandHolder.add("R", "CloseContent", "关闭选项卡")

        commandHolder.add("x", "\$Delete", "删除字符")

        commandHolder.add("O", "EditorStartNewLineBefore", "向前开始一个新行")
        commandHolder.add("o", "EditorStartNewLine", "向后开始一个新行")

        commandHolder.add("p", "\$Paste", "粘贴")

        commandHolder.add("e", "EditorSelectWord", "选择一个单词")
        commandHolder.add("E", "EditorUnSelectWord", "取消选择单词")

        commandHolder.add("q", "FindUsages", "查找使用")
        commandHolder.add("Q", "ShowPopupMenu", "模拟鼠标右键")

        commandHolder.add("/", "Find", "查找")
        commandHolder.add("?", "Replace", "替换")

        commandHolder.add("f", "SmartTypeCompletion", "智能提示")
        commandHolder.add("F", "FindWordAtCaret", "查找当前单词")

        commandHolder.add("n", "FindNext", "下一个")
        commandHolder.add("N", "FindPrevious", "前一个")


        commandHolder.add("a", "MotionAndInsert", "追加")


        commandHolder.add(".", "EditorCompleteStatement", "语句完成")
        commandHolder.add(">", "\$Copy", "复制行")



        commandHolder.add("<", "GotoPreviousError", "前一个错误")
        commandHolder.add(",", "GotoNextError", "后一个错误")


        commandHolder.add("c", "ActivateVersionControlToolWindow", "打开版本改动")

        commandHolder.add(":", "ShowCommandAction", "命令底行开启")

        commandHolder.add("I", "NotQuickInsert", "快速非运算")

    }


    operator fun get(keyStroke: KeyStroke): CommandNode? {
        return commandHolder[keyStroke]

    }

}
