package com.smp.rxplayround.sample.operator.create;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class Range extends BasePlayground {

    @Test
    public void play() throws Exception {

        Observable<Integer> rangeObservable = Observable.range(1, 5);

        rangeObservable.subscribe(new Observer<Integer>() {
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
            public void onNext(Integer value) {
                log.debug("onNext : {}", value);
            }
        });

        waitForObservable();
    }
}
