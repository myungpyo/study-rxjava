package com.smp.rxplayround.quiz;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by myungpyo.shim on 2016. 4. 28..
 *
 * 주어진 stringIndexArray 는 특정 문장을 charTable 의 index 로 암호화 한 것이다.
 * 아래 명령을 순서대로!! 따라 복호화 후 해당 문장만 한 행으로 출력하라.
 *
 * 1. charTable 최대 index 보다 큰 index 는 무시
 * 2. 순서대로 처리하다가 이전 값과 같은 값이 나올 경우 무시
 * 3. 마지막 7글자는 무시
 * 4. 결과 인덱스들을 charTable 의 문자로 전환
 * 5. 각각 발행되는 문자들을 한 문장으로 출력
 */
@Slf4j
public class Quiz3 extends BasePlayground {

    final String charTable[] = {
            /*0-6*/ "A", "B", "C", "D", "E", "F", "G",
            /*7-13*/ "H", "I", "J", "K", "L", "M", "N",
            /*14-20*/ "O", "P", "Q", "R", "S", "T", "U",
            /*21-27*/ "V", "W", "X", "Y", "Z", " ", ","
    };

    @Test
    public void quiz() throws Exception {

        Integer stringIndexArray[] = {
                24, 14, 14, 14, 29, 30, 20, 26, 26, 26, 3, 8, 8,
                8, 3, 26, 0, 58, 36, 26, 6, 6, 6, 100, 17, 4, 0,
                0, 0, 0, 19, 19, 26, 50, 9, 14, 1, 27, 26, 22, 17,
                17, 17, 14, 544, 13, 6, 6, 70};

        //TODO : Answer the question


    }
}
