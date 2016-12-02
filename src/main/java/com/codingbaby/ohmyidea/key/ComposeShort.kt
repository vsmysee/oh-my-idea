package com.codingbaby.ohmyidea.key

import com.codingbaby.ohmyidea.OhPlugin

/**
 * 组合命令，必须是两个字符
 */
object ComposeShort {

    var commandHolder = CommandHolder()

    init {

        commandHolder.add("gd", "GotoDeclaration", "到声明")
        commandHolder.add("gm", "GotoImplementation", "到实现")
        commandHolder.add("gu", "GotoSuperMethod", "到超类")

        commandHolder.add("gg", "MotionFirstLine", "到第一行")

        commandHolder.add("ge", "Generate", "生成代码")
        commandHolder.add("gv", "IntroduceVariable", "提取变量")
        commandHolder.add("gs", "SurroundWith", "代码环绕")


        commandHolder.add("dd", "EditorDeleteLine", "删除行")
        commandHolder.add("de", "EditorDeleteToLineEnd", "删除到行尾")
        commandHolder.add("dG", "EditorDeleteToFileEnd", "删除到行尾")


        commandHolder.add("gh", "HideAllWindows", "隐藏窗口")
        commandHolder.add("gp", "NextProjectWindow", "下一个项目窗口")
        commandHolder.add("gw", "JumpToLastWindow", "最近窗口")


        commandHolder.add("go", "GotoClass", "导航到类")
        commandHolder.add("gn", "Inline", "合并变量")
        commandHolder.add("gf", "GotoFile", "导航到文件")


        commandHolder.add("yn", "NullCheckInsert", "插入null判断")


        commandHolder.add("ye", "EditCodeTemplate", "编辑代码模板")


        //意图
        commandHolder.add("gi", "ShowIntentionActions", "显示意图")


        commandHolder.add("yc", "\$Cut", "剪切")
        commandHolder.add("yd", "EditorDuplicate", "复制行")

        commandHolder.add("yi", "ImplementMethods", "实现方法")
        commandHolder.add("yo", "OverrideMethods", "重载方法")

        commandHolder.add("yb", "CompileDirty", "编译")


        commandHolder.add("zt", "ScrollToTop", "当前行置顶")


        commandHolder.add("zj", "EditorJoinLines", "合并行")
        commandHolder.add("zs", "LoadScriptAction", "加载模板")


        commandHolder.add("zf", "ExpandRegion", "打开折叠")
        commandHolder.add("zv", "CollapseRegion", "折叠代码")

        commandHolder.add("gr", "RenameElement", "重命名")


        //这两个命令比较特殊,需要让打开的窗口的命令模式失效
        commandHolder.add("yr", "ReplaceInPath", "文件中替换")
        commandHolder.add("yf", "FindInPath", "文件中查找")


        commandHolder.add("ys", "Switcher", "选择组件")
        commandHolder.add("yv", "ShowUIToggleActions", "控制UI")
    }


    operator fun get(key: String): CommandNode? {
        if (key == "yr" || key == "yf") {
            OhPlugin.setPopWindowOpen(true)
        }
        return commandHolder[key]
    }
}
