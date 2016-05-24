package com.smp.rxplayround.sample;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.GroupedObservable;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class Play33_TransformOperator_GroupBy extends BasePlayground {

    @Test
    public void play() throws Exception {

        Observable.from(new Object[]{1, "A", "B", 2, 3, 4, 5, "C", "D"})
            .groupBy(new Func1<Object, String>() {
                @Override
                public String call(Object o) {
                    if (o instanceof Integer) {
                        return "Integer";
                    } else if (o instanceof String) {
                        return "String";
                    } else {
                        return "Other";
                    }
                }
            })
            .subscribe(new Observer<GroupedObservable<String, Object>>() {
                @Override
                public void onCompleted() {
                    log.debug("onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    log.debug("onError : {}", e.getMessage());
                }

                @Override
                public void onNext(final GroupedObservable<String, Object> groupedObservable) {
                    groupedObservable.subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object o) {
                            log.debug("onNext : GroupedObservable : key : {}, group item : {}", groupedObservable.getKey(), o);
                        }
                    });
                }
            });


    }
}
