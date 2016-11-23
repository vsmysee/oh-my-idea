package com.codingbaby.ohmyidea.key;

/**
 * Created by baby on 15/6/24.
 */
public class CommandMapping {

    private String key;

    private CommandNode commandNode;

    private String desc;

    public CommandMapping(String key, CommandNode commandNode, String desc) {
        this.key = key;
        this.commandNode = commandNode;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public CommandNode getCommandNode() {
        return commandNode;
    }

    public void setCommandNode(CommandNode commandNode) {
        this.commandNode = commandNode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
