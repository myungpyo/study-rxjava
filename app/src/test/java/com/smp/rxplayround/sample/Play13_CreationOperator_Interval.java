package com.smp.rxplayround.sample;

import com.smp.rxplayround.BasePlayground;
import com.smp.rxplayround.support.Utils;

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
public class Play13_CreationOperator_Interval extends BasePlayground {

    @Test
    public void printStrings() throws Exception {

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
