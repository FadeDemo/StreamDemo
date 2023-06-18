package org.fade.demo.streamdemo.reactive;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.LongStream;

/**
 * @author fade
 */
public class MySubscriberExample {

    public static void main(String[] args) {
        try (SubmissionPublisher<Long> publisher = new SubmissionPublisher<>()) {
            MySubscriber<Long> subscriber = new MySubscriber<>();
            publisher.subscribe(subscriber);
            LongStream.range(1, 6).forEach(publisher::submit);
            Thread.sleep(3000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
