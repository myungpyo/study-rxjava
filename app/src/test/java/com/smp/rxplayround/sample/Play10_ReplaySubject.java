package com.smp.rxplayround.sample;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observer;
import rx.subjects.BehaviorSubject;
import rx.subjects.ReplaySubject;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class Play10_ReplaySubject extends BasePlayground {

    @Test
    public void play() throws Exception {

        ReplaySubject<String> replaySubject = ReplaySubject.create();

        replaySubject.onNext("test1");
        replaySubject.onNext("test2");

        replaySubject.subscribe(new Observer<String>() {
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

        replaySubject.onNext("test3");
        replaySubject.onNext("test4");
        replaySubject.onCompleted();
    }
}
