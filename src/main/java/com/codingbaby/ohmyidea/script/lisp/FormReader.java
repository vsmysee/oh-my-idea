package com.codingbaby.ohmyidea.script.lisp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Queue;

public class FormReader {


    public static Object readFrom(Queue<String> tokens) {

        String token = tokens.poll();

        if ("(".equals(token)) {
            ArrayList<Object> l = new ArrayList<>();
            while (!tokens.peek().equals(")")) {
                l.add(readFrom(tokens));
            }
            tokens.poll();   // pop of ")"
            return l;
        }

        if ("[".equals(token)) {
            FuncArray<Object> funcArray = new FuncArray<>();
            while (!tokens.peek().equals("]")) {
                funcArray.add(readFrom(tokens));
            }
            tokens.poll();   // pop of "]"
            return funcArray;
        }

        if (")".equals(token)) {
            throw new RuntimeException("unexpected ')'");
        }

        return atom(token);
    }


    static Object atom(String token) {
        if (token.equals("true")) {
            return true;
        }

        if (token.equals("false")) {
            return false;
        }

        try {
            return new BigInteger(token);
        } catch (NumberFormatException __) {
            try {
                return new BigDecimal(token);
            } catch (NumberFormatException ___) {
                return token;
            }
        }
    }
}
