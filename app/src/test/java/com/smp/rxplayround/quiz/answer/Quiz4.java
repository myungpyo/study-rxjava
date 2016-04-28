package com.smp.rxplayround.quiz.answer;

import com.smp.rxplayround.BasePlayground;
import com.smp.rxplayround.support.Utils;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;

/**
 * Created by myungpyo.shim on 2016. 4. 28..
 *
 * 주어진 문자열 배열(strings)을 로그 한행에 한문자씩 출력하되 아래 룰을 따른다.
 * 1. '-' 기호를 포함하지 않은 문자열은 그대로 출력
 * 2. '-' 기호가 포함된 문자열은 범위 문자열이므로 해당 범위의 수의 합을 출력
 */
@Slf4j
public class Quiz4 extends BasePlayground {

    @Test
    public void quiz() throws Exception {

        String[] strings = {"A", "1-3", "B", "C", "5-8", "D", "E", "F", "10-15"};

        //TODO : Answer the question
        Observable.from(strings)
            .groupBy(new Func1<String, String>() {
                @Override
                public String call(String s) {
                    return s.contains("-") ? "Range" : "Single";
                }
            })
            .flatMap(new Func1<GroupedObservable<String, String>, Observable<String>>() {
                @Override
                public Observable<String> call(final GroupedObservable<String, String> groupedObservable) {
                    if (groupedObservable.getKey().equals("Single")) {
                        return groupedObservable;
                    } else if (groupedObservable.getKey().equals("Range")) {
                        return Observable.create(new Observable.OnSubscribe<String>() {
                            @Override
                            public void call(final Subscriber<? super String> subscriber) {
                                groupedObservable.subscribe(new Action1<String>() {
                                    @Override
                                    public void call(String range) {
                                        subscriber.onNext(sumRange(range));
                                        subscriber.onCompleted();
                                    }
                                });
                            }
                        });
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            })
            .subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    log.debug("{}", s);
                }
            });



    }

    private String sumRange(String range) {
        String[] split = range.split("-");
        if (split.length != 2) {
            throw new IllegalArgumentException("Check range format. ex> lowNum-HighNum");
        }

        int minNum = Integer.parseInt(split[0]);
        int maxNum = Integer.parseInt(split[1]);
        int sum = 0;

        for (int num = minNum; num <= maxNum; num++) {
            sum += num;
        }
        return String.valueOf(sum);
    }
}
