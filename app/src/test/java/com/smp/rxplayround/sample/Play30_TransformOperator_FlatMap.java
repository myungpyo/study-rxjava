package com.smp.rxplayround.sample;

import com.smp.rxplayround.BasePlayground;
import com.smp.rxplayround.support.Utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class Play30_TransformOperator_FlatMap extends BasePlayground {

    @Test
    public void printStrings() throws Exception {

        Observable.from(new String[]{"1-3","5-8", "10-15"})
            .flatMap(new Func1<String, Observable<Integer>>() {
                @Override
                public Observable<Integer> call(final String range) {
                    return Observable.create(new Observable.OnSubscribe<Integer>() {
                        @Override
                        public void call(Subscriber<? super Integer> subscriber) {
                            if (subscriber.isUnsubscribed()) {
                                return;
                            }

                            String[] split = range.split("-");
                            if (split.length != 2) {
                                throw new IllegalArgumentException("Check range format. ex> lowNum-HighNum");
                            }

                            int minNum = Integer.parseInt(split[0]);
                            int maxNum = Integer.parseInt(split[1]);

                            for (int num = minNum; num <= maxNum; num++) {
                                subscriber.onNext(num);
                            }
                            subscriber.onCompleted();
                        }
                    });
                }
            })
            .subscribe(new Observer<Integer>() {
                @Override
                public void onCompleted() {
                    log.debug("onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    log.debug("onError : {}", e.getMessage());
                }

                @Override
                public void onNext(Integer value) {
                    log.debug("onNext : {}", value);
                }
            });

    }

    @Test
    public void playWithMockApi() throws Exception {


        List<String> urlList = new ArrayList<>();
        urlList.add("http://test1.com");
        urlList.add("http://test2.com");
        urlList.add("http://test3.com");

        Observable.just(urlList)
                .flatMap(new Func1<List<String>, Observable<DownloadResult>>() {
                    @Override
                    public Observable<DownloadResult> call(final List<String> urlList) {

                        return Observable.create(new Observable.OnSubscribe<DownloadResult>() {
                            @Override
                            public void call(final Subscriber<? super DownloadResult> subscriber) {
                                subscriber.onStart();

                                for (String url : urlList) {
                                    MockRetrofitApi.request(url, true).subscribe(new Action1<DownloadResult>() {
                                        @Override
                                        public void call(DownloadResult downloadResult) {
                                            log.debug("onSubscribed : {}", downloadResult);
                                            subscriber.onNext(downloadResult);
                                        }
                                    });
                                }

                                //Uncomment below snippet to test partially failed
//                                MockRetrofitApi.request("http://fail.test1.com", false).subscribe(new Action1<DownloadResult>() {
//                                    @Override
//                                    public void call(DownloadResult downloadResult) {
//                                        subscriber.onNext(downloadResult);
//                                    }
//                                });

                                subscriber.onCompleted();
                            }
                        });
                    }
                })
                .all(new Func1<DownloadResult, Boolean>() {
                    @Override
                    public Boolean call(DownloadResult downloadResult) {
                        return downloadResult.statusCode >= 200 && downloadResult.statusCode < 300;
                    }
                })
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        log.debug("onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.debug("onError : {}", e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        log.debug("onNext : success : {}", aBoolean);
                    }
                });



    }

    static class DownloadModel {
        private List<String> urlList;

        public DownloadModel(List<String> urlList) {
            this.urlList = urlList;
        }
    }

    static class DownloadResult {
        int statusCode;
        String message;

        public DownloadResult(int statusCode, String message) {
            this.statusCode = statusCode;
            this.message = message;
        }

        @Override
        public String toString() {
            return String.valueOf(statusCode) + " " + message;
        }
    }

    static class MockRetrofitApi {

        public static Observable<DownloadResult> request(final String url, final boolean success) {
            return Observable.create(new Observable.OnSubscribe<DownloadResult>() {
                @Override
                public void call(Subscriber<? super DownloadResult> subscriber) {
                    subscriber.onStart();
                    subscriber.onNext(new DownloadResult(success ? 200 : 500, url + " : OK"));
                }
            });
        }
    }
}
