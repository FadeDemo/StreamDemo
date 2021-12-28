package org.fade.demo.streamdemo.lambda;

/**
 * 自定义示例
 *
 * @author fade
 * @date 2021/12/28
 */
public class CustomCase {

    public static void main(String[] args) {
        // java7 匿名内部类写法
        final String str = "Hello Fade";
        print(str, new MyFunctionalInterface<String>() {
            @Override
            public void accept(String s) {
                System.out.println(str);
            }
        });
        print(str, x -> System.out.println(x));
    }

    public static <T> void print(T arg1, MyFunctionalInterface<T> arg2) {
        arg2.accept(arg1);
    }

}
