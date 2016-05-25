package com.smp.rxplayround.sample.operator.combine;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.joins.Pattern2;
import rx.joins.Pattern3;
import rx.joins.Plan0;
import rx.observables.JoinObservable;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class AndWhenThen extends BasePlayground {

    @Test
    public void play() throws Exception {

        Observable<Integer> source = Observable.just(1, 2, 3, 4, 5);
        Observable<Long> interval1 = Observable.interval(500, TimeUnit.MILLISECONDS);
        Observable<Long> interval2 = Observable.interval(100, TimeUnit.MILLISECONDS);

        Pattern3<Integer, Long, Long> pattern = JoinObservable.from(source).and(interval1).and(interval2);

        Plan0<Long> plan = pattern.then(new Func3<Integer, Long, Long, Long>() {
            @Override
            public Long call(Integer integer, Long aLong, Long aLong2) {
                log.debug("call : int {}, long {}, long {}", integer, aLong, aLong2);
                return integer + aLong + aLong2;
            }
        });

        JoinObservable.when(plan).toObservable().subscribe(new Observer<Long>() {
            @Override
            public void onCompleted() {
                log.debug("onCompleted");
                stopWaitingForObservable();
            }

            @Override
            public void onError(Throwable e) {
                log.debug("onError {}", e.getMessage());
                stopWaitingForObservable();
            }

            @Override
            public void onNext(Long aLong) {
                log.debug("onNext {}", aLong);
            }
        });

        waitForObservable();
    }
}
