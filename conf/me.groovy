import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

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


class NotQuickInsertAction extends EditorAction {

    NotQuickInsertAction() {
        super(new Handler())
    }

    static class Handler extends EditorActionHandler {
        @Override
        protected void doExecute(@NotNull Editor editor, @Nullable Caret caret, DataContext dataContext) {
            def oldOffset = editor.caretModel.offset
            editor.document.insertString(oldOffset, "!")
        }
    }
}

action {

    reg "OH_W_EditorDeleteToFileEnd", new DeleteToFileEndAction()
    reg "OH_W_NotQuickInsert", new NotQuickInsertAction()

}


single {

    key "1", "FindInPath", "文件中查找"
    key "2", "ReplaceInPath", "文件中替换"
    key "4", "\$Cut", "剪切"
    key "5", "EditorDuplicate", "复制行"
    key "6", "IntroduceVariable", "提取变量"
    key "7", "EditorDeleteLine", "删除行"
    key "8", "GotoFile", "导航到文件"
    key "9", "GotoClass", "导航到类"
    key "0", "CompileDirty", "编译"

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
    key "S", "PrevSplitter", "前一个拆分窗口"

    key "m", "MethodDown", "下一个方法声明"
    key "M", "MethodUp", "前一个方法声明"

    key "w", "EditorNextWord", "下一个单词"
    key "W", "EditorPreviousWord", "前一个单词"

    key "B", "Forward", "回退"
    key "b", "Back", "向前"

    key "u", "\$Undo", "撤销"
    key "U", "EditorToggleCase", "大小写转换"

    key "r", "\$Redo", "重做"
    key "R", "CloseContent", "关闭选项卡"

    key "x", "\$Delete", "删除字符"

    key "o", "EditorStartNewLine", "向后开始一个新行"

    key "p", "\$Paste", "粘贴"

    key "e", "EditorSelectWord", "选择一个单词"
    key "E", "EditorUnSelectWord", "取消选择单词"


    key "/", "Find", "查找"
    key "?", "Replace", "替换"

    key "F", "FindWordAtCaret", "查找当前单词"

    key "n", "FindNext", "下一个"
    key "N", "FindPrevious", "前一个"

    key ".", "EditorCompleteStatement", "语句完成"
    key ">", "\$Copy", "复制"

    key "<", "GotoPreviousError", "前一个错误"
    key ",", "GotoNextError", "后一个错误"

    key "c", "ActivateVersionControlToolWindow", "打开版本改动"

    key ":", "OH_ShowCommandAction", "命令底行开启"

    key ";", "RunClass", "运行当前类"

    key "I", "OH_W_NotQuickInsert", "快速非运算"

    key "G", "OH_ShowUIToggleActions", "控制UI"

    key "`", "ToggleZenMode", "Zen"

}


composite {

    key "d;", "DebugClass", "调试当前类"


    key "gh", "HideAllWindows", "隐藏窗口"


    key "gl", "ReformatCode", "格式化代码"


    key "gd", "GotoDeclaration", "到声明"
    key "gm", "GotoImplementation", "到实现"
    key "gu", "GotoSuperMethod", "到超类"

    key "ge", "Generate", "生成代码"
    key "gs", "SurroundWith", "代码环绕"

    key "de", "EditorDeleteToLineEnd", "删除到行尾"
    key "dG", "OH_W_EditorDeleteToFileEnd", "删除到文件尾"

    key "gw", "JumpToLastWindow", "最近窗口"

    key "gn", "Inline", "合并变量"

    key "gi", "ShowIntentionActions", "显示意图"


    key "yi", "ImplementMethods", "实现方法"
    key "yo", "OverrideMethods", "重载方法"


    key "zt", "OH_ScrollToTop", "当前行置顶"

    key "zj", "EditorJoinLines", "合并行"


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

    key "U", "EditorToggleCase", "转大写"
    key "u", "\$Undo", "撤销"

    key "/", "CommentByBlockComment", "块注释"
    key "x", "\$Delete", "删除字符"
    key "o", "OptimizeImports", "优化导入"

    key "e", "JumpToLastChange", "跳转到最近改动"

    key ">", "\$Copy", "复制行"
    key "?", "Replace", "替换"

    key "n", "MoveTabRight", "编辑器右移"
    key "d", "MoveTabDown", "编辑器下移"


    key "c", "EditorToggleColumnMode", "列模式"
    key "s", "SurroundWith", "代码环绕"

}

movement {

    key "h", "EditorLeft", "左移动"
    key "H", "EditorLineStart", "左开始"

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

    key "c", "ActivateCommitToolWindow", "显示提交tool"

    key "p", "ActivateProjectToolWindow", "显示项目Tool"

    key "t", "ActivateTerminalToolWindow", "显示终端Tool"

    key "r", "ActivateRunToolWindow", "显示运行Tool"

    key "b", "ActivateDebugToolWindow", "显示调试Tool"

    key "d", "ActivateTODOToolWindow", "显示终端TODO"

    key "g", "ActivateGradleToolWindow", "现实Gradle"

    key "<", "EditorCodeBlockStart", "到代码块开始"

    key ">", "EditorCodeBlockEnd", "到代码块结束"

    key "n", "EditorToggleShowLineNumbers", "定位行"

    key "q", "FindUsages", "查找使用"

    key "f", "SmartTypeCompletion", "智能提示"

    key "l", "OH_LoadScriptAction", "加载模板"

}