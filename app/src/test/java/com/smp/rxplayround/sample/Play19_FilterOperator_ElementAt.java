package com.smp.rxplayround.sample;

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
public class Play19_FilterOperator_ElementAt extends BasePlayground {

    @Test
    public void printStrings() throws Exception {

        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5, 6, 7, 8);

        observable.elementAt(3).subscribe(new Observer<Integer>() {
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
