package com.smp.rxplayround.sample.operator.filter;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class DistinctObject extends BasePlayground {

    @Test
    public void play() throws Exception {

        Person[] people = new Person[]{
                new Person(0, "Peter"), new Person(0, "Harry"),
                new Person(1, "Sam"),
                new Person(3, "Oliver"), new Person(3, "Jack"), new Person(3, "Bruno")
        };

        Observable<Person> observable = Observable.from(people);

        observable.distinct(new Func1<Person, Integer>() {
            @Override
            public Integer call(Person person) {
                return person.groupId;
            }
        }).subscribe(new Observer<Person>() {
            @Override
            public void onCompleted() {
                log.debug("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                log.debug("onError : {}", e.getMessage());
            }

            @Override
            public void onNext(Person value) {
                log.debug("onNext : {}", value);
            }
        });

    }

    static class Person {
        int groupId;
        String name;

        public Person(int groupId, String name) {
            this.groupId = groupId;
            this.name = name;
        }

        @Override
        public String toString() {
            return name + " of Group " + groupId;
        }
    }
}
