### 内置函数接口

- Predicate是一个布尔类型的函数，该函数只有一个输入参数。Predicate接口包含了多种默认方法，用于处理复杂的逻辑动词（and, or，negate）。

- Function接口接收一个参数，并返回单一的结果。默认方法可以将多个函数串在一起（compse, andThen,identity）

- Supplier接口产生一个给定类型的结果。与Function不同的是，Supplier没有输入参数。

- Consumer代表了在一个输入参数上需要进行的操作

- Comparator接口在早期的Java版本中非常著名。Java 8 为这个接口添加了不同的默认方法。

- Optional不是一个函数式接口，而是一个精巧的工具接口，用来防止NullPointerEception产生