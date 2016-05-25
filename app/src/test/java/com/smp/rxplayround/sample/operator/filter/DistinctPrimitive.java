package com.smp.rxplayround.sample.operator.filter;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class DistinctPrimitive extends BasePlayground {

    @Test
    public void play() throws Exception {

        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 4, 5, 6, 6, 7, 7);

        observable.distinct().subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                log.debug("onCompleted");
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
