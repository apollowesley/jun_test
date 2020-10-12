
https://www.elastic.co/guide/en/elasticsearch/reference/7.1/docs.html


## cat
/_cat/indices?v


## index
GET /xxx/_count
GET /xxx/_search

PUT /xxx/_reflush


GET my-index/_search
{
  "query": { "term": { "field": { "value": "xyz" } } },
  "size": 0,
  "track_total_hits": true
}


https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-aliases.html
https://www.elastic.co/guide/cn/elasticsearch/guide/current/index-aliases.html