package org.fade.demo.streamdemo.reactive;

import java.util.concurrent.Flow;

/**
 * 自定义订阅者示例
 * @author fade
 */
public class MySubscriber<T> implements Flow.Subscriber<T> {

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        // 开始请求元素
        // 必须有，不然一个元素不消费
        this.subscription.request(1);
    }

    @Override
    public void onNext(T item) {
        System.out.println("Received item from Publisher: " + item);
        // 再次请求元素，不然只消费一次
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace(System.err);
    }

    @Override
    public void onComplete() {
        System.out.println("Processing completed");
    }

}
