package com.smp.rxplayround.sample.usecase;

import com.smp.rxplayround.BasePlayground;
import com.smp.rxplayround.support.Utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by myungpyo.shim on 2016. 7. 19..
 * Scenario
 * 1. Request first page to mock server
 * 2. If there are more data, request other pages
 * 3. Merge all pages into one
 * 4. Sort result data
 */
@Slf4j
public class RequestPaging extends BasePlayground {

    //Test server data
    private static final String[] ALPHABET = {
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y",
            "Z"
    };

    //Test server response
    private static class Response {
        @Getter
        int totalCount;
        List<String> charList;

        public Response(int totalCount, List<String> charList) {
            this.totalCount = totalCount;
            this.charList = charList;
        }

        public Response(Response source1, Response source2) {
            this.totalCount = source1.getTotalCount();
            this.charList = new ArrayList<>();
            this.charList.addAll(source1.charList);
            this.charList.addAll(source2.charList);
        }

        public String dump() {
            return "Total count : " + totalCount
                    + " List : " + Utils.convertToCommaSeparatedString(charList);
        }
    }

    //Mock server request
    static Observable<Response> mockRequest(int startIndex, int count) {

        count = Math.min(count, ALPHABET.length);
        int maxIndex = startIndex + count;

        List<String> result = new ArrayList<>();
        for (int index = startIndex; index < maxIndex; index++) {
            result.add(ALPHABET[index]);
        }

        return Observable.just(new Response(ALPHABET.length, result));
    }

    @Test
    public void play() throws Exception {
        //Default page size is 5
        final int COUNT_PER_PAGE = 5;

        //Request first page
        mockRequest(0, COUNT_PER_PAGE)
        .flatMap(new Func1<Response, Observable<Response>>() {
            @Override
            public Observable<Response> call(Response response) {
                //If there are not more pages, return just 1 observable
                if (response.totalCount <= COUNT_PER_PAGE) {
                    return Observable.just(response);
                }

                List<Observable<Response>> observableList = new ArrayList<>();
                //Add first page
                observableList.add(Observable.just(response).observeOn(Schedulers.io()));

                //Add other pages
                for (int startIndex = COUNT_PER_PAGE;
                     startIndex < response.totalCount;
                     startIndex+=COUNT_PER_PAGE) {
                    observableList.add(
                            mockRequest(startIndex, Math.min(response.totalCount - startIndex, COUNT_PER_PAGE))
                                    .observeOn(Schedulers.io()));
                }

                //Merge all pages
                return Observable.merge(observableList);
            }
        //Aggregate all pages into 1 page
        }).reduce(new Func2<Response, Response, Response>() {
            @Override
            public Response call(Response response, Response response2) {
                return new Response(response, response2);
            }
        //Sort result list by ascending alphabet order
        }).map(new Func1<Response, Response>() {
            @Override
            public Response call(Response response) {
                Collections.sort(response.charList);
                return response;
            }
        }).subscribe(new Observer<Response>() {
            @Override
            public void onCompleted() {
                log.debug("onCompleted");
                stopWaitingForObservable();
            }

            @Override
            public void onError(Throwable e) {
                log.debug("onError");
                stopWaitingForObservable();
            }

            @Override
            public void onNext(Response response) {
                log.debug(response.dump());
            }
        });

        log.debug("waiting...");
        waitForObservable();
        log.debug("finished.");
    }


}
