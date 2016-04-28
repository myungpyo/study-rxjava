package com.smp.rxplayround.sample;

import com.smp.rxplayround.BasePlayground;
import com.smp.rxplayround.support.ChainedObservable;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class Play100_Custom_Chain extends BasePlayground {

    @Test
    public void printStrings() throws Exception {

        ChainedObservable.from(new AggregatedResult(""), Observable.just(new TestData1("aaa")), new ChainedObservable.DataAbsorbable<AggregatedResult, TestData1>() {
            @Override
            public AggregatedResult call(AggregatedResult aggregatedResult, TestData1 testData1) {
                aggregatedResult.firstValue = testData1.value;
                aggregatedResult.aggregatedValue = aggregatedResult.aggregatedValue + "+" + testData1.value;
                return aggregatedResult;
            }
        }).chain(Observable.just(new TestData2(111)), new ChainedObservable.DataAbsorbable<AggregatedResult, TestData2>() {
            @Override
            public AggregatedResult call(AggregatedResult aggregatedResult, TestData2 testData2) {
                aggregatedResult.secondValue = testData2.value;
                aggregatedResult.aggregatedValue = aggregatedResult.aggregatedValue + "+" + Integer.toString(testData2.value);
                return aggregatedResult;
            }
        }).chainUsingAggData(new ChainedObservable.ObservableCreator<TestData2, AggregatedResult>() {
            @Override
            public Observable<TestData2> create(AggregatedResult prevData) {
                return Observable.just(new TestData2(prevData.secondValue * 2));
            }
        }, new ChainedObservable.DataAbsorbable<AggregatedResult, TestData2>() {
            @Override
            public AggregatedResult call(AggregatedResult aggregatedResult, TestData2 testData2) {
                aggregatedResult.aggregatedValue = aggregatedResult.aggregatedValue + "+" + Integer.toString(testData2.value);
                return aggregatedResult;
            }
        }).chain(Observable.just(new TestData3(12.3f)), new ChainedObservable.DataAbsorbable<AggregatedResult, TestData3>() {
            @Override
            public AggregatedResult call(AggregatedResult aggregatedResult, TestData3 testData3) {
                aggregatedResult.thirdValue = testData3.value;
                aggregatedResult.aggregatedValue = aggregatedResult.aggregatedValue + "+" + Float.toString(testData3.value);
                return aggregatedResult;
            }
        }).subscribe(new Observer<AggregatedResult>() {
            @Override
            public void onCompleted() {
                log.debug("onComplete");
            }

            @Override
            public void onError(Throwable e) {
                log.debug("onError");
            }

            @Override
            public void onNext(AggregatedResult aggregatedResult) {
                log.debug("onNext : {}", aggregatedResult.aggregatedValue);
            }
        });

    }

    static class TestData1 {
        String value;

        public TestData1(String value) {
            this.value = value;
        }
    }

    static class TestData2 {
        int value;

        public TestData2(int value) {
            this.value = value;
        }
    }

    static class TestData3 {
        float value;

        public TestData3(float value) {
            this.value = value;
        }
    }

    static class AggregatedResult {

        String firstValue;
        int secondValue;
        float thirdValue;

        String aggregatedValue;

        public AggregatedResult(String aggregatedValue) {
            this.aggregatedValue = aggregatedValue;
        }
    }

}
