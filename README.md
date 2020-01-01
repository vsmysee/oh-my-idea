Oh My Idea是一个IDEA插件，这个取名模仿了Oh My ZSH

## 为什么要写这个插件

我酷爱IDEA，但是骨子里又离不开VIM和EMACS那种运键如飞和随时随地可编程的特性，所以发明了这个插件，虽然社区已经存在IdeaVim这样的插件，但是它只能理解文本不能理解Java语言。
使用本插件需要理解VIM模式操作, 安装之后，IDE启动时默认进入命令模式，不能输入，只能控制，插件的核心功能就是把IDEA内置的Action放进一个编排上下文，我们可以任意灵活的通过DSL的方式编排这些Action。

## 设计原则

* 最少手指移动
* 最少击键次数
* 灵活编排动作
* 连接外部应用
* 连接其他插件
* 可编程式扩展


## DSL编排

所有控制命令全部用字母和ESC键进行组合,这个组合由一个groovy dsl文件进行定制,并保存在home目录,命名为.oh-my-idea,可以按照需要和习惯进行调整


## 模式种类

类似于vim

* 插入(i)
* 命令(esc)
* 选择(v)
* 动作(a)
* 移动(V)
* 底行(:)


## 技术实现

采用Kotlin静态编程语言加上Groovy DSL，如下是一个简单的配置，表示敲击h，左移动一个字符，敲击H，光标移动到行首。

```
  single {
      key "h", "EditorLeft", "左移动"
      key "H", "EditorLineStart", "左开始"
 }
```

single是一个内置函数，表示单键，另外还有composite(复键)，select（选择），movement（移动），bottom（底行)


## 缺点

不能和IdeaVim共存，需要改变使用快捷键的习惯

比如在我的配置里,J是快速定位到文件尾，而在vim中却是合并行

## 内置关联插件

* [翻译](https://yiiguxing.github.io/TranslationPlugin/)
* [URL导航](https://github.com/viartemev/requestmapper)


## 配置参考(我每天使用)

敲击0表示编译工作区，敲击9表示搜索类，敲击m表示跳转到下一个方法，敲击gd跳转到声明，可以定制所有的动作。

```

//调用快捷键,先敲击a
keyboard {
    key "r", [KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_R]
    key "d", [KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_D]
    key "t", [KeyEvent.VK_CONTROL, KeyEvent.VK_META, KeyEvent.VK_T]
    key "c", [KeyEvent.VK_CONTROL, KeyEvent.VK_META, KeyEvent.VK_C]
}


//单键
single {


    key "0", "CompileDirty", "编译"
    key "1", "FindInPath", "文件中查找"
    key "2", "ReplaceInPath", "文件中替换"

    //GoToRequestMapping连接了Request Mapper插件
    key "3", "GoToRequestMapping", "URL查找[Request mapper插件]"

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


//复键
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

//敲击v，选择模式
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

    key ".", "OH_RepeatCurrentAction", "重复上一个动作"

    key ";", "RunClass", "运行当前类"

    key "c", "EditorToggleColumnMode", "列模式"
    key "s", "SurroundWith", "代码环绕"

}

//敲击大写V，开始移动文本
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


//底行
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

## 如何实现自己的Action

在.oh-my-idea中写入脚本

```

class DeleteToFileEndAction extends EditorAction {

    DeleteToFileEndAction() {

        super(new EditorActionHandler() {
            @Override
            protected void doExecute(@NotNull Editor editor, @Nullable Caret caret, DataContext dataContext) {
                def startPos = editor.getCaretModel().offset
                def endPos = editor.getDocument().textLength
                editor.getDocument().deleteString(startPos, endPos)
            }
        })
    }

}


action {

    reg "OH_EditorDeleteToFileEnd", new DeleteToFileEndAction()

}

```



## 编译构建
```
./gradlew buildPlugin
```

然后从build/distributions里得到安装包进行安装，兼容idea 2014 - 2018