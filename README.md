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

All control commands are combined with the letters and ESC keys, and this combination is customized by a groovy DSL file and stored in the home directory, named .oh-my-idea, which can be adjusted as needed and customary.

During starting the IDE, if the plugin can not find the file .oh-my-idea, the plugin will not active.


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
      key "h", "EditorLeft"
      key "H", "EditorLineStart"
 }
```

Single is a built-in function that represents a single key, as well as composite, select, movement, and bottom.


## Disadvantages

Unable to co-exist with IdeaVim, you need to change your mind.


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