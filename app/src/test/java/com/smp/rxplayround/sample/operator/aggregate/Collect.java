package com.smp.rxplayround.sample.operator.aggregate;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.functions.Action2;
import rx.functions.Func0;

/**
 * Created by myungpyo.shim on 2016. 5. 25..
 */

@Slf4j
public class Collect extends BasePlayground {

    @Test
    public void play() throws Exception {

        Observable<TestData1> observable1 = Observable.just(new TestData1("Wow!!!"));
        Observable<TestData2> observable2 = Observable.just(new TestData2(500));
        Observable<TestData3> observable3 = Observable.just(new TestData3(3.141592f));

        Observable.merge(observable1, observable2, observable3).collect(new Func0<AggregatedResult>() {
            @Override
            public AggregatedResult call() {
                return new AggregatedResult();
            }
        }, new Action2<AggregatedResult, AggregateElement<AggregatedResult>>() {
            @Override
            public void call(AggregatedResult result, AggregateElement<AggregatedResult> element) {
                element.apply(result);
            }
        }).subscribe(new Observer<AggregatedResult>() {
            @Override
            public void onCompleted() {
                log.debug("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                log.debug("onError : {}", e.getMessage());
            }

            @Override
            public void onNext(AggregatedResult result) {
                log.debug("onNext : {}", result);
            }
        });
    }


    static class AggregatedResult {

        String stringValue;
        int intValue;
        float floatValue;

        public AggregatedResult() {
        }

        @Override
        public String toString() {
            return "StringVal : " + stringValue + " IntVal : " + intValue + " FloatVal : " + floatValue;
        }
    }

    interface AggregateElement<T> {
        void apply(T aggregateResult);
    }


    private static class TestData1 implements AggregateElement<AggregatedResult> {
        String value;

        TestData1(String value) {
            this.value = value;
        }

        @Override
        public void apply(AggregatedResult aggregateResult) {
            aggregateResult.stringValue = value;
        }
    }

    private static class TestData2 implements AggregateElement<AggregatedResult> {
        int value;

        TestData2(int value) {
            this.value = value;
        }

        @Override
        public void apply(AggregatedResult aggregateResult) {
            aggregateResult.intValue = value;
        }
    }

    private static class TestData3 implements AggregateElement<AggregatedResult> {
        float value;

        TestData3(float value) {
            this.value = value;
        }

        @Override
        public void apply(AggregatedResult aggregateResult) {
            aggregateResult.floatValue = value;
        }
    }
}
