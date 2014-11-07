package com.codingbaby.ohmyidea.script;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 */
public class CodeQuick {

    private static Map<String, String> codeGen = new HashMap<String, String>();

    public static void add(String key, String value) {
        codeGen.put(key, value);
    }

    public static String getMapping(String key) {
        return codeGen.get(key);
    }


}
