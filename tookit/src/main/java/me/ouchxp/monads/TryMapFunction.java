package me.ouchxp.monads;

public interface TryMapFunction<T, R> {
    R apply(T t) throws Throwable;
}