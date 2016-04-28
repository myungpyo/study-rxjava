package com.smp.rxplayround.sample;

import com.smp.rxplayround.BasePlayground;
import com.smp.rxplayround.support.Utils;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class Play36_TransformOperator_ClosingWindow extends BasePlayground {

    @Test
    public void printStrings() throws Exception {

        Observable.interval(500, TimeUnit.MILLISECONDS)
            .window(new Func0<Observable<Long>>() {
                @Override
                public Observable<Long> call() {
                    log.debug("closing func called");
                    return Observable.interval(3, TimeUnit.SECONDS);
                }
            }).subscribe(new Action1<Observable<Long>>() {
            @Override
            public void call(final Observable<Long> observable) {
                observable.subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long value) {
                        log.debug("onNext : {} from observable {}", value, observable);
                    }
                });
            }
        });

        waitForObservable();
    }
}
