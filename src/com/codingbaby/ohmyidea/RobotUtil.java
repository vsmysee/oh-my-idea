package com.codingbaby.ohmyidea;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by baby on 15/4/5.
 */
public class RobotUtil {

    private static Robot robot;


    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void keyDown() {
        robot.keyPress(KeyEvent.VK_DOWN);
    }


}
