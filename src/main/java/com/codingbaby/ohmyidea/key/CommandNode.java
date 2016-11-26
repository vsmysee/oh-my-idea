package com.codingbaby.ohmyidea.key;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;

public class CommandNode {

    protected final AnAction action;

    protected final String actionId;

    public CommandNode(String actName) {
        this.actionId = actName;
        ActionManager aMgr = ActionManager.getInstance();
        this.action = aMgr.getAction(actionId);
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
