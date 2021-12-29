# 调试说明

### 环境准备

* IntelliJ IDEA
* JDK corretto-11.0.13

### 操作

1. 编辑当前目录下的 [breakpoint.xml](breakpoint.xml) 里 `component` 元素中所有的 `url` 元素值，把其替换你本地jdk对应的位置
2. 把当前目录下的 [breakpoint.xml](breakpoint.xml) 里的 `component` 元素与项目目录下的 `.idea` 目录里的 `workspace.xml` 中的 `component` 元素进行替换
3. 对 [程序](../src/main/java/org/fade/demo/streamdemo/pipeline/Main.java) 进行debug

### 结论

参考 [Stream Pipelines](https://github.com/CarpenterLee/JavaLambdaInternals/blob/master/6-Stream%20Pipelines.md)

##### 为什么需要流水线

目的是为了尽量减少迭代次数和避免存储中间结果

##### stream流水线要解决的问题

1. 如何记录用户的操作
2. 操作如何叠加
3. 叠加之后的操作如何执行
4. 执行后的结果（如果有）在哪里

##### 1. 如何记录用户的操作

首先明确一个概念，这里的用户操作指的是stream的中间操作：

<table width="600"><tr><td colspan="3" align="center"  border="0">Stream操作分类</td></tr><tr><td rowspan="2"  border="1">中间操作(Intermediate operations)</td><td>无状态(Stateless)</td><td>unordered() filter() map() mapToInt() mapToLong() mapToDouble() flatMap() flatMapToInt() flatMapToLong() flatMapToDouble() peek()</td></tr><tr><td>有状态(Stateful)</td><td>distinct() sorted() sorted() limit() skip() </td></tr><tr><td rowspan="2"  border="1">结束操作(Terminal operations)</td><td>非短路操作</td><td>forEach() forEachOrdered() toArray() reduce() collect() max() min() count()</td></tr><tr><td>短路操作(short-circuiting)</td><td>anyMatch() allMatch() noneMatch() findFirst() findAny()</td></tr></table>

> Stream上的所有操作分为两类：中间操作和结束操作，中间操作只是一种标记，只有结束操作才会触发实际计算。中间操作又可以分为无状态的(*Stateless*)和有状态的(*Stateful*)，无状态中间操作是指元素的处理不受前面元素的影响，而有状态的中间操作必须等到所有元素处理之后才知道最终结果，比如排序是有状态操作，在读取所有元素之前并不能确定排序结果；结束操作又可以分为短路操作和非短路操作，短路操作是指不用处理全部元素就可以返回结果，比如*找到第一个满足条件的元素*。之所以要进行如此精细地划分，是因为底层对每一种情况的处理方式不同。

然后我们先对结果有个总体的概念，