package com.codingbaby.ohmyidea.ui;

import com.codingbaby.ohmyidea.script.OhScript;
import com.codingbaby.ohmyidea.script.RobotHandler;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by baby on 16/12/24.
 */
public class RobtHolder {


    public static Robot robot = null;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        OhScript.INSTANCE.loadScriptFile();

        System.out.println(RobotHandler.holder.size());

        java.util.List<Integer> c =
                RobotHandler.holder.get("t");
        if (c != null) {
            for (int i : c) {
                RobtHolder.robot.keyPress(i);
            }
            for (int i : c) {
                RobtHolder.robot.keyRelease(i);
            }
        }

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_META);
        robot.keyPress(KeyEvent.VK_T);

        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_META);
        robot.keyRelease(KeyEvent.VK_T);
        Thread.sleep(10000000);
    }


}
