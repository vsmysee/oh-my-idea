package com.codingbaby.ohmyidea.script

import com.codingbaby.ohmyidea.script.lisp.EnvBuilder
import com.codingbaby.ohmyidea.script.lisp.Evaler
import com.codingbaby.ohmyidea.script.lisp.FormReader
import com.intellij.openapi.util.io.FileUtil
import groovy.lang.Binding
import groovy.lang.GroovyClassLoader
import org.apache.commons.lang.StringUtils
import org.codehaus.groovy.runtime.InvokerHelper
import java.io.File
import java.io.IOException
import java.util.*


object OhScript {

    val OH_FILE = ".oh-my-idea"

    var content = """
(do

(single

    [:h :EditorLeft :左移动]
    [:H :EditorLineStart :左开始]

    [:0 :EditorLineStart :左开始]

    [:l :EditorRight :右移动]
    [:L :EditorLineEnd :右结束]
    [:9 :EditorLineEnd :右结束]

    [:j :EditorDown :下移动]
    [:J :OH_MotionLastLine :最后一行]

    [:G :OH_MotionLastLine :最后一行]

    [:k :EditorUp :上移动]
    [:K :OH_MotionFirstLine :第一行]

    [:t :NextTab :下一个选项卡]
    [:T :PreviousTab :前一个选项卡]

    [:s :NextSplitter :下一个拆分窗口]
    [:S :PrevSplitter :钱一个拆分窗口]

    [:m :MethodDown :下一个方法声明]
    [:M :MethodUp :前一个方法声明]

    [:w :EditorNextWord :下一个单词]
    [:W :EditorPreviousWord :前一个单词]

    [:B :Forward :回退]
    [:b :Back :向前]

    [:u :${'$'}Undo :撤销]
    [:U :EditorToggleCase :转为大写]

    [:r :${'$'}Redo :重做]
    [:R :CloseContent :关闭选项卡]

    [:x :${'$'}Delete :删除字符]

    [:O :EditorStartNewLineBefore :向前开始一个新行]
    [:o :EditorStartNewLine :向后开始一个新行]

    [:p :${'$'}Paste :粘贴]

    [:e :EditorSelectWord :选择一个单词]
    [:E :EditorUnSelectWord :取消选择单词]

    [:q :FindUsages :查找使用]
    [:Q :ShowPopupMenu :模拟鼠标右键]

    [:/ :Find :查找]
    [:? :Replace :替换]

    [:f :SmartTypeCompletion :智能提示]
    [:F :FindWordAtCaret :查找当前单词]

    [:n :FindNext :下一个]
    [:N :FindPrevious :前一个]

    [:. :EditorCompleteStatement :语句完成]
    [:> :${'$'}Copy :复制行]

    [:< :GotoPreviousError :前一个错误]
    [:, :GotoNextError :后一个错误]

    [:c :ActivateVersionControlToolWindow :打开版本改动]

    [:: :OH_ShowCommandAction :命令底行开启]

    [:I :OH_NotQuickInsert :快速非运算])


(composite
     [:gd :GotoDeclaration :到声明]
     [:gm :GotoImplementation :到实现]
     [:gu :GotoSuperMethod :到超类]

     [:gg :OH_MotionFirstLine :到第一行]

     [:ge :Generate :生成代码]
     [:gv :IntroduceVariable :提取变量]
     [:gs :SurroundWith :代码环绕]

     [:dd :EditorDeleteLine :删除行]
     [:de :EditorDeleteToLineEnd :删除到行尾]

     [:dG :OH_EditorDeleteToFileEnd :删除到行尾]

     [:gh :HideAllWindows :隐藏窗口]
     [:gp :NextProjectWindow :下一个项目窗口]
     [:gw :JumpToLastWindow :最近窗口]

     [:go :GotoClass :导航到类]
     [:gn :Inline :合并变量]
     [:gf :GotoFile :导航到文件]

     [:ye :OH_EditCodeTemplate :编辑代码模板]

     [:gi :ShowIntentionActions :显示意图]

     [:yc :${'$'}Cut :剪切]
     [:yd :EditorDuplicate :复制行]

     [:yi :ImplementMethods :实现方法]
     [:yo :OverrideMethods :重载方法]

     [:yb :CompileDirty :编译]

     [:zt :OH_ScrollToTop :当前行置顶]

     [:zj :EditorJoinLines :合并行]
     [:zs :OH_LoadScriptAction :加载模板]

     [:zf :ExpandRegion :打开折叠]
     [:zv :CollapseRegion :折叠代码]

     [:gr :RenameElement :重命名]

     [:yr :ReplaceInPath :文件中替换]
     [:yf :FindInPath :文件中查找]

     [:ys :Switcher :选择组件]
     [:yv :OH_ShowUIToggleActions :控制UI])


(select

    [:h :EditorLeftWithSelection :左选择]
       [:l :EditorRightWithSelection :右选择]
       [:j :EditorDownWithSelection :上选择]
       [:k :EditorUpWithSelection :下选择]

       [:H :EditorLineStartWithSelection :选择到行首]
       [:L :EditorLineEndWithSelection :选择到行尾]
       [:J :EditorScrollUp :编辑器上滚动]
       [:K :EditorScrollDown :编辑器下滚动]

       [:p :${'$'}Paste :粘贴]

       [:U :EditorToggleCase :转位大写]
       [:u :${'$'}Undo :撤销]

       [:/ :CommentByBlockComment :块注释]
       [:x :${'$'}Delete :删除字符]
       [:r :ReformatCode :格式化代码]
       [:o :OptimizeImports :优化导入]

       [:e :JumpToLastChange :跳转到最近改动]

       [:> :${'$'}Copy :复制行]
       [:? :Replace :替换]

       [:n :MoveTabRight :编辑器右移]
       [:d :MoveTabDown :编辑器下移]

       [:. :OH_RepeatCurrentAction :重复上一个动作]

       [:c :RunClass :运行当前类]
       [:b :DebugClass :调试当前类]
       [:t :ToggleTemporaryLineBreakpoint :临时断点]
       [:g :StepOver :行跳过]
       [:i :StepOver :行进入])


(movement

 [:h :EditorLeft :左移动]
    [:H :EditorLineStart :左开始]

    [:0 :EditorLineStart :左开始]

    [:l :EditorRight :右移动]
    [:L :EditorLineEnd :右结束]
    [:9 :EditorLineEnd :右结束]

    [:j :EditorDown :下移动]
    [:J :OH_MotionLastLine :最后一行]

    [:G :OH_MotionLastLine :最后一行]

    [:k :EditorUp :上移动]
    [:K :OH_MotionFirstLine :第一行]

    [:e :MoveLineUp :上移动一行]
    [:E :MoveStatementUp :上移动块]

    [:d :MoveLineDown :下移动行]
    [:D :MoveStatementDown :下移动块]

    [:u :${'$'}Undo :撤销])


(bottom

    [:v :SplitVertically :水平拆分]
    [:h :SplitHorizontally :垂直拆分]

    [:s :ActivateStructureToolWindow :显示结构Tool]
    [:S :ActivateStructureToolWindow :显示结构Tool]

    [:p :ActivateProjectToolWindow :显示项目Tool]
    [:P :ActivateProjectToolWindow :显示项目Tool]

    [:t :ActivateTerminalToolWindow :显示终端Tool]
    [:T :ActivateTerminalToolWindow :显示终端Tool]

    [:r :ActivateRunToolWindow :显示运行Tool]
    [:R :ActivateRunToolWindow :显示运行Tool]

    [:b :ActivateDebugToolWindow :显示调试Tool]
    [:B :ActivateDebugToolWindow :显示调试Tool]

    [:d :ActivateTODOToolWindow :显示终端TODO]
    [:D :ActivateTODOToolWindow :显示终端TODO]

    [:< :EditorCodeBlockStart :到代码块开始]
    [:> :EditorCodeBlockEnd :到代码块结束]

    [:n :EditorToggleShowLineNumbers :显示行号]

    [:k :OH_ShowHelpDialog :显示帮助]
    [:K :OH_ShowHelpDialog :显示帮助])


)
"""


