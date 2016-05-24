package com.smp.rxplayround.sample;

import com.smp.rxplayround.BasePlayground;
import com.smp.rxplayround.support.Utils;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class Play32_TransformOperator_ConcatMapOnWorker extends BasePlayground {

    @Test
    public void play() throws Exception {

        Observable.from(new String[]{"1-3","5-8", "10-15"})
            .concatMap(new Func1<String, Observable<Integer>>() {
                @Override
                public Observable<Integer> call(final String range) {
                    return Observable.create(new Observable.OnSubscribe<Integer>() {
                        @Override
                        public void call(Subscriber<? super Integer> subscriber) {
                            if (subscriber.isUnsubscribed()) {
                                return;
                            }

                            String[] split = range.split("-");
                            if (split.length != 2) {
                                throw new IllegalArgumentException("Check range format. ex> lowNum-HighNum");
                            }

                            int minNum = Integer.parseInt(split[0]);
                            int maxNum = Integer.parseInt(split[1]);

                            for (int num = minNum; num <= maxNum; num++) {
                                Utils.sleep(500);
                                subscriber.onNext(num);
                            }
                            subscriber.onCompleted();
                        }
                    }).subscribeOn(Schedulers.io());
                }
            })
            .subscribe(new Observer<Integer>() {
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
                public void onNext(Integer value) {
                    log.debug("onNext : {}", value);
                }
            });

        waitForObservable();

    }
}
