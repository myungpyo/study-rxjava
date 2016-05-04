package com.smp.rxplayround.support;

import java.util.Collection;
import java.util.Iterator;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class Utils {

    public static void sleep(long timeInMaill) {
        try {
            Thread.sleep(timeInMaill);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static<T> String convertToCommaSeparatedString(Collection<T> collection) {

        if (collection == null || collection.size() == 0) {
            return  "";
        }

        Iterator<T> iterator = collection.iterator();
        StringBuilder builder = new StringBuilder();

        while (iterator.hasNext()) {
            builder.append(iterator.next());
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }

        return builder.toString();
    }

    public static Observable<Integer> createInfiniteRandomIntervalIntegerEmitter() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                int prev = 0;
                while (!subscriber.isUnsubscribed()) {
                    int maxEmitNum = prev + (int)(Math.random() * 10) + 1;
                    long interval = (long)(Math.random() * 5000);

                    log.debug("Emit items {} ~ {} and waiting for the next tick : {}", prev, maxEmitNum - 1, interval);

                    for (int num = prev; num < maxEmitNum; num++) {
                        subscriber.onNext(num);
                    }
                    prev = maxEmitNum;

                    Utils.sleep(interval);
                }
            }
        });
    }

    public static Observable<Integer> createFiniteRegulerIntervalIntegerEmitter(final int minNum, final int maxNum) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {

                for (int num = minNum; num < maxNum + 1; num++) {
                    if(subscriber.isUnsubscribed()) {
                        break;
                    }

                    log.debug("Emit item {}", num);
                    subscriber.onNext(num);

                    Utils.sleep(200);
                }
                subscriber.onCompleted();
            }
        });
    }
}
