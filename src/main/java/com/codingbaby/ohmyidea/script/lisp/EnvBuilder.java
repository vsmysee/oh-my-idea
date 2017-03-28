package com.codingbaby.ohmyidea.script.lisp;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;


public class EnvBuilder {

    public interface Fun {
        Object apply(Object a);
    }

    public interface Fun2 {
        Object apply(Object a, Object b);
    }

    public interface FunAll {
        Object apply(Object[] args);
    }

    static MethodHandle mhRef(Class<?> type, String name) {
        try {
            return MethodHandles.publicLookup().unreflect(stream(type.getMethods()).filter(m -> m.getName().equals(name)).findFirst().get());
        } catch (IllegalAccessException e) {
            throw new Error(e);
        }
    }

    static <F> MethodHandle mh(Class<F> type, F fun) {
        return mhRef(type, "apply").bindTo(fun);
    }

    static int compare(Object a, Object b) {
        return ((Comparable<Object>) a).compareTo(b);
    }

    static List<?> list(Object o) {
        return (List<?>) o;
    }

    static String string(Object o) {
        return (String) o;
    }

    static double dbl(Object o) {
        return ((Number) o).doubleValue();
    }

    static BigInteger bigint(Object o) {
        return ((BigInteger) o);
    }

    static boolean isdbl(Object o) {
        return o instanceof Double;
    }


    public static class Env {
        final HashMap<String, Object> dict = new HashMap<>();
        private final Env outer;

        Env(Env outer) {
            this.outer = outer;
        }

        Env find(String var) {
            return dict.containsKey(var) ? this : outer.find(var);
        }

        Env add(String var, Object value) {
            dict.put(var, value);
            return this;
        }

        Env addAll(List<?> vars, List<?> values) {
            range(0, vars.size()).forEach(i -> add(string(vars.get(i)), values.get(i)));
            return this;
        }
    }

    public static Env globalEnv() {
        return new Env(null)
                .add("+", mh(Fun2.class, (a, b) -> (isdbl(a) || isdbl(b)) ? dbl(a) + dbl(b) : bigint(a).add(bigint(b))))
                .add("-", mh(Fun2.class, (a, b) -> (isdbl(a) || isdbl(b)) ? dbl(a) - dbl(b) : bigint(a).subtract(bigint(b))))
                .add("*", mh(Fun2.class, (a, b) -> (isdbl(a) || isdbl(b)) ? dbl(a) * dbl(b) : bigint(a).multiply(bigint(b))))
                .add("/", mh(Fun2.class, (a, b) -> (isdbl(a) || isdbl(b)) ? dbl(a) / dbl(b) : bigint(a).divide(bigint(b))))
                .add("<", mh(Fun2.class, (a, b) -> compare(a, b) < 0))
                .add("<=", mh(Fun2.class, (a, b) -> compare(a, b) <= 0))
                .add(">", mh(Fun2.class, (a, b) -> compare(a, b) > 0))
                .add(">=", mh(Fun2.class, (a, b) -> compare(a, b) >= 0))
                .add("=", mhRef(Object.class, "equals"))
                .add("length", mhRef(List.class, "size"))
                .add("cons", mh(Fun2.class, (a, l) -> concat(of(a), list(l).stream()).collect(toList())))
                .add("car", mh(Fun.class, l -> list(l).get(0)))
                .add("cdr", mh(Fun.class, l -> list(l).subList(1, list(l).size())))
                .add("append", mh(Fun2.class, (l, m) -> concat(list(l).stream(), list(m).stream()).collect(toList())))
                .add("list", mh(FunAll.class, args -> asList(args)).asVarargsCollector(Object[].class))
                .add("list?", mh(Fun.class, l -> l instanceof List))
                .add("null?", mhRef(List.class, "isEmpty"))
                .add("single", mh(FunAll.class, args -> FuncArray.parseSingle("single", args)).asVarargsCollector(Object[].class))
                .add("composite", mh(FunAll.class, args -> FuncArray.parseSingle("composite", args)).asVarargsCollector(Object[].class))
                .add("select", mh(FunAll.class, args -> FuncArray.parseSingle("select", args)).asVarargsCollector(Object[].class))
                .add("movement", mh(FunAll.class, args -> FuncArray.parseSingle("movement", args)).asVarargsCollector(Object[].class))
                .add("bottom", mh(FunAll.class, args -> FuncArray.parseSingle("bottom", args)).asVarargsCollector(Object[].class));
    }
}
