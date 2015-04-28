package me.ouchxp.monads;

interface TryMapFunction<T, R> {
    R apply(T t) throws Throwable;
}