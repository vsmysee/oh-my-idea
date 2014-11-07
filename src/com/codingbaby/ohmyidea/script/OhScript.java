package com.codingbaby.ohmyidea.script;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 *
 *
 */
public class OhScript {

    public static final String OH_FILE = ".oh-my-idea";
    public static final int BUFSIZE = 4096;
    private static final Pattern EOL_SPLIT_PATTERN = Pattern.compile(" *(\r\n|\n)+ *");

    private OhScript() {
    }

    @Nullable
    public static void parseScriptFile() {
        final String homeDirName = System.getProperty("user.home");
        if (homeDirName != null) {
            final File file = new File(homeDirName, OH_FILE);
            if (file.exists()) {
                executeFile(file);
            }
        }
    }

    private static void executeFile(@NotNull File file) {
        final String data;
        try {
            data = readFile(file);
        } catch (IOException ignored) {
            return;
        }
        executeText(data);
    }

    private static void executeText(@NotNull String text) {
        for (String line : EOL_SPLIT_PATTERN.split(text)) {
            int start = line.indexOf("=");
            CodeQuick.add(line.substring(0,start),line.substring(start + 1));
        }
    }

    @NotNull
    private static String readFile(@NotNull File file) throws IOException {
        final BufferedReader reader = new BufferedReader(new FileReader(file));
        final StringBuilder builder = new StringBuilder();
        final char[] buffer = new char[BUFSIZE];
        int n;
        while ((n = reader.read(buffer)) > 0) {
            builder.append(buffer, 0, n);
        }
        return builder.toString();
    }
}
