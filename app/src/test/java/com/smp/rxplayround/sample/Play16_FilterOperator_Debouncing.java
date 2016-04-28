package com.smp.rxplayround.sample;

import com.smp.rxplayround.BasePlayground;
import com.smp.rxplayround.support.Utils;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class Play16_FilterOperator_Debouncing extends BasePlayground {

    @Test
    public void printStrings() throws Exception {


        Observable<Integer> observable = Utils.createInfiniteRandomIntervalIntegerEmitter().subscribeOn(Schedulers.newThread());


        observable.debounce(3000, TimeUnit.MILLISECONDS).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                log.debug("onCompleted");
                stopWaitingForObservable();
            }

            @Override
            public void onError(Throwable e) {
                log.debug("onError : {}", e.getMessage());
                e.printStackTrace();
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
