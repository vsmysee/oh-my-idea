package com.codingbaby.ohmyidea.script;

import com.intellij.openapi.util.io.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * _ls|描述
 * List<String> list = new ArrayList<String>();
 * <p>
 * <p>
 * _mso|描述
 * Map<String,Object> map = new HashMap<String,Object>();
 */
public class OhScript {

    public static final String OH_FILE = ".oh-my-idea";

    public static final int BUFSIZE = 4096;

    private static final Pattern EOL_SPLIT_PATTERN = Pattern.compile("(\r\n|\n)");

    private static LineHolder holder;

    private static Map<String, String> codeGen = new HashMap<String, String>();

    private static void putKey(String key, String value) {
        codeGen.put(key.trim(), value.trim());
    }


    public static String getMapping(String key) {
        return codeGen.get(key);
    }


    public static void loadScriptFile() {

        parseTokens(loadContent());

        if (holder == null) {
            return;
        }

        List<CodeKV> codeKV = holder.getCodeKV();
        for (CodeKV kv : codeKV) {
            putKey(kv.key, kv.value);
        }
    }

    public static void saveScript(String text) {
        if (StringUtils.isNotBlank(text)) {
            final String homeDirName = System.getProperty("user.home");
            if (homeDirName != null) {
                final File file = new File(homeDirName, OH_FILE);
                try {
                    FileUtil.writeToFile(file, text);
                    loadScriptFile();
                } catch (IOException e) {
                }
            }
        }
    }

    public static String loadContent() {
        final String homeDirName = System.getProperty("user.home");
        if (homeDirName != null) {
            final File file = new File(homeDirName, OH_FILE);
            if (file.exists()) {
                try {
                    return readFile(file);
                } catch (IOException e) {
                    return "";
                }
            }
        }

        return "";
    }

    public static Map<String, String> getHelpDesc() {
        return holder.getDescMap();
    }

    private static void parseTokens(String content) {
        if (StringUtils.isBlank(content)) {
            return;
        }
        String[] lines = EOL_SPLIT_PATTERN.split(content);
        holder = new LineHolder(lines);
        holder.buildCodeQuick(holder.next());
    }

    private static class LineHolder {
        private String[] lines;
        private int begin = 0;
        private List<String> keys = new ArrayList<String>();
        private List<String> descriptions = new ArrayList<String>();
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

        public Map<String, String> getDescMap() {
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < keys.size(); i++) {
                map.put(keys.get(i), descriptions.get(i));
            }
            return map;
        }

        public void buildCodeQuick(String line) {
            if (StringUtils.isNotBlank(line) && line.startsWith("_")) {

                String key = line.trim().substring(1);
                String[] split = key.split("\\|");
                keys.add(split[0]);
                if (split.length == 2) {
                    descriptions.add(split[1]);
                } else {
                    descriptions.add("");
                }

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
            } else {
                line = next();
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
