package org.fade.demo.streamdemo.reactive;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * 自定义处理器示例
 * @author fade
 */
public class MyProcessor extends SubmissionPublisher<String>
        implements Flow.Processor<Long, String> {

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(Long item) {
        System.out.println("Received item from Publisher: " + item);
        this.submit("Processor: " + item.toString());
        // 再次请求元素，不然只消费一次
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace(System.err);
        closeExceptionally(throwable);
    }

    @Override
    public void onComplete() {
        System.out.println("Processing completed");
        this.close();
    }

}
