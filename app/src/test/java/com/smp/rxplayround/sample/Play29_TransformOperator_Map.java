package com.smp.rxplayround.sample;

import com.smp.rxplayround.BasePlayground;
import com.smp.rxplayround.support.Utils;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
public class Play29_TransformOperator_Map extends BasePlayground {

    @Test
    public void play() throws Exception {

        Observable.from(new Integer[]{1,2,3,4,5})
            .map(new Func1<Integer, String>() {
                @Override
                public String call(Integer integer) {
                    switch (integer) {
                        case 1 :
                            return "One";
                        case 2 :
                            return "Two";
                        case 3 :
                            return "Three";
                        case 4 :
                            return "Four";
                        case 5 :
                            return "Five";
                        default:
                            return "Other";
                    }
                }
            })
            .subscribe(new Observer<String>() {
                @Override
                public void onCompleted() {
                    log.debug("onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    log.debug("onError : {}", e.getMessage());
                }

                @Override
                public void onNext(String value) {
                    log.debug("onNext : {}", value);
                }
            });

    }
}
