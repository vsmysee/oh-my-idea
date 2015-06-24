package com.codingbaby.ohmyidea.key;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 */
public class MoveShort {

    public static CommandHolder commandHolder  = new CommandHolder();


    static {

        commandHolder.add("h","EditorLeft","左移动");
        commandHolder.add("H","EditorLineStart","左开始");

        commandHolder.add("0","EditorLineStart","左开始");

        commandHolder.add("l","EditorRight","右移动");
        commandHolder.add("L","EditorLineEnd","右结束");
        commandHolder.add("9","EditorLineEnd","右结束");

        commandHolder.add("j","EditorDown","下移动");
        commandHolder.add("J","MotionLastLine","最后一行");

        commandHolder.add("G","MotionLastLine","最后一行");

        commandHolder.add("k","EditorUp","上移动");
        commandHolder.add("K","MotionFirstLine","第一行");


        commandHolder.add("e","MoveLineUp","上移动一行");
        commandHolder.add("E","MoveStatementUp","上移动块");

        commandHolder.add("d","MoveLineDown","下移动行");
        commandHolder.add("D","MoveStatementDown","下移动块");

        commandHolder.add("u","$Undo","撤销");

    }



    public static CommandNode get(KeyStroke keyStroke) {
        return  commandHolder.get(keyStroke);
    }
}
