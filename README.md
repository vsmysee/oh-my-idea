Oh My Idea is an Idea plugin inspired by Oh My ZSH

## Why this plug-in ?

I'm a big fan of IDEA, but I can't get away from VIM and EMACS 'features of flying keys and being programmable anytime, anywhere. Therefore, I invented this plug-in. Although there is a plug-in like IdeaVim in the community, it can only understand text but not the Java language.

You need to understand the operation in VIM mode to use this plug-in. After installation, the IDE starts up in command mode by default, which cannot be input but can only be controlled. The core function of the plug-in is to put the built-in Action of IDEA into a choreography context.

## Design principles

* minimum finger movement
* minimum number of keystrokes
* flexible choreography
* connect to external applications
* connect to other plug-ins
* programmable extensions


## DSL choreography

All control commands are combined with the letters and ESC keys, and this combination is customized by a groovy DSL file and stored in the home directory, named .oh-my-idea, which can be adjusted as needed and customary


Patterns List

Similar to the vim

* insertion (I)
* command (esc)
* choose (v)
* action (a)
* mobile (V)
* end of line (:)


## technology


Using the Kotlin static programming language and the Groovy script as DSL, here's a simple configuration that represents hitting h, moving a character to the left, hitting h and moving the cursor to the beginning of the line.

```
  single {
      key "h", "EditorLeft", "move left"
      key "H", "EditorLineStart", "move to line start"
 }
```

Single is a built-in function that represents a single key, as well as composite, select, movement, and bottom.


## disadvantages

Unable to co-exist with IdeaVim, you need to change your mind.


## My DSL

single {


    key "0", "CompileDirty", "编译"
    key "1", "FindInPath", "文件中查找"
    key "2", "ReplaceInPath", "文件中替换"

    key "4", "\$Cut", "剪切"
    key "5", "EditorDuplicate", "复制行"
    key "6", "IntroduceVariable", "提取变量"

    key "7", "EditorDeleteLine", "删除行"


    key "9", "GotoClass", "导航到类"
    key "8", "GotoFile", "导航到文件"



    key "`", "NextProjectWindow", "下一个项目窗口"
    key "=", "OH_ShowHelpDialog", "显示帮助"
    key "-", "ShowPopupMenu", "模拟鼠标右键"



    key "h", "EditorLeft", "左移动"
    key "H", "EditorLineStart", "左开始"


    key "l", "EditorRight", "右移动"
    key "L", "EditorLineEnd", "右结束"


    key "j", "EditorDown", "下移动"
    key "J", "OH_MotionLastLine", "最后一行"

    key "k", "EditorUp", "上移动"
    key "K", "OH_MotionFirstLine", "第一行"

    key "t", "NextTab", "下一个选项卡"
    key "T", "PreviousTab", "前一个选项卡"

    key "s", "NextSplitter", "下一个拆分窗口"
    key "S", "PrevSplitter", "钱一个拆分窗口"

    key "m", "MethodDown", "下一个方法声明"
    key "M", "MethodUp", "前一个方法声明"

    key "w", "EditorNextWord", "下一个单词"
    key "W", "EditorPreviousWord", "前一个单词"

    key "B", "Forward", "回退"
    key "b", "Back", "向前"

    key "u", "\$Undo", "撤销"
    key "U", "EditorToggleCase", "转为大写"

    key "r", "\$Redo", "重做"
    key "R", "CloseContent", "关闭选项卡"

    key "x", "\$Delete", "删除字符"

    key "O", "EditorStartNewLineBefore", "向前开始一个新行"
    key "o", "EditorStartNewLine", "向后开始一个新行"

    key "p", "\$Paste", "粘贴"

    key "e", "EditorSelectWord", "选择一个单词"
    key "E", "EditorUnSelectWord", "取消选择单词"

    key "q", "FindUsages", "查找使用"

    key "/", "Find", "查找"
    key "?", "Replace", "替换"

    key "f", "SmartTypeCompletion", "智能提示"
    key "F", "FindWordAtCaret", "查找当前单词"

    key "n", "FindNext", "下一个"
    key "N", "FindPrevious", "前一个"

    key ".", "EditorCompleteStatement", "语句完成"
    key ">", "\$Copy", "复制"

    key "<", "GotoPreviousError", "前一个错误"
    key ",", "GotoNextError", "后一个错误"

    key "c", "ActivateVersionControlToolWindow", "打开版本改动"

    key ":", "OH_ShowCommandAction", "命令底行开启"

    key "G", "OH_ShowUIToggleActions", "控制UI"

}


