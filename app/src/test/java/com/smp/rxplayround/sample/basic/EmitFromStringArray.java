package com.smp.rxplayround.sample.basic;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 * Simple observable test. just emit some strings.
 */
@Slf4j
public class EmitFromStringArray extends BasePlayground {

    @Test
    public void play() throws Exception {
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
