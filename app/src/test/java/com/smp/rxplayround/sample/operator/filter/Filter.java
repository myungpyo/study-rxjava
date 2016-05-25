package com.smp.rxplayround.sample.operator.filter;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class Filter extends BasePlayground {

    @Test
    public void play() throws Exception {

        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5, 6, 7, 8);

        observable.filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                //Odd number filter
                return integer % 2 != 0;
            }
        }).subscribe(new Observer<Integer>() {
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
