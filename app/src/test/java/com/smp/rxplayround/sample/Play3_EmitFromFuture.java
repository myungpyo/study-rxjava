package com.smp.rxplayround.sample;

import com.smp.rxplayround.BasePlayground;
import com.smp.rxplayround.support.Utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class Play3_EmitFromFuture extends BasePlayground {

    @Test
    public void printStrings() throws Exception {
        Future<String> stringFuture = Executors.newCachedThreadPool().submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Utils.sleep(3000);
                return "test1";
            }
        });

        Observable.from(stringFuture)
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
                    public void onNext(String s) {
                        log.debug("onNext : {}", s);
                    }
                });
    }
}
