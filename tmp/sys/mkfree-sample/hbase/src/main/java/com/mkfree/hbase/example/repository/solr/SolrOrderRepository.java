package com.mkfree.hbase.example.repository.solr;

import com.mkfree.hbase.example.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

public interface SolrOrderRepository extends SolrCrudRepository<SolrOrder, String> {

    List<SolrOrder> findByCustomerMobile(String mobile);
//
//    @Query(value = "id:*?0* OR name:*?0*")
//    Page<Order> findByCustomQuery(String searchTerm, Pageable pageable);
//

}