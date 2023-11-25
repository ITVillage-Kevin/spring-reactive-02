package com.itvillage.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Logger {
    public static void info(Object data) {
        log.info("{}",data);
    }

    public static void info(String msg, Object data) {
        log.info(msg, data);
    }

    public static void info(String msg, Object data1, Object data2) {
        log.info(msg, data1, data2);
    }

    public static void doOnNext(Object data) {
        log.info("# doOnNext(): {}", data);
    }

    public static void doOnNext(Object data1, Object data2) {
        log.info("# doOnNext() {}: {}", data1, data2);
    }

    public static void doOnNext(String taskName, String operator, Object data) {
        log.info("# doOnNext() {} {}: {}", taskName, operator, data);
    }

    public static void doOnSubscribe() {
        log.info("# doOnSubscribe()");
    }

    public static void doFirst() {
        log.info("# doFirst()");
    }

    public static void doFinally(Object data) {
        log.info("# doFinally(): {}", data);
    }

    public static void doOnRequest(Object data) {
        log.info("# doOnRequest(): {}", data);
    }

    public static void doOnComplete() {
        log.info("# doOnComplete()");
    }

    public static void doOnTerminate(String operator) {
        log.info("# doOnTerminate() {}", operator);
    }

    public static void doAfterTerminate(String operator) {
        log.info("# doAfterTerminate() {}", operator);
    }

    public static void onNext(Object data) {
        log.info("# onNext(): {}", data);
    }

    public static void onNext(Object data1, Object data2) {
        log.info("# onNext(): {} : {}", data1, data2);
    }

    public static void onNext(String message, Object data1, Object data2) {
        log.info("# onNext(): " + message, data1, data2);
    }

    public static void onError(Throwable error) {
        log.error("error happened: ", error);
    }

    public static void onError(String message, Throwable error, Object data) {
        log.error(message, error, data);
    }

    public static void onComplete() {
        log.info("# onComplete()");
    }

    public static void onComplete(Object data) {
        log.info("# onComplete(): {}", data);
    }

    public static void onNext(String message, Object data) {
        log.info("# {} onNext(): {}", message, data);
    }


    public static void filter(Object data) {
        log.info("# filter(): {}", data);
    }
    public static void map(Object data) {
        log.info("# map(): {}", data);
    }
}
