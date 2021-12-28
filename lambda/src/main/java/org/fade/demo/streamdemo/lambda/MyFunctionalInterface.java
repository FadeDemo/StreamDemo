package org.fade.demo.streamdemo.lambda;

/**
 * 自定义函数式接口
 * <p>{@link FunctionalInterface} 是可选的，使用它会使它在编译阶段检查该注解下的
 * 接口是否符合函数式接口规范</p>
 * @author fade
 * @date 2021/12/28
 * @see FunctionalInterface
 */
@FunctionalInterface
public interface MyFunctionalInterface<T> {

    /**
     * accept
     * @param t 参数
     * */
    void accept(T t);

}
