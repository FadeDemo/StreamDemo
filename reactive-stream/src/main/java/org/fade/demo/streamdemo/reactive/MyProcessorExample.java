package org.fade.demo.streamdemo.reactive;

import java.util.concurrent.SubmissionPublisher;
import java.util.stream.LongStream;

/**
 * @author fade
 */
public class MyProcessorExample {

    public static void main(String[] args) {
        try (SubmissionPublisher<Long> publisher = new SubmissionPublisher<>();
             MyProcessor processor = new MyProcessor()) {
            // processor的创建可以放到下面来
            publisher.subscribe(processor);
            MySubscriber<String> subscriber = new MySubscriber<>();
            processor.subscribe(subscriber);
            LongStream.range(1, 6).forEach(publisher::submit);
            Thread.sleep(3000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
