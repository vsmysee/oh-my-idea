package com.codingbaby.ohmyidea.script.lisp;

import java.util.ArrayList;
import java.util.List;

public class FuncArray<T> {

    public List<T> elements = new ArrayList<T>();

    public FuncArray(List<T> elements) {
        this.elements = elements;
    }

    public FuncArray() {
    }

    public void add(T data) {
        elements.add(data);
    }

    public static List<List> parseSingle(Object... args) {
        List<List> list = new ArrayList<>();
        for (Object arg : args) {
            FuncArray funcArray = (FuncArray) arg;
            list.add(funcArray.elements);
        }
        return list;
    }

}
