package org.fade.demo.streamdemo.lambda;

import java.util.*;
import java.util.function.*;

/**
 * 集合中的lambda
 *
 * @author fade
 * @date 2021/12/28
 */
public class LambdaInCollection {

    public static void main(String[] args) {
        forEach();
        removeIf();
        replaceAll();
        sort();
        merge();
        compute();
        computeIfAbsent();
        computeIfPresent();
    }

    /**
     * <p>foreach方法</p>
     * <p>对容器的每个元素执行指定的动作</p>
     * @see Iterable#forEach(Consumer)
     * @see Consumer
     * @see BiConsumer
     * */
    public static void forEach() {
        List<String> list = List.of("Hello", "World", "!");
        Set<String> set = Set.of("Hello", "World", "!");
        Map<Integer, String> map = Map.of(1, "Hello", 2, "World", 3, "!");
        list.forEach(x -> System.out.println(x));
        set.forEach(x -> System.out.println(x));
        map.forEach((x,y) -> System.out.println(x + ":" + y));
    }

    /**
     * <p>removeIf方法</p>
     * <p>删除容器中满足条件的元素</p>
     * @see Predicate
     * @see List#removeIf(Predicate)
     * @see Set#removeIf(Predicate)
     * */
    public static void removeIf() {
        List<String> list = new ArrayList<>(List.of("Hello", "World", "!"));
        Set<String> set = new HashSet<>(Set.of("Hello", "World", "!"));
        list.removeIf(x -> "Hello".equals(x));
        set.removeIf(x -> "World".equals(x));
        System.out.println(list);
        System.out.println(set);
    }

    /**
     * <p>replaceAll方法</p>
     * <p>对容器的每个元素执行指定操作，并用操作的结果替换原来的元素</p>
     * @see UnaryOperator
     * @see BiFunction
     * @see List#replaceAll(UnaryOperator)
     * @see Map#replaceAll(BiFunction)
     * */
    public static void replaceAll() {
        List<String> list = new ArrayList<>(List.of("Hello", "World", "!"));
        Map<Integer, String> map = new HashMap<>(16);
        map.put(1, "Hello");
        map.put(2, "World");
        map.put(3, "!");
        list.replaceAll(x -> x.toUpperCase());
        map.replaceAll((x, y) -> y.toUpperCase());
        System.out.println(list);
        System.out.println(map);
    }

    /**
     * <p>sort方法</p>
     * <p>对容器内的元素按指定规则进行排序</p>
     * @see Comparator
     * @see List#sort(Comparator)
     * */
    public static void sort() {
        List<Integer> list = new ArrayList<>(16);
        list.add(4);
        list.add(1);
        list.add(9);
        System.out.println(list);
        list.sort((x, y) -> {
            if (x == null) {
                return -1;
            } else if (y == null) {
                return 1;
            } else {
                return x.compareTo(y);
            }
        });
        System.out.println(list);
    }

    /**
     * <p>merge方法</p>
     * <p>如果给定的key对应的映射不存在或为 {@code null} ，
     * 将给定的非空value关联到key上</p>
     * <p>否则执行指定的操作，如果操作执行的结果为 {@code null} ，移除该映射；
     * 如果执行的结果不为 {@code null} ，则用该结果与给定的key关联</p>
     * @see BiFunction
     * @see Map#merge(Object, Object, BiFunction)
     * */
    public static void merge() {
        Map<Integer, String> map = new HashMap<>(16);
        map.put(1, "Hello");
        map.put(2, "World");
        map.put(3, "!");
        map.put(4, "test");
        // 映射存在，操作的结果不为null，用操作的结果关联key
        map.merge(1, "Hello ", (x, y) -> "Hello ");
        // 映射存在，操作的结果不为null，用操作的结果关联key
        map.merge(3, ", ", (x, y) -> ", ");
        // 映射存在，操作的结果为null，删除映射
        map.merge(4, "", (x, y) -> null);
        // 映射不存在，用给定的value关联key
        map.merge(4, "Hello Fade!", (x, y) -> null);
        System.out.println(map);
    }
    
    /**
     * <p>compute方法</p>
     * <p>和 {@link Map#merge(Object, Object, BiFunction)} 作用差不多，
     * 当操作的结果为 {@code null} 时，删除给定的key的映射；
     * 当操作的结果不为 {@code null} 时，用操作的结果关联给定的key</p>
     * @see BiFunction
     * @see Map#compute(Object, BiFunction) 
     * */
    public static void compute() {
        Map<Integer, String> map = new HashMap<>(16);
        map.put(1, "Hello");
        map.put(2, "World");
        map.put(3, "!");
        map.put(4, "test");
        map.compute(3, (x, y) -> ", ");
        map.compute(4, (x, y) -> null);
        map.compute(4, (x, y) -> "Hello Fade!");
        System.out.println(map);
    }

    /**
     * <p>computeIfAbsent方法</p>
     * <p>当映射不存在或映射的value为null时，用<b>非空的操作结果</b>与给定的key关联</p>
     * @see Function
     * @see Map#computeIfAbsent(Object, Function)
     * @see Map#putIfAbsent(Object, Object) 
     * */
    public static void computeIfAbsent() {
        Map<Integer, String> map = new HashMap<>(16);
        map.put(1, "Hello");
        map.put(2, "World");
        map.put(3, "!");
        map.put(4, "test");
        map.put(5, null);
        map.computeIfAbsent(1, x -> "test");
        map.computeIfAbsent(5, x -> "test");
        map.computeIfAbsent(6, x -> "Hello Fade!");
        map.computeIfAbsent(7, x -> null);
        System.out.println(map);
    }

    /**
     * <p>computeIfPresent方法</p>
     * <p>当映射存在且对应的value不为空时，执行指定的操作，
     * 如果操作结果不为空，用操作结果替换value；如果
     * 操作结果为空，删除给定key对应的映射</p>
     * @see BiFunction
     * @see Map#computeIfPresent(Object, BiFunction)
     * */
    public static void computeIfPresent() {
        Map<Integer, String> map = new HashMap<>(16);
        map.put(1, "Hello");
        map.put(2, null);
        map.put(3, "!");
        map.put(4, "test");
        map.put(5, "World");
        map.computeIfPresent(1, (x, y) -> "test");
        map.computeIfPresent(2, (x, y) -> "Hello");
        map.computeIfPresent(4, (x, y) -> null);
        map.computeIfPresent(6, (x, y) -> "World");
        System.out.println(map);
    }

}
