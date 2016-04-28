package com.smp.rxplayround.support;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func2;

/**
 * Created by myungpyo.shim on 2016. 4. 27..
 */
public class ChainedObservable<ChainedData, T> extends Observable<ChainedData> {

    protected ChainedObservable(OnSubscribe<ChainedData> f) {
        super(f);
    }

    public static<ChainedData, T> ChainedObservable<ChainedData, T> from(final ChainedData initData, final Observable<T> observable, final DataAbsorbable<ChainedData, T> dataAbsorbable) {
        return from(Observable.just(initData), observable, dataAbsorbable);
    }

    public static<ChainedData, T> ChainedObservable<ChainedData, T> from(final Observable<ChainedData> initData, final Observable<T> observable, final DataAbsorbable<ChainedData, T> dataAbsorbable) {
        return new ChainedObservable<>(new OnSubscribe<ChainedData>() {
            @Override
            public void call(Subscriber<? super ChainedData> subscriber) {
                combineLatest(initData, observable, dataAbsorbable).subscribe(subscriber);
            }
        });
    }

    public<OT> ChainedObservable<ChainedData, T>  chain(final Observable<OT> observable, final DataAbsorbable<ChainedData, OT> dataAbsorbable) {
        return new ChainedObservable<>(new OnSubscribe<ChainedData>() {
            @Override
            public void call(Subscriber<? super ChainedData> subscriber) {
                combineLatest(ChainedObservable.this, observable, dataAbsorbable).subscribe(subscriber);
            }
        });
    }
    public<OT> ChainedObservable<ChainedData, T>  chainIf(boolean predicate, final Observable<OT> observable, final DataAbsorbable<ChainedData, OT> dataAbsorbable) {
        if (!predicate) {
            return this;
        }

        return chain(observable, dataAbsorbable);
    }

    public<OT> ChainedObservable<ChainedData, T>  chainUsingAggData(final ObservableCreator<OT, ChainedData> observableCreator, final DataAbsorbable<ChainedData, OT> dataAbsorbable) {
        return new ChainedObservable<>(new OnSubscribe<ChainedData>() {
            @Override
            public void call(final Subscriber<? super ChainedData> subscriber) {

                ChainedObservable.this.last().subscribe(new Action1<ChainedData>() {
                    @Override
                    public void call(ChainedData data) {
                        combineLatest(Observable.just(data), observableCreator.create(data), dataAbsorbable).subscribe(subscriber);
                    }
                });
            }
        });
    }

    public<OT> ChainedObservable<ChainedData, T>  chainUsingAggData(boolean predicate, final ObservableCreator<OT, ChainedData> observableCreator, final DataAbsorbable<ChainedData, OT> dataAbsorbable) {
        if (!predicate) {
            return this;
        }

        return chainUsingAggData(observableCreator, dataAbsorbable);
    }

    public interface DataAbsorbable<Absorber, Target> extends Func2<Absorber, Target, Absorber> {
    }

    public static<A, T> DataAbsorbable<A, T> NON_ABSORBER() {
        return new DataAbsorbable<A, T>() {
            @Override
            public A call(A a, T t) {
                return a;
            }
        };
    }

    public interface ObservableCreator<T, AggData> {
        Observable<T> create(AggData prevData);
    }
}
