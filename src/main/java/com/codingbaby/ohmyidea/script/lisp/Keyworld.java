package com.codingbaby.ohmyidea.script.lisp;

/**
 * Created by codingbaby on 2017/4/8.
 */
public class Keyworld {

    public String that;

    public Keyworld(String that) {
        this.that = that;
    }

    @Override
    public boolean equals(Object obj) {
        return that.equals(((Keyworld)obj).that);
    }
}
