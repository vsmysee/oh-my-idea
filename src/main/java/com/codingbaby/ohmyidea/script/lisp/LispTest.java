package com.codingbaby.ohmyidea.script.lisp;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LispTest {

    private Object lisp(String program) {
        EnvBuilder.Env env = EnvBuilder.globalEnv();
        Object val = Evaler.eval(FormReader.readFrom(Tokenr.tokenize(program)), env);
        return val;
    }

    @Test
    public void test() {
        assertEquals(new BigInteger("2"), lisp("2"));
        assertEquals(new BigDecimal("2.22"), lisp("2.22"));
        assertEquals(true, lisp("true"));
        assertEquals(false, lisp("false"));
        assertEquals("x", lisp(":x"));

        assertEquals(new BigInteger("5"), lisp("(+ 2 3)"));
        assertEquals(new BigInteger("5"), lisp("(+ 2 \n 3)"));
        assertEquals(new BigInteger("-1"), lisp("(- 2 3)"));
        assertEquals(new BigInteger("6"), lisp("(* 2 3)"));
        assertEquals(new BigInteger("3"), lisp("(/ 6 2)"));


        assertEquals(true, lisp("(> 6 2)"));
        assertEquals(true, lisp("(>= 6 2)"));
        assertEquals(true, lisp("(< 2 6)"));
        assertEquals(true, lisp("(<= 2 6)"));
        assertEquals(true, lisp("(= 2 2)"));
        assertEquals(true, lisp("(= :x :x)"));


        assertEquals(1, lisp("(length (list 1))"));
        assertEquals(false, lisp("(not true)"));
        assertEquals(FuncArray.class, lisp("[1 2 :x]").getClass());
        assertEquals(FuncArray.class, lisp("[1 2 :x (+ 1 2)]").getClass());
        assertTrue(lisp("(list 1 2)") instanceof List);
        assertEquals("'123'", lisp("(that '123')"));
        assertEquals(new BigInteger("1"), lisp("(if true 1 2)"));
        assertEquals(new BigInteger("2"), lisp("(if false 1 2)"));
        assertEquals(new BigInteger("2"), lisp("(do (def x 2) x)"));
        assertEquals(new BigInteger("2"), lisp("(do (def x 2) x)"));
        assertEquals(new BigInteger("3"), lisp("(do (def x 2) (set! x 3) x)"));
        assertEquals(new BigInteger("4"), lisp("((fn (x) (* x x)) 2)"));
        assertEquals(new BigInteger("4"), lisp("(do (def power (fn (x) (* x x))) (power 2) )"));
        assertEquals(new BigInteger("4"), lisp("((fn (x) \n (* x x)) 2)"));


        assertEquals("[[a, b, c], [d, e, f]]", lisp("(single [:a :b :c][:d :e :f])").toString());
        assertEquals("[[a, b, c], [d, e, f]]", lisp("(single \n[:a :b :c] \n [:d :e :f])").toString());
    }
}
