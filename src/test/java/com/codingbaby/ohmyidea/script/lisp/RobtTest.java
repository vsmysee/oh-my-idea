package com.codingbaby.ohmyidea.script.lisp;

import com.codingbaby.ohmyidea.ui.RobtHolder;

import java.awt.event.KeyEvent;

public class RobtTest {

    public static void main(String[] args) {


        RobtHolder.INSTANCE.getRobot().keyPress(KeyEvent.VK_CONTROL);
        RobtHolder.INSTANCE.getRobot().keyPress(KeyEvent.VK_ALT);
        RobtHolder.INSTANCE.getRobot().keyPress(KeyEvent.VK_R);


        RobtHolder.INSTANCE.getRobot().keyRelease(KeyEvent.VK_CONTROL);
        RobtHolder.INSTANCE.getRobot().keyRelease(KeyEvent.VK_ALT);
        RobtHolder.INSTANCE.getRobot().keyRelease(KeyEvent.VK_R);


    }

}
