package com.codingbaby.ohmyidea.script.lisp;

import java.util.ArrayDeque;
import java.util.Queue;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toCollection;

public class Tokenr {

    public static Queue<String> tokenize(String text) {
        return stream(text
                .replace("(", "( ")
                .replace(")", " )")
                .replace("[", " [ ")
                .replace("]", " ] ")
                .replace("\n", " ")
                .split(" "))
                .filter(s -> !s.isEmpty()).collect(toCollection(ArrayDeque::new));
    }
}
