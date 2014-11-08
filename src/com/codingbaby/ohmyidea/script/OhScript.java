package com.codingbaby.ohmyidea.script;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 *
 */
public class OhScript {

    public static final String OH_FILE = ".oh-my-idea";
    public static final int BUFSIZE = 4096;
    private static final Pattern EOL_SPLIT_PATTERN = Pattern.compile("(\r\n|\n)");
    private static LineHolder holder;

    private OhScript() {
    }

    @Nullable
    public static void parseScriptFile() {
        final String homeDirName = System.getProperty("user.home");
        if (homeDirName != null) {
            final File file = new File(homeDirName, OH_FILE);
            if (file.exists()) {
                List<CodeKV> codeKVs = ParseTokens(file);
                for (CodeKV codeKV : codeKVs) {
                    CodeQuick.add(codeKV.key, codeKV.value);
                }
            }
        }
    }

    private static List<CodeKV> ParseTokens(@NotNull File file) {
        String data = "";
        try {
            data = readFile(file);
        } catch (IOException ignored) {
            Collections.emptyList();
        }
        List<CodeKV> tokens = new ArrayList<CodeKV>();
        String[] lines = EOL_SPLIT_PATTERN.split(data);
        holder = new LineHolder(lines);
        String line = holder.next();
        statements(tokens, line);
        return tokens;
    }

    private static void statements(List<CodeKV> tokens, String line) {
        if (Pattern.compile("[a-zA-Z0-9]").matcher(line).find()) {
            int start = line.indexOf("=>");
            //发现有映射
            if (start != -1) {
                String key = line.substring(0, start).trim();
                String value = line.substring(start + 2);
                if (StringUtils.isNotBlank(value)) {
                    tokens.add(new CodeKV(key, value.trim()));
                } else {
                    parseCodeBlock(tokens, key);
                }
                if (holder.done()) {
                    return;
                }
                statements(tokens, holder.next());
            }
        } else if (!holder.done()) {
            statements(tokens, holder.next());
        }
    }

    private static void parseCodeBlock(List<CodeKV> tokens, String key) {
        String line = holder.next();
        if (line.startsWith("```")) {
            StringBuffer sb = new StringBuffer();
            String next = holder.next();
            while (!next.startsWith("```")) {
                sb.append(next);
                sb.append("\n");
                next = holder.next();
            }
            tokens.add(new CodeKV(key, sb.toString()));
        }
    }


    private static class LineHolder {

        private String[] lines;
        private int begin = 0;

        public LineHolder(String[] lines) {
            this.lines = lines;
        }

        public String next() {
            String line = lines[begin];
            begin++;
            return line;
        }

        public boolean done() {
            return begin == lines.length;
        }
    }

    private static class CodeKV {
        String key;
        String value;

        private CodeKV(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        OhScript.parseScriptFile();
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
