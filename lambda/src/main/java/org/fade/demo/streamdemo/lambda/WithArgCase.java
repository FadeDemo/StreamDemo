package org.fade.demo.streamdemo.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 有参数情况下
 *
 * @author fade
 * @date 2021/12/28
 */
public class WithArgCase {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("I", "love", "you", "too");
        // java7匿名内部类写法
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                if (s1 == null) {
                    return -1;
                }
                if (s2 == null) {
                    return 1;
                }
                return s1.length() - s2.length();
            }
        });
        System.out.println(list);
        // java8 lambda表达式写法
        list.sort((x, y) -> {
            if (x == null) {
                return 1;
            }
            if (y == null) {
                return -1;
            }
            return y.length() - x.length();
        });
        System.out.println(list);
    }

}
