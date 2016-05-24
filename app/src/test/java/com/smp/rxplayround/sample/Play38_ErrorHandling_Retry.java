package com.smp.rxplayround.sample;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class Play38_ErrorHandling_Retry extends BasePlayground {

    @Test
    public void play() throws Exception {

        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {

                for (int i = 0; i < 10; i++) {
                    if (i == 5) {
                        throw new IllegalStateException("UnExpectedValue : " + i);
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        })
        .retry(2)
        .subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                log.debug("onComplete");
            }

            @Override
            public void onError(Throwable e) {
                log.debug("onError : {}", e.getMessage());
            }

            @Override
            public void onNext(Integer value) {
                log.debug("onNext : {}", value);
            }
        });

    }
}
