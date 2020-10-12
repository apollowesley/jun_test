## reindex
#check index info
```
get /source_fix
get /source_fix/_mapping

get /source/_count
get /source_fix/_count
```

#delete index data
```
#one method
POST /source_fix/_delete_by_query
  "query": {"match_all": {}}
}

#two method
delete /source_fix
put /source_fix   //new index
{
    "mapping": {}
}
```

#reindex 
```
POST /_reindex?slices=auto&wait_for_completion=false
{
  "source": {
    "index": "source",
    "size": 10000
  },
  "dest": {
    "index": "source_fix"
  }
}

get /_tasks/xxx  //index process
```

#delete index and alias index
```
delete /source
put /source_fix/_alias/source

get /source
get /source/_count
```

#create index and mapping field
```
put /source_fix
{
    "mappings" : {
      "properties" : {
        "@timestamp" : {
          "type" : "date"
        },
        "@version" : {
          "type" : "text",
          "fields" : {
            "keyword" : {
              "type" : "keyword",
              "ignore_above" : 256
            }
          }
        },
        "accountId" : {
          "type" : "keyword"
        },
        "configId" : {
          "type" : "integer"
        },
        "environment" : {
          "type" : "keyword"
        },
        "evtName" : {
          "type" : "keyword"
        },
        "isInvited" : {
          "type" : "integer"
        },
        "lv" : {
          "type" : "integer"
        },
        "monthCard" : {
          "type" : "integer"
        },
        "mothCard" : {
          "type" : "long"
        },
        "num" : {
          "type" : "integer"
        },
        "onlineDays" : {
          "type" : "integer"
        },
        "onlineDuration" : {
          "type" : "long"
        },
        "operation" : {
          "type" : "integer"
        },
        "platform" : {
          "type" : "integer"
        },
        "region" : {
          "type" : "keyword"
        },
        "registerTime" : {
          "type" : "date"
        },
        "source" : {
          "type" : "integer"
        },
        "time" : {
          "type" : "date"
        },
        "type" : {
          "type" : "integer"
        },
        "vip" : {
          "type" : "integer"
        }
      }
    }
}
```
