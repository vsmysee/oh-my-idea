package com.codingbaby.ohmyidea.key;

import com.intellij.openapi.actionSystem.AnAction;

/**
 *
 *
 */
public class CommandNode {

    protected final AnAction action;
    protected final String actionId;

    public CommandNode(String actName, AnAction action) {
        this.actionId = actName;
        this.action = action;
    }

    public String getActionId() {
        return actionId;
    }

    /**
     * Gets the command's action
     *
     * @return The command's action
     */
    public AnAction getAction() {
        return action;
    }
}
