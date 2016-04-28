package com.smp.rxplayround.sample;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class Play8_PublishSubject extends BasePlayground {

    @Test
    public void printStrings() throws Exception {

        PublishSubject<String> publishSubject = PublishSubject.create();

        publishSubject.onNext("test1");
        publishSubject.onNext("test2");

        publishSubject.subscribe(new Observer<String>() {
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

        publishSubject.onNext("test3");
        publishSubject.onNext("test4");
        publishSubject.onCompleted();
    }
}
