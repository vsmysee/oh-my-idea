package com.codingbaby.ohmyidea.key;

import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by baby on 15/6/24.
 */

public class CommandHolder {

    private Map<String, CommandMapping> data = new LinkedHashMap<String, CommandMapping>();


    public void add(String key, String action, String desc) {
        data.put(key, new CommandMapping(key, new CommandNode(action), desc));
    }

    public CommandNode get(KeyStroke stroke) {
        char keyChar = stroke.getKeyChar();
        CommandMapping commandMapping = data.get("" + keyChar);
        if (commandMapping == null) {
            return null;
        }
        return commandMapping.getCommandNode();
    }

    public CommandNode get(String key) {
        CommandMapping commandMapping = data.get(key);
        if (commandMapping == null) {
            return null;
        }
        return commandMapping.getCommandNode();
    }

    public Map<String,String> getCommandDesc() {
        Map<String,String> map = new HashMap<String, String>();
        for (CommandMapping commandMapping : data.values()) {
            map.put(commandMapping.getKey(), commandMapping.getDesc());
        }
        return map;
    }
}
