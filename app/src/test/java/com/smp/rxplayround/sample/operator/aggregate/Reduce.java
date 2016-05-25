package com.smp.rxplayround.sample.operator.aggregate;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.functions.Func2;

/**
 * Created by myungpyo.shim on 2016. 5. 25..
 */
@Slf4j
public class Reduce extends BasePlayground {

    @Test
    public void play() throws Exception {

        Observable.from(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}).reduce(new Func2<Integer, Integer, Integer>() {
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
                log.debug("onError");
            }

            @Override
            public void onNext(Integer integer) {
                log.debug("onNext : {}", integer);
            }
        });

    }
}
