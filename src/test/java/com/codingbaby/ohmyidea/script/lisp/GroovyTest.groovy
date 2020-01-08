package com.codingbaby.ohmyidea.script.lisp


class D {

    def key(p,t="") {
        println p
        println t
    }
}


def single = {
    closure ->
        closure.delegate = new D()
        closure()

}


single {

    key "hello"
    key "hello","world"

}
