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
        Object val = Evaler.eval(FormReader.readForm(Tokenr.tokenize(program)), env);
        return val;
    }


    @Test
    public void test() {
        assertEquals(new BigInteger("234"), lisp("234"));
        assertEquals(new BigInteger("234"), lisp(" 234"));
        assertEquals(new BigDecimal("2.22"), lisp("2.22"));

        assertEquals(true, lisp("true"));
        assertEquals(false, lisp("false"));
        assertEquals(lisp(":key"), "key");
        assertEquals(":", lisp("::"));

        assertEquals("hello world", lisp("\"hello world\""));
        lisp("(def x 2)");


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
        assertTrue(lisp("(list 1 2)") instanceof List);
        assertTrue(lisp("(that '123')") instanceof Symbol);
        assertEquals(new BigInteger("1"), lisp("(if true 1 2)"));
        assertEquals(new BigInteger("2"), lisp("(if false 1 2)"));
        assertEquals(new BigInteger("2"), lisp("(do (def x 2) x)"));
        assertEquals(new BigInteger("2"), lisp("(do (def x 2) x)"));
        assertEquals(new BigInteger("3"), lisp("(do (def x 2) (set! x 3) x)"));
        assertEquals(new BigInteger("4"), lisp("((fn (x) (* x x)) 2)"));
        assertEquals(new BigInteger("4"), lisp("(do (def power (fn (x) (* x x))) (power 2) )"));
        assertEquals(new BigInteger("4"), lisp("((fn (x) \n (* x x)) 2)"));



        assertEquals(2, lisp("(single [:a :b :c][:d :e :f])"));
        assertEquals(2, lisp("(single \n[:a :b :c] \n [:d :e :f])"));
        assertEquals(2, lisp("(do (single \n[:a :b :c] \n [:d :e :f]) )"));
        assertEquals(2, lisp("(do\n(single \n[:a :b :c] \n [:d :e :f]) )"));
        assertEquals(2, lisp("(do\n(composite \n[:a :b :c] \n [:d :e :f]) )"));
        assertEquals(2, lisp("(do\n(select \n[:a :b :c] \n [:d :e :f]) )"));
        assertEquals(2, lisp("(do\n(movement \n[:a :b :c] \n [:d :e :f]) )"));
        assertEquals(2, lisp("(do\n(bottom \n[:a :b :c] \n [:d :e :f]) )"));

    }


}
