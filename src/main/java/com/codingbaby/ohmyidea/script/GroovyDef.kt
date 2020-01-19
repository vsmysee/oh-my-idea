package com.codingbaby.ohmyidea.script

object GroovyDef {

    var groovy = """

import java.awt.Robot;
import java.awt.event.KeyEvent;

class RobotContainer {

        def outerList

        RobotContainer(env){
            outerList = env
        }

        def key(key,list) {
            outerList[key] = list
        }
}



class ActorContainer {

        def outerAction


        ActorContainer(env){
            outerAction = env
        }


        def key(key,action,desc = "") {
            outerAction.add(key, action, desc)
        }

}

import com.intellij.openapi.actionSystem.ActionManager

class ActionContainer {

        def reg(id,action) {
            if(ActionManager.getInstance().getAction(id) == null){
                ActionManager.getInstance().registerAction(id, action)
            }
        }
}

def action = {
 closure ->
        closure.delegate = new ActionContainer()
        closure()
}


def keyboard = {
    closure ->
        closure.delegate = new RobotContainer(envMap)
        closure()
}

def select = {
    closure ->
        closure.delegate = new ActorContainer(keyMap['select'])
        closure()
}

def single = {
    closure ->
        closure.delegate = new ActorContainer(keyMap['single'])
        closure()
}

def movement = {
    closure ->
        closure.delegate = new ActorContainer(keyMap['movement'])
        closure()
}

def composite = {
    closure ->
        closure.delegate = new ActorContainer(keyMap['composite'])
        closure()
}

def bottom = {
    closure ->
        closure.delegate = new ActorContainer(keyMap['bottom'])
        closure()
}

"""

}