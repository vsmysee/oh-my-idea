package com.codingbaby.ohmyidea.helper;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 *
 */
public class RunnableHelper {

    public static void runReadCommand(@Nullable Project project, @NotNull Runnable cmd, @Nullable String name, @Nullable Object groupId) {
        CommandProcessor.getInstance().executeCommand(project, new ReadAction(cmd), name, groupId);
    }

    public static void runWriteCommand(@Nullable Project project, @NotNull Runnable cmd, @Nullable String name, @Nullable Object groupId) {
        CommandProcessor.getInstance().executeCommand(project, new WriteAction(cmd), name, groupId);
    }

    static class ReadAction implements Runnable {
        @NotNull private final Runnable cmd;

        ReadAction(@NotNull Runnable cmd) {
            this.cmd = cmd;
        }

        public void run() {
            ApplicationManager.getApplication().runReadAction(cmd);
        }
    }

    static class WriteAction implements Runnable {
        @NotNull private final Runnable cmd;

        WriteAction(@NotNull Runnable cmd) {
            this.cmd = cmd;
        }

        public void run() {
            ApplicationManager.getApplication().runWriteAction(cmd);
        }
    }
}
