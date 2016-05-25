package com.smp.rxplayround.sample.basic;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class EmitFromCustomObservable extends BasePlayground {

    @Test
    public void play() throws Exception {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String[] strings = {"test1", "test2", "test3"};

                for (String string : strings) {
                    subscriber.onNext(string);
                }

                subscriber.onCompleted();
            }
        });

        observable.subscribe(new Observer<String>() {
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
    }
}
