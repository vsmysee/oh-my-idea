package com.codingbaby.ohmyidea.script.lisp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

public class FormReader {

    public static Queue<Character> tokenize(String text) {
        text = text.replace("(", "( ").replace(")", " )");
        text = text.replace("[", "[ ").replace("]", " ]");
        Stream<Character> stream = text.chars().mapToObj(c -> (char) c);
        return stream.collect(toCollection(ArrayDeque::new));
    }


    static boolean isWhitespace(int ch) {
        return Character.isWhitespace(ch) || ch == ',';
    }


    public static Object readForm(String text) {
        Queue<Character> tokens = tokenize(text);
        return readForm(tokens);
    }


    private static Object readForm(Queue<Character> tokens) {

        Character peek = tokens.poll();

        while (isWhitespace(peek)) {
            peek = tokens.poll();
        }

        if (peek == ')' || peek == ']') {
            return new ListTerminal();
        }

        if (peek == '"') {
            StringBuilder sb = new StringBuilder();
            for (peek = tokens.peek(); peek != null && peek != '"'; peek = tokens.peek()) {
                sb.append(tokens.poll());
            }
            tokens.poll();
            return sb.toString();
        }

        if (peek.isDigit(peek)) {
            StringBuilder sb = new StringBuilder();
            sb.append(peek);
            for (peek = tokens.peek(); peek != null && (peek.isDigit(peek) || peek == '.'); peek = tokens.peek()) {
                sb.append(tokens.poll());
            }
            try {
                return new BigInteger(sb.toString());
            } catch (NumberFormatException __) {
                return new BigDecimal(sb.toString());
            }
        }

        if ('(' == peek) {
            ArrayList<Object> l = new ArrayList<>();
            Character see = tokens.peek();
            while (see != null && see != ')') {
                Object e = readForm(tokens);
                if (e instanceof ListTerminal)
                    return l;
                l.add(e);
                see = tokens.peek();
            }
        }


        if ('[' == peek) {
            FuncArray<Object> funcArray = new FuncArray<>();
            Character see = tokens.peek();
            while (see != null && see != ']') {
                Object e = readForm(tokens);
                if (e instanceof ListTerminal)
                    return funcArray;
                funcArray.add(e);
                see = tokens.peek();
            }
        }


        StringBuilder sb = new StringBuilder();
        sb.append(peek);
        for (peek = tokens.peek(); peek != null && !peek.isWhitespace(peek) && peek != ')' && peek != ']'; peek = tokens.peek()) {
            sb.append(tokens.poll());
        }

        return interpretToken(sb.toString());
    }

    static private Object interpretToken(String s) {
        if (s.equals("true")) {
            return true;
        }

        if (s.equals("false")) {
            return false;
        }

        if (s.startsWith(":")) {
            return new Keyworld(s.replaceFirst(":", ""));
        }

        return new Symbol(s);

    }


}
