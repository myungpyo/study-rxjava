package com.smp.rxplayround.sample;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class Play1_EmitFromStringArray extends BasePlayground {

    @Test
    public void printStrings() throws Exception {
        Observable.from(new String[]{"test1", "test2", "test3"})
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
