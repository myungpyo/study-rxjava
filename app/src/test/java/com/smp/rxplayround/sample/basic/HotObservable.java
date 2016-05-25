package com.smp.rxplayround.sample.basic;

import com.smp.rxplayround.BasePlayground;
import com.smp.rxplayround.support.Utils;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.observables.ConnectableObservable;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class HotObservable extends BasePlayground {

    @Test
    public void play() throws Exception {

        TemperatureObservable temperatureObservable = new TemperatureObservable();
        //Start
        ConnectableObservable<Integer> observable = temperatureObservable.publish();
        observable.connect();

        //Register first subscriber after a few seconds later
        observable.delay(2, TimeUnit.SECONDS)
                .take(5)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        log.debug("#Subscribe1 is completed successfully");
                        stopWaitingForObservable();
                    }
                    @Override
                    public void onError(Throwable e) {
                        log.debug("#Subscribe1 is completed with an error : {}", e.getMessage());
                        stopWaitingForObservable();
                    }
                    @Override
                    public void onNext(Integer integer) {
                        log.debug("#Subscribe1 is received : {}", integer);
                    }
                });


        //Register second subscriber after a few seconds later
        observable.delay(5, TimeUnit.SECONDS)
                .take(2)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        log.debug("#Subscribe2 is completed successfully");
                        stopWaitingForObservable();
                    }
                    @Override
                    public void onError(Throwable e) {
                        log.debug("#Subscribe2 is completed with an error : {}", e.getMessage());
                        stopWaitingForObservable();
                    }
                    @Override
                    public void onNext(Integer integer) {
                        log.debug("#Subscribe2 is received : {}", integer);
                    }
                });

        waitForObservable(2);
    }


    static class TemperatureObservable extends Observable<Integer> {

        /**
         * Creates an Observable with a Function to execute when it is subscribed to.
         * <p/>
         * <em>Note:</em> Use {@link #create(OnSubscribe)} to create an Observable, instead of this constructor,
         * unless you specifically have a need for inheritance.
         */
        protected TemperatureObservable() {

            super(new OnSubscribe<Integer>() {
                @Override
                public void call(final Subscriber<? super Integer> subscriber) {
                    log.debug("#TemperatureObservable is called.");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            log.debug("#HOT-Observable is started.");
                            while (true) {
                                increase();
                                decrease();
                            }
                        }

                        private void increase() {
                            for (int num = 10; num < 31; num++) {
                                //Demonstrate emitting latency
                                Utils.sleep(1000);
                                log.debug("#HOT-Observable is emitting {}", num);
                                subscriber.onNext(num);

                            }
                        }

                        private void decrease() {
                            for (int num = 30; num > 9; num++) {
                                //Demonstrate emitting latency
                                Utils.sleep(1000);
                                log.debug("#HOT-Observable is emitting {}", num);
                                subscriber.onNext(num);
                            }
                        }
                    }).start();
                }
            });


        }
    }

}
