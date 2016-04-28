package com.smp.rxplayround.sample;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observer;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class Play9_BehaviorSubject extends BasePlayground {

    @Test
    public void printStrings() throws Exception {

        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();

        behaviorSubject.onNext("test1");
        behaviorSubject.onNext("test2");

        behaviorSubject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                log.debug("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                log.debug("onError : {}", e.getMessage());
            }

            @Override
            public void onNext(String s) {
                log.debug("onNext : {}", s);
            }
        });

        behaviorSubject.onNext("test3");
        behaviorSubject.onNext("test4");
        behaviorSubject.onCompleted();
    }
}
