package com.smp.rxplayround.quiz;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.GroupedObservable;

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


    }

}
