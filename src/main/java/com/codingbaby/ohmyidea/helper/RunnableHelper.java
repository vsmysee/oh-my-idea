package com.codingbaby.ohmyidea.helper;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.project.Project;



public class RunnableHelper {

    public static void runReadCommand( Project project,  Runnable cmd,  String name,  Object groupId) {
        CommandProcessor.getInstance().executeCommand(project, new ReadAction(cmd), name, groupId);
    }

    public static void runWriteCommand( Project project,  Runnable cmd,  String name,  Object groupId) {
        CommandProcessor.getInstance().executeCommand(project, new WriteAction(cmd), name, groupId);
    }

    static class ReadAction implements Runnable {
         private final Runnable cmd;

        ReadAction( Runnable cmd) {
            this.cmd = cmd;
        }

        public void run() {
            ApplicationManager.getApplication().runReadAction(cmd);
        }
    }

    static class WriteAction implements Runnable {
         private final Runnable cmd;

        WriteAction( Runnable cmd) {
            this.cmd = cmd;
        }

        public void run() {
            ApplicationManager.getApplication().runWriteAction(cmd);
        }
    }
}
