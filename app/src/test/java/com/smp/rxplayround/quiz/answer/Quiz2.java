package com.smp.rxplayround.quiz.answer;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by myungpyo.shim on 2016. 4. 28..
 *
 * 1. 500ms 간격으로 Long 값을 발행
 * 2. 발행되는 Long 값 중 홀수만 수신
 * 3. 최대 수신 개수는 5개
 * 4. 발행되는 항목 logging, complete event logging
 * 5. 테스트 케이스 종료 확인
 */
@Slf4j
public class Quiz2 extends BasePlayground {

    @Test
    public void quiz() throws Exception {

        Observable.interval(1, TimeUnit.SECONDS)
            .filter(new Func1<Long, Boolean>() {
                @Override
                public Boolean call(Long value) {
                    return value % 2 != 0;
                }
            })
            .take(5)
            .subscribe(new Observer<Long>() {
                @Override
                public void onCompleted() {
                    log.debug("onCompleted");
                    stopWaitingForObservable();
                }

                @Override
                public void onError(Throwable e) {
                    stopWaitingForObservable();
                }

                @Override
                public void onNext(Long value) {
                    log.debug("value : {}", value);
                }
            });

        waitForObservable();
    }
}
