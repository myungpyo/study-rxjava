package com.smp.rxplayround.sample.operator.transform;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func2;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class CountWindow extends BasePlayground {

    @Test
    public void play() throws Exception {

        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .window(3)
            .subscribe(new Observer<Observable<Integer>>() {
                @Override
                public void onCompleted() {
                    log.debug("onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    log.debug("onError : {}", e.getMessage());
                }

                @Override
                public void onNext(final Observable<Integer> observable) {
                    observable.subscribe(new Action1<Integer>() {
                        @Override
                        public void call(Integer integer) {
                            log.debug("onNext : {} from observable {}", integer, observable);
                        }
                    });

                }
            });

    }
}
