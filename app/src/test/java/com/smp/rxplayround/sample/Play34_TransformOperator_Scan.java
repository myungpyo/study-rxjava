package com.smp.rxplayround.sample;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class Play34_TransformOperator_Scan extends BasePlayground {

    @Test
    public void printStrings() throws Exception {

        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).scan(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                return integer + integer2;
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
