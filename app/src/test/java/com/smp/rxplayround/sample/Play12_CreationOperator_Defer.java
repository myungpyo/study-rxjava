package com.smp.rxplayround.sample;

import com.smp.rxplayround.BasePlayground;
import com.smp.rxplayround.support.Utils;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.functions.Func0;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class Play12_CreationOperator_Defer extends BasePlayground {

    @Test
    public void printStrings() throws Exception {

        //Below method are not supported under java 8
//        Observable.defer(this::createBigGiantObject);

        Observable<BigGiantObject> deferredObserble = Observable.defer(new Func0<Observable<BigGiantObject>>() {
            @Override
            public Observable<BigGiantObject> call() {
                return Observable.just(createBigGiantObject());
            }
        });

        Utils.sleep(100);

        deferredObserble.subscribe();
    }

    private BigGiantObject createBigGiantObject() {
        return new BigGiantObject();
    }

    static class BigGiantObject {

        public BigGiantObject() {
            Utils.sleep(300);
            log.debug("Big Gient Object has been created.");
        }
    }
}
