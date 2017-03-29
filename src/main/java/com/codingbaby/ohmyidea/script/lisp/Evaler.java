package com.codingbaby.ohmyidea.script.lisp;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.List;

import static com.codingbaby.ohmyidea.script.lisp.EnvBuilder.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class Evaler {

    public static Object eval(Object x, EnvBuilder.Env env) {

        if (x instanceof String) {

            if (((String) x).startsWith(":")) {
                if (((String) x).length() == 1) {
                    throw new RuntimeException("not symbol");
                }
                return ((String) x).replaceFirst(":","");
            }

            // 符号查找
            return env.find(string(x)).dict.get(x);
        }

        if (x instanceof FuncArray) {
            List list = new ArrayList();
            for (Object element : ((FuncArray) x).elements) {
                list.add(eval(element, env));
            }
            return new FuncArray(list);
        }


        if (!(x instanceof List)) {            //原子
            return x;
        }


        List<?> l = (List<?>) x;
        String var;
        Object exp, cmd = l.get(0);
        if (cmd instanceof String) {
            switch (string(l.get(0))) {
                case "not":
                    return !(Boolean) eval(l.get(1), env);
                case "that":                        // (that exp)
                    return l.get(1);
                case "if":                           // (if test conseq alt)
                    return eval(((Boolean) eval(l.get(1), env)) ? l.get(2) : l.get(3), env);
                case "set!":                         // (set! var exp)
                    var = string(l.get(1));
                    env.find(var).add(var, eval(l.get(2), env));
                    return null;
                case "def":                       // (def var exp)
                    var = string(l.get(1));
                    env.add(var, eval(l.get(2), env));
                    return null;
                case "fn":                       // (fn (vars) exp)
                    List<?> vars = list(l.get(1));
                    exp = l.get(2);
                    return mh(EnvBuilder.FunAll.class, args -> eval(exp, new EnvBuilder.Env(env).addAll(vars, asList(args)))).asCollector(Object[].class, vars.size());
                case "do":                        // (do exp*)
                    return l.stream().skip(1).reduce(null, (val, e) -> eval(e, env), (__1, __2) -> null);
                default:
            }
        }
        List<?> exprs = l.stream().map(expr -> eval(expr, env)).collect(toList());
        MethodHandle proc = (MethodHandle) exprs.get(0);
        try {
            return proc.invokeWithArguments(exprs.subList(1, exprs.size()));
        } catch (Throwable e) {
            if (e instanceof Error) throw (Error) e;
            throw new Error(e);
        }
    }

}
