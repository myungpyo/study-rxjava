package com.smp.rxplayround.sample.basic;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class EmitFromWorkerThread extends BasePlayground {

    @Test
    public void play() throws Exception {
        Observable.from(new String[]{"test1", "test2", "test3"})
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
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
                    public void onNext(String s) {
                        log.debug("onNext : {}", s);
                    }
                });

        waitForObservable();
    }
}
