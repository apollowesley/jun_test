
[返回主页](/README.md)

**示例1: 使用numpy生成矩阵**

```
#!/usr/bin/python
# -*- coding: utf-8 -*-

import numpy as np

if __name__ == '__main__':
    # numpy是非常好用的数据包，如：可以这样得到这个二维数组
    # [[ 0  1  2  3  4  5]
    #  [10 11 12 13 14 15]
    #  [20 21 22 23 24 25]
    #  [30 31 32 33 34 35]
    #  [40 41 42 43 44 45]
    #  [50 51 52 53 54 55]]
    # arange(start,stop,step) 生成[start,stop)数组，注意是作闭右开区间
    # reshape(m,n)方法用于将数组转换成[m x n]的矩阵
    a = np.arange(0, 60, 10).reshape((-1, 1)) + np.arange(6)
    print a
```

**示例2: 使用numpy生成多为数组(nd-array)**
```
#!/usr/bin/python
# -*- coding: utf-8 -*-

import numpy as np

if __name__ == '__main__':
    # 标准Python的列表(list)中，元素本质是对象。
    # 如：L = [1, 2, 3]，需要3个指针和三个整数对象，对于数值运算比较浪费内存和CPU。
    # 因此，Numpy提供了ndarray(N-dimensional array object)对象：存储单一数据类型的多维数组。

    # # 1.使用array创建
    # 通过array函数传递list对象
    L = [1, 2, 3, 4, 5, 6]
    print "L = ", L
    a = np.array(L)
    print "a = ", a
    print type(a), type(L)
    # 若传递的是多层嵌套的list，将创建多维数组
    b = np.array([[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12]])
    print b
```


**示例3: nd-array的shape的用法**
```
#!/usr/bin/python
# -*- coding: utf-8 -*-

import numpy as np

if __name__ == '__main__':
    a = np.array([1,2,3])
    b = np.array([[1,2,3],[4,5,6],[8,9,0]])

    ## 数组大小可以通过其shape属性获得
    print a.shape   #(3,)
    print b.shape   #(3,3)

    ## shape返回的类型是元祖tuple
    print type(a.shape) # <type 'tuple'>
    print type(b.shape) # <type 'tuple'>
    
    ## 可以强制修改shape,将原来的2x3的矩阵转换成3x2的
    ## [[1,2,3],      ==>  [[1,2],
    ##  [4,5,6]]            [3,4],
    ##                      [5,6]]
    b.shape = 3, 2
    print b
```

**示例4: nd-array的reshape的用法,注意不改变原数组的内容，但是他们引用的对象是一样的，类似于浅拷贝**
```
#!/usr/bin/python
# -*- coding: utf-8 -*-

import numpy as np

if __name__ == '__main__':
    a = np.array([1, 2, 3, 4])
    b = np.array([[1, 2, 3], [4, 5, 6]])

    # # # 使用reshape方法，可以创建改变了尺寸的新数组，原数组的shape保持不变
    c = b.reshape((3, 2))
    print "b = \n", b
    print 'c = \n', c
    
    # # # 数组b和c共享内存，修改任意一个将影响另外一个
    b[0][1] = 20
    print "b = \n", b
    print "c = \n", c
```

**示例5: dtype的用法**
```
#!/usr/bin/python
# -*- coding: utf-8 -*-

import numpy as np

if __name__ == '__main__':
    a = np.array([1, 2, 3, 4])
    b = np.array([[1, 2, 3], [4, 5, 6]])

    # # # 数组的元素类型可以通过dtype属性获得
    print a.dtype  # int64
    print b.dtype  # int64
    # # #
    # # # # 可以通过dtype参数在创建时指定元素类型
    d = np.array([[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12]], dtype=np.float)
    f = np.array([[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12]], dtype=np.complex)
    print d    # [[  1.   2.   3.   4.]
               #  [  5.   6.   7.   8.]
               #  [  9.  10.  11.  12.]]
    
    print f    # [[  1.+0.j   2.+0.j   3.+0.j   4.+0.j]
               #  [  5.+0.j   6.+0.j   7.+0.j   8.+0.j]
               #  [  9.+0.j  10.+0.j  11.+0.j  12.+0.j]]
```


**示例6: astype的用法,数组的类型转换**
```
#!/usr/bin/python
# -*- coding: utf-8 -*-

import numpy as np

if __name__ == '__main__':
    # # # # 可以通过dtype参数在创建时指定元素类型
    d = np.array([[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12]], dtype=np.float)
    f = np.array([[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12]], dtype=np.complex)

    # # 如果更改元素类型，可以使用astype安全的转换
    f = d.astype(np.int)
    print f
    
    # # 但不要强制仅修改元素类型，如下面这句，将会以int来解释单精度float类型
    d.dtype = np.int
    print d
```