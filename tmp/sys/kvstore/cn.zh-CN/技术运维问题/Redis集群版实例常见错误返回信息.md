# Redis集群版实例常见错误返回信息 {#concept_qgh_l1w_ydb .concept}

以下列出了在使用阿里云Redis集群版实例过程中经常遇到的一些错误返回信息，帮助用户识别和排查实际操作中遇到的类似问题。

-   DB处于只读状态，主要发生在实例变配、小版本升级等流程中。

```
NOWRITE You can't write against a non-write redis
```

-   实例目前处于锁定状态，一般是用户欠费导致。

```
DISABLE You can't write or read against a disable instance
```

-   Redis请求的value最大不能超过500MB，超过之后返回以下错误信息。

```
redis tempory failure or response big than 500MB
```

-   以下错误信息主要出现在iinfo，riinfo，iscan，imonitor等命令中，用于指定节点的index不在合法范围内。

    ```
    node idx is invalid
    node num specified >= node count
    ```

-   在Redis集群版实例中，事务、脚本等命令要求所有的key必须在同一个slot中，如果不在同一个slot中将返回以下错误信息。

    ```
    command keys must in same slot
    ```

-   eval和evalsha命令必须至少带一个key且numkeys参数大于0。

```
for redis cluster,eval/evalsha number of keys can't be negative or zero
```

-   后端堆积过多未处理完的request，新请求被拒绝。出现的原因是客户端使用了不合理的pipeline。

    ```
    request refused, too many pending request
    ```


