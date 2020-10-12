package com.mkfree.hbase.example.repository.jpa;

import com.mkfree.hbase.example.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
