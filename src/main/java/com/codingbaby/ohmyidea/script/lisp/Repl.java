package com.codingbaby.ohmyidea.script.lisp;

import java.util.List;
import java.util.Scanner;

import static com.codingbaby.ohmyidea.script.lisp.EnvBuilder.list;
import static java.util.stream.Collectors.joining;


public class Repl {

    static String toString(Object val) {
        if (val instanceof List) {
            return list(val).stream().map(Repl::toString).collect(joining(" ", "(", ")"));
        }
        if (val instanceof FuncArray) {
            return list(((FuncArray) val).elements).stream().map(Repl::toString).collect(joining(" ", "[", "]"));
        } else {
            return String.valueOf(val);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        EnvBuilder.Env env = EnvBuilder.globalEnv();

        for (; ; ) {

            System.out.print("lisp=> ");

            try {
                String program = in.nextLine();
                if (program.equals("")) {
                    System.out.println("");
                    continue;
                }
                Object val = Evaler.eval(FormReader.readForm(Tokenr.tokenize(program)), env);
                if (val != null) {
                    System.out.println(toString(val));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}