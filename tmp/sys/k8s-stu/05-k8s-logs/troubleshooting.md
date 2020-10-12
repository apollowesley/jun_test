
## response code: 429
```
[2020-06-29T06:43:29,092][INFO ][logstash.outputs.elasticsearch] retrying failed action with response code: 429 ({"type"=>"es_rejected_execution_exception", "reason"=>"rejected execution of processing of [27244][indices:data/write/bulk[s][p]]: request: BulkShardRequest [[battle_button_click][4]] containing [34] requests, target allocation id: EEQebttSTiqKX9C0vXCPow, primary term: 1 on EsThreadPoolExecutor[name = ca3bffaea82ad29f996babdf68c41520/write, queue capacity = 200, org.elasticsearch.common.util.concurrent.EsThreadPoolExecutor@13cb1fbf[Running, pool size = 2, active threads = 2, queued tasks = 207, completed tasks = 1357]]"})
```
#问题解决
[thread_pools](https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-threadpool.html)
```
放大es的threadpool只能缓解，最终的解决都是用消息缓冲kafka等
```