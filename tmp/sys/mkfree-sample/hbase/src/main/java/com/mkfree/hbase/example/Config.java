package com.mkfree.hbase.example;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;

@Configuration
public class Config {

    @Bean
    public SolrClient solrClient() {
        return new HttpSolrClient.Builder("http://hb-proxy-pub-wz95d8474e452itz6-master3-001.hbase.rds.aliyuncs.com:8983/solr").build();
//        return new CloudSolrClient.Builder().withZkHost("hb-proxy-pub-wz95d8474e452itz6-master3-001.hbase.rds.aliyuncs.com:2181/solr").build();
    }
    @Bean
    public SolrTemplate solrTemplate(SolrClient solrClient) throws Exception {
        return new SolrTemplate(solrClient);
    }

}
