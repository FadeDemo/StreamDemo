package org.fade.demo.streamdemo.lambda;

/**
 * 没有参数情况
 *
 * @author fade
 * @date 2021/12/28
 */
public class NoArgCase {

    public static void main(String[] args) {
        // java7匿名内部类写法
        Thread java7 = new Thread(new Runnable(){
            @Override
            public void run(){
                System.out.println("Thread run()");
            }
        }, "java7");
        // java8 lambda表达式写法
        // 函数接口方法语句只有一条
        Thread java8one = new Thread(
                () -> System.out.println("Thread run()"), "java8one"
        );
        // 函数接口方法语句有多条
        Thread java8etc = new Thread(
                () -> {
                    System.out.print("Hello");
                    System.out.println("Fade");
                }, "java8etc"
        );
        java7.start();
        java8one.start();
        java8etc.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
