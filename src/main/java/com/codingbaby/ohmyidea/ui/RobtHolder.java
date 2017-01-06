package com.codingbaby.ohmyidea.ui;

import java.awt.*;


public class RobtHolder {
    public static Robot robot = null;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

}
