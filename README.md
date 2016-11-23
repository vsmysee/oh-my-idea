Oh My Idea是一个IDEA插件，这个取名模仿了Oh My ZSH，用这个插件可以在IDEA中神乎其神的编写代码.

## 为什么要写这个插件
在MaxOS或者Linux下开发，你或许会用VIM或者Emacs，给人的感觉就是快,IDEA是Java开发的最好IDE，如何在里面找到VIM的感觉？于是有了这个插件
虽然IDEA官方已经存在了VIM插件，但是我在写Java代码的时候不希望用VIM的操作方式。


## 操作约定
以字母的大小写表达反向和加速操作，比如t是正向tab切换,T就是反向tab切换,l是右移，那么L就是移到行尾.以下这是部分快捷键

~~~ java
'h' 左移动
'H' 移动到行首
'l' 右移动
'L' 移动到行尾
't' 'T' Tab切换
'gh' 隐藏Tool
'yb' 编译
',' 跳转到错误
'vc' 运行当前类
'vb' 调试当前类
~~~


## 模式种类
命令,插入,可视


![](https://github.com/vsmysee/oh-my-idea/blob/master/ohmyidea.gif?raw=true)


## 编译
./gradlew buildPlugin
