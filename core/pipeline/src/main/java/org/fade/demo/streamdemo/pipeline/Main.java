package org.fade.demo.streamdemo.pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fade
 * @date 2021/12/29
 */
public class Main {

    public static void main(String[] args) {
        // 示例程序，主要用于debug
        List<String> list = new ArrayList<>(16);
        list.add("A");
        list.add("B");
        list.add("C");
        List<String> collect = list.stream().filter("A"::equals)
                .sorted((x, y) -> {
                    if (x == null) {
                        return -1;
                    } else if (y == null) {
                        return 1;
                    } else {
                        return x.compareTo(y);
                    }
                })
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        System.out.println(collect);
    }

}
