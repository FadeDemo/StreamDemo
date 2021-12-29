package org.fade.demo.streamdemo.api;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * @author fade
 * @date 2021/12/28
 */
public class Main {

    public static void main(String[] args) {
        view();
        consume();
        forEach();
        filter();
        distinct();
        sorted();
        map();
        flatMap();
        reduce();
        collect();
        x2x();
        methodReference();
        collector();
    }

    /**
     * <p>解释 {@link Stream} 的视图特性，对 {@link Stream} 中
     * 的元素进行操作不会影响产生流的容器</p>
     * */
    public static void view() {
        List<String> list = new ArrayList<>(16);
        list.add("A");
        list.add("B");
        list.add("C");
        List<String> collect = list.stream().filter(x -> "A".equals(x)).collect(Collectors.toList());
        System.out.println(collect);
        System.out.println(list);
    }

    /**
     * <p>解释 {@link Stream} 的可消费性，{@link Stream} 只能
     * 被消费一次，一但被消费过了就会失效</p>
     * @throws IllegalStateException
     * */
    public static void consume() {
        Stream<String> stream = Stream.of("I", "love", "you", "too");
        stream.collect(Collectors.toList());
        try {
            stream.collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>forEach方法</p>
     * <p>对 {@link Stream} 中的元素逐一进行指定操作</p>
     * @see Consumer
     * @see Stream#forEach(Consumer)
     * */
    public static void forEach() {
        Stream<String> stream = Stream.of("I", "love", "you", "too");
        stream.forEach(str -> System.out.println(str));
    }

    /**
     * <p>filter方法</p>
     * <p>对 {@link Stream} 中的元素按指定的条件进行过滤</p>
     * @see Predicate
     * @see Stream#filter(Predicate)
     * */
    public static void filter() {
        Stream<String> stream = Stream.of("I", "love", "you", "too");
        stream.filter(str -> str.length() == 3)
                .forEach(str -> System.out.println(str));
    }

    /**
     * <p>distinct方法</p>
     * <p>去除 {@link Stream} 中的重复元素</p>
     * @see Stream#distinct()
     * */
    public static void distinct() {
        Stream<String> stream = Stream.of("I", "love", "you", "too", "too");
        stream.distinct()
                .forEach(str -> System.out.println(str));
    }

    /**
     * <p>sorted方法</p>
     * <p>对 {@link Stream} 中的元素进行排序</p>
     * @see Stream#sorted()
     * @see Stream#sorted(Comparator)
     * @see Comparator
     * */
    public static void sorted() {
        // 自定义排序
        Stream<String> stream = Stream.of("I", "love", "you", "too");
        stream.sorted((str1, str2) -> str1.length()-str2.length())
                .forEach(str -> System.out.println(str));
        // 自然顺序排序
        Stream<String> custom = Stream.of("I", "love", "you", "too");
        custom.sorted().forEach(str -> System.out.println(str));
    }

    /**
     * <p>map方法</p>
     * <p>对 {@link Stream} 中的元素进行映射</p>
     * @see Function
     * @see Stream#map(Function)
     * */
    public static void map() {
        Stream<String> stream = Stream.of("I", "love", "you", "too");
        stream.map(str -> str.toUpperCase())
                .forEach(str -> System.out.println(str));
    }

    /**
     * <p>flatMap方法</p>
     * <p>将 {@link Stream} 中的元素展开为 {@link Stream} </p>
     * |----|--------|
     * |1, 2| 3, 4, 5|
     * |1, 2, 3, 4, 5|
     * @see Function
     * @see Stream#flatMap(Function)
     * */
    public static void flatMap() {
        Stream<List<Integer>> stream = Stream.of(Arrays.asList(1,2), Arrays.asList(3, 4, 5));
        stream.flatMap(list -> list.stream())
                .forEach(i -> System.out.println(i));
    }

    /**
     * <p>reduce方法</p>
     * <p>规约操作-通过某个连接动作将所有元素汇总成一个汇总结果的过程</p>
     * <p>像 {@link Stream#min(Comparator)} 这种规约操作底层还是
     * 使用的reduce方法</p>
     * <p> {@link Stream#reduce(BinaryOperator)} 等价于：
     * <pre>{@code
     * boolean foundAny = false;
     * T result = null;
     * for (T element : this stream) {
     *      if (!foundAny) {
     *          foundAny = true;
     *          result = element;
     *      }  else {
     *          result = accumulator.apply(result, element);
     *      }
     * }
     * return foundAny ? Optional.of(result) : Optional.empty();
     * }</pre>
     * </p>
     * <p> {@link Stream#reduce(Object, BinaryOperator)} 等价于：
     * <pre>{@code
     *    T result = identity;
     *    for (T element : this stream) {
     *        result = accumulator.apply(result, element);
     *    }
     *    return result;
     * }</pre>
     * </p>
     * @see BinaryOperator
     * @see BiFunction
     * @see Stream#reduce(BinaryOperator)
     * @see Stream#reduce(Object, BinaryOperator)
     * @see Stream#reduce(Object, BiFunction, BinaryOperator)
     * @see Stream#min(Comparator)
     * @see Stream#max(Comparator)
     * @see Stream#count()
     * @see IntStream#sum()
     * @see DoubleStream#sum()
     * @see LongStream#sum()
     * @see Optional
     * */
    public static void reduce() {
        Stream<String> stream1 = Stream.of("I", "love", "you", "too");
        // 找出最长的单词
        // 只使用累加器
        // Optional可以null的麻烦
        Optional<String> longest = stream1.reduce((s1, s2) -> s1.length()>=s2.length() ? s1 : s2);
        System.out.println(longest.get());
        // 求单词长度之和
        // 使用初始值、累加器和部分和拼接器
        // 部分和拼接器仅当并行执行时才会用到
        Stream<String> stream2 = Stream.of("I", "love", "you", "too");
        Integer lengthSum = stream2.reduce(0,
                (sum, str) -> sum + str.length(),
                (a, b) -> a + b);
        System.out.println(lengthSum);
    }


    /**
     * <p>collect方法</p>
     * <p>规约操作，可以将 {@link Stream} 转化为容器</p>
     * <p> {@link Stream#collect(Collector)} 和
     * {@link Stream#collect(Supplier, BiConsumer, BiConsumer)} 的区别与
     * {@link Stream#reduce(Object, BiFunction, BinaryOperator)} 类似</p>
     * @see Stream#collect(Collector)
     * @see Stream#collect(Supplier, BiConsumer, BiConsumer)
     * @see Collector
     * @see Supplier
     * @see BiConsumer
     * @see Collectors
     * @see #collector()
     * */
    public static void collect() {
        // 将Stream转换成容器或Map
        Stream<String> stream1 = Stream.of("I", "love", "you", "too");
        List<String> list = stream1.collect(Collectors.toList());
        Stream<String> stream2 = Stream.of("I", "love", "you", "too");
        Set<String> set = stream2.collect(Collectors.toSet());
        Stream<String> stream3 = Stream.of("I", "love", "you", "too");
        Map<String, Integer> map = stream3.collect(Collectors.toMap(x -> x, y -> y.length()));
        System.out.println(list);
        System.out.println(set);
        System.out.println(map);
    }

    /**
     * <p>{@link Function#identity()} 方法用于返回
     * 一个 x -> x 的函数接口实现</p>
     * @see Function#identity()
     * */
    public static void x2x() {
        Stream<String> stream = Stream.of("I", "love", "you", "too");
        Map<String, Integer> map = stream.collect(Collectors.toMap(Function.identity(), y -> y.length()));
        System.out.println(map);
    }

    /**
     * <p>方法引用用于代替某些特定形式lambda表达式，
     * 当lambda表达式的全部内容就是调用一个已有的方法，
     * 那么可以用方法引用来替代lambda表达式</p>
     * */
    public static void methodReference() {
        Stream<String> stream = Stream.of("I", "love", "you", "too");
        Map<String, Integer> map = stream.collect(Collectors.toMap(Function.identity(), String::length));
        System.out.println(map);
    }

    /**
     * <p>收集器有关</p>
     * @see Stream#collect(Supplier, BiConsumer, BiConsumer)
     * @see Stream#collect(Collector)
     * @see Collectors
     * @see Collector
     * @see Supplier
     * @see BiConsumer
     * */
    public static void collector() {
        // Stream#collect(Supplier, BiConsumer, BiConsumer)
        // 第一个参数要使用的目标容器
        // 第二个参数指出元素如何添加到目标容器上
        // 第三个参数用于合并多个部分结果，仅当并行时使用
        // Stream#collect(Collector) 简化了参数的传递
        // Collectors则为我们提供了一些封装好了的Collector


        Stream<String> stream1 = Stream.of("I", "love", "you", "too");
        List<String> list = stream1.collect(Collectors.toList());
        Stream<String> stream2 = Stream.of("I", "love", "you", "too");
        List<String> collect = stream2.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        // 上面两个的效果是一样的
        System.out.println(list);
        System.out.println(collect);


        // 有关收集器的复杂的api就不一一在这里展示了
    }

}
