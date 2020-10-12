# Lua脚本支持与限制 {#concept_zf1_3mb_kfb .concept}

云数据库Redis版的各版本实例都支持Lua相关命令。

## Lua命令支持 {#section_kdm_tmb_kfb .section}

Lua脚本的使用进一步提升了Redis的性能。通过内嵌对Lua环境的支持，Redis解决了长久以来不能高效地处理CAS（check-and-set）命令的缺点， 并且可以通过组合使用多个命令， 轻松实现以前很难实现或者不能高效实现的模式。

**说明：** 如果发现无法执行Eval相关命令，比如提示`ERR command eval not support for normal user`，请尝试[升级小版本](cn.zh-CN/用户指南/管理实例/升级小版本.md#)。升级过程中会出现短暂的闪断和实例只读，建议在业务低峰期进行。

## Lua使用限制 {#section_c1h_l5b_kfb .section}

为了保证脚本里面的所有操作都在相同slot进行，云数据库Redis集群版本会对Lua脚本做如下限制：

-   所有key都应该由KEYS数组来传递，redis.call/pcall中调用的redis命令，key的位置必须是KEYS array（不能使用Lua变量替换KEYS），否则直接返回错误信息：

    ```
    -ERR bad lua script for redis cluster, all the keys that the script uses should be passed using the KEYS arrayrn
    ```

-   所有key必须在一个slot上，否则返回错误信息：

    ```
    -ERR eval/evalsha command keys must be in same slotrn
    ```

-   调用必须要带有key，否则直接返回错误信息

    ```
    -ERR for redis cluster, eval/evalsha number of keys can't be negative or zerorn
    ```


**说明：** 如果用户能够在代码中确保所有操作都在相同slot，且希望打破Redis集群的Lua限制，可以在控制台将script\_check\_enable修改为0，则后端不会对脚本进行校验。修改方法请参见[参数设置](cn.zh-CN/用户指南/管理实例/参数设置.md#)。

