package com.codingbaby.ohmyidea.script.lisp


class D {

    def key(p) {
        println p
    }
}


def single = {
    closure ->
        closure.delegate = new D()
        closure()

}


single {

    key "hello"

}