    fun loadGroovyScriptFile() {
        if (try {
            Class.forName("groovy.lang.GroovyClassLoader")
            false
        } catch (e: Exception) {
            true
        }) {
            val env = EnvBuilder.globalEnv()
            Evaler.eval(FormReader.readForm(content), env)
            return
        }

        val content = loadContent(OH_FILE)

        val groovyClassLoader = GroovyClassLoader()
        val scriptClass = groovyClassLoader.parseClass(GroovyDef.groovy + content)

        var codes = ArrayList<HashMap<String, String>>()
        var robotHolder = HashMap<String, List<Int>>()

        var bind = Binding()
        bind.setVariable("envList", codes)
        bind.setVariable("envMap", robotHolder)

        ShortHolder.clear()

        bind.setVariable("smode", ShortHolder.single)
        bind.setVariable("vmode", ShortHolder.select)
        bind.setVariable("mmode", ShortHolder.movement)
        bind.setVariable("cmode", ShortHolder.compose)
        bind.setVariable("bmode", ShortHolder.bottom)

        InvokerHelper.createScript(scriptClass, bind).run()

        RobotHandler.holder.clear()
        RobotHandler.holder.putAll(robotHolder)

        CodeSnippet.clear()

        for (map in codes) {
            CodeSnippet.desc.put(map["key"] as String, map["desc"] as String)
            CodeSnippet.code.put(map["key"] as String, map["code"] as String)
        }
    }


    fun saveScript(text: String) {
        if (StringUtils.isNotBlank(text)) {
            val homeDirName = System.getProperty("user.home")
            if (homeDirName != null) {
                val file = File(homeDirName, OH_FILE)
                try {
                    FileUtil.writeToFile(file, text)
                    loadGroovyScriptFile()
                } catch (e: IOException) {
                }

            }
        }
    }

    fun loadContent(path: String): String {
        val homeDirName = System.getProperty("user.home")
        if (homeDirName != null) {
            val file = File(homeDirName, path)
            if (file.exists()) {
                try {
                    return file.readText(Charsets.UTF_8)
                } catch (e: IOException) {
                    return ""
                }
            }
        }
        return ""
    }


}
