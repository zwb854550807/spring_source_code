package com.zengwb.utils;

import java.util.concurrent.ForkJoinPool;

public enum ThreadPoolUtil {

    INSTANCE;

    private ForkJoinPool forkJoinPool;

    ThreadPoolUtil() {
        forkJoinPool = new ForkJoinPool(5);
    }

    public ForkJoinPool getForkJoinPool() {
        return forkJoinPool;
    }
}
