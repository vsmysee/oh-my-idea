package com.codingbaby.ohmyidea;

import com.intellij.openapi.application.ApplicationManager;
import fun.codecode.OhPlugin;

public class Oh {
    public static OhPlugin get() {
        return ApplicationManager.getApplication().getComponent(OhPlugin.class);
    }
}