composite {

    key "d;", "DebugClass", "调试当前类"

    key "gh", "HideAllWindows", "隐藏窗口"

    key "gd", "GotoDeclaration", "到声明"
    key "gm", "GotoImplementation", "到实现"
    key "gu", "GotoSuperMethod", "到超类"

    key "ge", "Generate", "生成代码"
    key "gs", "SurroundWith", "代码环绕"

    key "de", "EditorDeleteToLineEnd", "删除到行尾"

    key "gw", "JumpToLastWindow", "最近窗口"

    key "gn", "Inline", "合并变量"

    key "gi", "ShowIntentionActions", "显示意图"



    key "yi", "ImplementMethods", "实现方法"
    key "yo", "OverrideMethods", "重载方法"


    key "zt", "OH_ScrollToTop", "当前行置顶"

    key "zj", "EditorJoinLines", "合并行"
    key "zs", "OH_LoadScriptAction", "加载模板"

    key "zf", "ExpandRegion", "打开折叠"
    key "zv", "CollapseRegion", "折叠代码"

    key "gr", "RenameElement", "重命名"


    key "ys", "Switcher", "选择组件"

}

select {

    key "h", "EditorLeftWithSelection", "左选择"
    key "l", "EditorRightWithSelection", "右选择"
    key "j", "EditorDownWithSelection", "上选择"
    key "k", "EditorUpWithSelection", "下选择"

    key "H", "EditorLineStartWithSelection", "选择到行首"
    key "L", "EditorLineEndWithSelection", "选择到行尾"
    key "J", "EditorScrollUp", "编辑器上滚动"
    key "K", "EditorScrollDown", "编辑器下滚动"

    key "p", "\$Paste", "粘贴"

    key "U", "EditorToggleCase", "转位大写"
    key "u", "\$Undo", "撤销"

    key "/", "CommentByBlockComment", "块注释"
    key "x", "\$Delete", "删除字符"
    key "r", "ReformatCode", "格式化代码"
    key "o", "OptimizeImports", "优化导入"

    key "e", "JumpToLastChange", "跳转到最近改动"

    key ">", "\$Copy", "复制行"
    key "?", "Replace", "替换"

    key "n", "MoveTabRight", "编辑器右移"
    key "d", "MoveTabDown", "编辑器下移"


    key ";", "RunClass", "运行当前类"

    key "c", "EditorToggleColumnMode", "列模式"
    key "s", "SurroundWith", "代码环绕"

}

movement {

    key "h", "EditorLeft", "左移动"
    key "H", "EditorLineStart", "左开始"

    key "0", "EditorLineStart", "左开始"

    key "l", "EditorRight", "右移动"
    key "L", "EditorLineEnd", "右结束"
    key "9", "EditorLineEnd", "右结束"

    key "j", "EditorDown", "下移动"
    key "J", "OH_MotionLastLine", "最后一行"

    key "k", "EditorUp", "上移动"
    key "K", "OH_MotionFirstLine", "第一行"

    key "e", "MoveLineUp", "上移动一行"
    key "E", "MoveStatementUp", "上移动块"

    key "d", "MoveLineDown", "下移动行"
    key "D", "MoveStatementDown", "下移动块"

    key "u", "\$Undo", "撤销"
}


bottom {

    key "v", "SplitVertically", "水平拆分"
    key "h", "SplitHorizontally", "垂直拆分"

    key "s", "ActivateStructureToolWindow", "显示结构Tool"
    key "S", "ActivateStructureToolWindow", "显示结构Tool"

    key "p", "ActivateProjectToolWindow", "显示项目Tool"
    key "P", "ActivateProjectToolWindow", "显示项目Tool"

    key "t", "ActivateTerminalToolWindow", "显示终端Tool"
    key "T", "ActivateTerminalToolWindow", "显示终端Tool"

    key "r", "ActivateRunToolWindow", "显示运行Tool"
    key "R", "ActivateRunToolWindow", "显示运行Tool"

    key "b", "ActivateDebugToolWindow", "显示调试Tool"
    key "B", "ActivateDebugToolWindow", "显示调试Tool"

    key "d", "ActivateTODOToolWindow", "显示终端TODO"
    key "D", "ActivateTODOToolWindow", "显示终端TODO"

    key "<", "EditorCodeBlockStart", "到代码块开始"
    key ">", "EditorCodeBlockEnd", "到代码块结束"

    key "n", "EditorToggleShowLineNumbers", "定位到行"

}

```

## Implement your actions


```

class DeleteToFileEndAction extends EditorAction {

    DeleteToFileEndAction() {
        super(new Handler())
    }

    static class Handler extends EditorActionHandler {
        @Override
        protected void doExecute(@NotNull Editor editor, @Nullable Caret caret, DataContext dataContext) {
            def startPos = editor.getCaretModel().offset
            def endPos = editor.getDocument().textLength
            editor.getDocument().deleteString(startPos, endPos)
        }
    }

}


action {

    reg "OH_EditorDeleteToFileEnd", new DeleteToFileEndAction()

}

```



## How to build

```
./gradlew buildPlugin
```

And then get the installation package from the build/distributions for installation, compatible with the idea of 2015-2019