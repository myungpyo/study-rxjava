package com.smp.rxplayround.sample.operator.transform;

import com.smp.rxplayround.BasePlayground;
import com.smp.rxplayround.support.Utils;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class CountBuffer extends BasePlayground {

    @Test
    public void play() throws Exception {

        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {

                for (int num = 1; num < 10; num++) {
                    subscriber.onNext(num);
                }

                subscriber.onCompleted();
            }
        });

        observable.buffer(3).subscribe(new Observer<List<Integer>>() {
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
            public void onNext(List<Integer> value) {
                log.debug("onNext : {}", Utils.convertToCommaSeparatedString(value));
            }
        });

    }
}
