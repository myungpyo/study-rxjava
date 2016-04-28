package com.smp.rxplayround.quiz;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by myungpyo.shim on 2016. 4. 28..
 *
 * 주어진 배열 (data) 의 각 항목 emit 하되, 아래 룰을 따른다.
 * 1. 처음 2개 항목은 제외
 * 2. 이후 5개 항목만 포함
 * 3. 발행되는 항목 logging, complete event logging
 */
@Slf4j
public class Quiz1 extends BasePlayground {

    @Test
    public void quiz() throws Exception {
        Integer data[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        //TODO : Answer the question
    }
}
