package org.fade.demo.streamdemo.lambda;

/**
 * this在lambda中的含义
 *
 * @author fade
 * @date 2021/12/28
 */
public class ThisInLambda {

    private final Runnable r1 = () -> {
        System.out.println(this);
    };

    public static void main(String[] args) {
        ThisInLambda instance = new ThisInLambda();
        instance.r1.run();
    }

    @Override
    public String toString() {
        return "this in lambda represent a ThisInLambda instance";
    }

}
