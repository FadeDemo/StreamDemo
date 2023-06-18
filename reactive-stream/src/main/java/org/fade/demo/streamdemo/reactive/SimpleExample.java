package org.fade.demo.streamdemo.reactive;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.LongStream;

/**
 * @author fade
 */
public class SimpleExample {

    public static void main(String[] args) {
//        oClose();
        tClose();
    }

    private static void tClose() {
        CompletableFuture<Void> future;
        try (SubmissionPublisher<Long> publisher = new SubmissionPublisher<>()) {
            // 先订阅再发布元素，反过来的话订阅者可能会无元素消费
            future = publisher.consume(System.out::println);
            LongStream.range(1, 6).forEach(publisher::submit);
        }
        assert future != null;
        while (!future.isDone()) {

        }
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static void oClose() {
        try (SubmissionPublisher<Long> publisher = new SubmissionPublisher<>()) {
            CompletableFuture<Void> future = publisher.consume(System.out::println);
            LongStream.range(1, 6).forEach(publisher::submit);
            publisher.close();
            while (!future.isDone()) {

            }
            future.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
