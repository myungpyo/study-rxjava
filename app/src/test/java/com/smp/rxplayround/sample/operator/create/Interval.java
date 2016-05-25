package com.smp.rxplayround.sample.operator.create;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class Interval extends BasePlayground {

    @Test
    public void play() throws Exception {

        Observable<Long> intervalObservable = Observable.interval(100, 300, TimeUnit.MILLISECONDS);

        intervalObservable.take(5).subscribe(new Observer<Long>() {
            @Override
            public void onCompleted() {
                log.debug("onCompleted");
                stopWaitingForObservable();
            }

            @Override
            public void onError(Throwable e) {
                log.debug("onError : {}", e.getMessage());
                stopWaitingForObservable();
            }

            @Override
            public void onNext(Long value) {
                log.debug("onNext : {}", value);
            }
        });

        waitForObservable();
    }
}
