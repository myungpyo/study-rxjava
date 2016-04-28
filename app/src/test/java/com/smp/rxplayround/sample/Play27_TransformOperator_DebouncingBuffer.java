package com.smp.rxplayround.sample;

import com.smp.rxplayround.BasePlayground;
import com.smp.rxplayround.support.Utils;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class Play27_TransformOperator_DebouncingBuffer extends BasePlayground {

    @Test
    public void printStrings() throws Exception {


        Observable<Integer> sharedObserver = Utils.createInfiniteRandomIntervalIntegerEmitter().subscribeOn(Schedulers.newThread()).share();

        Observable<Integer> debounce = sharedObserver.debounce(3000, TimeUnit.MILLISECONDS);

        sharedObserver.buffer(debounce).subscribe(new Observer<List<Integer>>() {
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
            public void onNext(List<Integer> values) {
                log.debug("onNext : {}", Utils.convertToCommaSeparatedString(values));
            }
        });

        waitForObservable();
    }
}
