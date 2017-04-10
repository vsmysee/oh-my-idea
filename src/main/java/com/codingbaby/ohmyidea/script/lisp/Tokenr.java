package com.codingbaby.ohmyidea.script.lisp;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toCollection;

public class Tokenr {

    public static Queue<Character> tokenize(String text) {
        text = text.replace("(", "( ").replace(")", " )");
        text = text.replace("[", "[ ").replace("]", " ]");
        Stream<Character> stream = text.chars().mapToObj(c -> (char)c);
        return stream.collect(toCollection(ArrayDeque::new));
    }


}
