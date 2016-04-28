package com.smp.rxplayround.quiz.answer;

import com.smp.rxplayround.BasePlayground;
import com.smp.rxplayround.support.Utils;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * Created by myungpyo.shim on 2016. 4. 28..
 *
 * 1. emitter 로 발행되는 아이템을 3개 단위로 묶어 합을 구하여 출력
 * 2. 총 10개의 묶음만 출력
 * 2. 출력 형식을 아래와 같아야 함
 *
 * ex>
 * Sum of 0, 1, 2
 * = 3
 * Sum of 3, 4, 5
 * = 12
 * ... 총 10개
 */
@Slf4j
public class Quiz5 extends BasePlayground {

    @Test
    public void quiz() throws Exception {

        Observable<Long> emitter = Observable.interval(500, TimeUnit.MILLISECONDS);

        //TODO : Answer the question
        emitter
            .buffer(3)
            .map(new Func1<List<Long>, Long>() {
                @Override
                public Long call(List<Long> valueList) {
                    log.debug("Sum of {}", Utils.convertToCommaSeparatedString(valueList));
                    return sum(valueList);
                }
            })
            .take(10)
            .subscribe(new Observer<Long>() {
                @Override
                public void onCompleted() {
                    stopWaitingForObservable();
                }

                @Override
                public void onError(Throwable e) {
                    stopWaitingForObservable();
                }

                @Override
                public void onNext(Long value) {
                    log.debug("= {}", value);
                }
            });

        waitForObservable();
    }

    private Long sum(List<Long> list) {
        if (list == null || list.size() == 0) {
            return 0L;
        }

        long result = 0L;
        for (Long value : list) {
            result += value;
        }
        return result;
    }
}
