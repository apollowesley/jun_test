### Stream Api

- Filter接受一个predicate接口类型的变量，并将所有流对象中的元素进行过滤

- Sorted是一个中间操作，能够返回一个排过序的流对象的视图。流对象中的元素会默认按照自然顺序进行排序

- Map能够把流对象中的每一个元素对应到另外一个对象上

- Match判断集合元素是否匹配predicate条件

- Count返回包含的元素数量。

- Reduce对元素进行削减操作，该操作的结果会放在一个Optional变量里返回