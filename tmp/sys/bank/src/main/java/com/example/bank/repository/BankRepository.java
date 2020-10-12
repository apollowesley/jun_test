package com.example.bank.repository;

import com.example.bank.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "t_bank_name", path = "t_bank_name")
public interface BankRepository extends JpaRepository<Bank, Long>, PagingAndSortingRepository<Bank, Long> {
}
