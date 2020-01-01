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


class CodeContainer {

        def outerList

        def holder

        CodeContainer(env){
            outerList = env
        }

        def key(key,desc,code) {
            outerList << ["key":key,"desc":desc,"code":code]
        }

}

class ActorContainer {

        def outerAction


        ActorContainer(env){
            outerAction = env
        }


        def key(key,action,desc) {
            outerAction.add(key, action, desc)
        }

}

class ActionContainer {

        def reg(id,action) {
            com.intellij.openapi.actionSystem.ActionManager.getInstance().registerAction(id, action)
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
        closure.delegate = new ActorContainer(vmode)
        closure()
}

def single = {
    closure ->
        closure.delegate = new ActorContainer(smode)
        closure()
}

def movement = {
    closure ->
        closure.delegate = new ActorContainer(mmode)
        closure()
}

def composite = {
    closure ->
        closure.delegate = new ActorContainer(cmode)
        closure()
}

def bottom = {
    closure ->
        closure.delegate = new ActorContainer(bmode)
        closure()
}


"""

}