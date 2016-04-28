package com.smp.rxplayround.quiz;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;

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

    }

}
