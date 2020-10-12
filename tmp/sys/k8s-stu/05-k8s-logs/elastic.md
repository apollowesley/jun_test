# elastic stack
https://github.com/elastic

https://www.elastic.co/cn/support/eol  //产品生命周期

[es-cluster](https://blog.csdn.net/yonggeit/article/details/102553358)

https://blog.csdn.net/qq_41262248/article/details/85382719
https://blog.csdn.net/bbwangj/article/details/81275237


https://www.jianshu.com/p/7baa06bab46a
https://www.jianshu.com/p/8591cdc27df3
https://www.jianshu.com/p/9bb925f3767e
https://www.jianshu.com/p/80183dad136b
https://blog.csdn.net/boonya/article/details/82500553
https://blog.csdn.net/boonya/article/details/82585032

https://yq.aliyun.com/articles/716967
https://blog.csdn.net/weixin_34345560/article/details/93660163
https://blog.csdn.net/bbwangj/article/details/80600698
https://blog.csdn.net/bbwangj/article/details/89890068
https://blog.csdn.net/bbwangj/article/details/81977947
https://blog.csdn.net/wangshuminjava/article/details/107153139

https://blog.csdn.net/wangshuminjava/article/details/107061867
https://blog.csdn.net/wangshuminjava/article/details/106436440

https://blog.csdn.net/wangshuminjava/article/details/106436413


## 解释
```
一、Search Rate和Search Latency
首先是Search Rate：当索引有10个分片时，它大约是实际请求的10倍。 当索引只有1个分片时，它几乎与实际请求相同。然后Search Latency： 当索引有10个分片时，大约是0.9ms，但端到端延迟大约是30ms。 当单个分片时，它上升到1.88ms，而端到端延迟仅为7ms。 这可能是因为每个分片的数据量因分片的减少而增加，因此每个分片中的搜索时间更长，但是不再需要合并结果。 在这里，我们可以看到合并结果的严重程度如何影响搜索性能

二、Index Rate和Indexing Latency
Index Rate和Indexing Latency也是相同的原因。
Search Rate：对于单个索引，它是每秒查找次数*分片数。 对于多个索引，它是每个索引的搜索速率的总和。
Search Latency：每个分片中的平均延迟。
Indexing Rate：对于单个索引，它是每秒索引的数量*分片数量。 对于多个索引，它是每个索引的索引速率的总和。
Indexing Latency：每个分片中的平均延迟
```