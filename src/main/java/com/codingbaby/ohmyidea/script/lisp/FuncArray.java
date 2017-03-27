package com.codingbaby.ohmyidea.script.lisp;

import com.codingbaby.ohmyidea.script.ShortHolder;

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

    public static int parseSingle(String key, Object... args) {
        for (Object arg : args) {
            FuncArray funcArray = (FuncArray) arg;
            List elements = funcArray.elements;
            if (key.equals("single")) {
                ShortHolder.INSTANCE.getSingle().add(elements.get(0).toString(), elements.get(1).toString(), elements.get(2).toString());
            }
            if (key.equals("composite")) {
                ShortHolder.INSTANCE.getCompose().add(elements.get(0).toString(), elements.get(1).toString(), elements.get(2).toString());
            }
        }
        return args.length;
    }

}
