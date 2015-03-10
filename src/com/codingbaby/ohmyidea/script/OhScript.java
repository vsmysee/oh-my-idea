package com.codingbaby.ohmyidea.script;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * _ls
 * List<String> list = new ArrayList<String>();
 * <p/>
 * _mso
 * Map<String,Object> map = new HashMap<String,Object>();
 */
public class OhScript {

    public static final String OH_FILE = ".oh-my-idea";

    public static final int BUFSIZE = 4096;

    private static final Pattern EOL_SPLIT_PATTERN = Pattern.compile("(\r\n|\n)");

    private static LineHolder holder;

    private OhScript() {
    }

    public static void loadScriptFile() {
        final String homeDirName = System.getProperty("user.home");
        if (homeDirName != null) {
            final File file = new File(homeDirName, OH_FILE);
            if (file.exists()) {
                parseTokens(file);

                List<CodeKV> codeKV = holder.getCodeKV();
                for (CodeKV kv : codeKV) {
                    CodeQuick.add(kv.key, kv.value);
                }
            }
        }
    }

    private static void parseTokens(@NotNull File file) {
        String data = "";
        try {
            data = readFile(file);
        } catch (IOException ignored) {
            Collections.emptyList();
        }
        String[] lines = EOL_SPLIT_PATTERN.split(data);
        holder = new LineHolder(lines);
        holder.buildCodeQuick(holder.next());
    }

    private static class LineHolder {
        private String[] lines;
        private int begin = 0;
        private List<String> keys = new ArrayList<String>();
        private List<String> values = new ArrayList<String>();

        public LineHolder(String[] lines) {
            this.lines = lines;
        }

        public List<CodeKV> getCodeKV() {
            List<CodeKV> list = new ArrayList<CodeKV>();
            for (int i = 0; i < keys.size(); i++) {
                list.add(new CodeKV(keys.get(i), values.get(i)));
            }
            return list;
        }

        public void buildCodeQuick(String line) {
            if (StringUtils.isNotBlank(line) && line.startsWith("_")) {
                keys.add(line.trim().substring(1));
                StringBuilder sb = new StringBuilder();
                line = next();

                while (!line.startsWith("_")) {
                    sb.append(line);
                    sb.append("\n");
                    if (done()) {
                        break;
                    }
                    line = next();
                }

                values.add(sb.toString());
            }

            if (!done()) {
                buildCodeQuick(line);
            }
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
        OhScript.loadScriptFile();
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
