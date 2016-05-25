package com.smp.rxplayround.sample.operator.transform;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func0;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class ClosingWindow extends BasePlayground {

    @Test
    public void play() throws Exception {

        Observable.interval(500, TimeUnit.MILLISECONDS)
            .window(new Func0<Observable<Long>>() {
                @Override
                public Observable<Long> call() {
                    log.debug("closing func called");
                    return Observable.interval(3, TimeUnit.SECONDS);
                }
            }).take(5).subscribe(new Observer<Observable<Long>>() {
            @Override
            public void onCompleted() {
                stopWaitingForObservable();
            }

            @Override
            public void onError(Throwable e) {
                stopWaitingForObservable();
            }

            @Override
            public void onNext(final Observable<Long> observable) {
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
