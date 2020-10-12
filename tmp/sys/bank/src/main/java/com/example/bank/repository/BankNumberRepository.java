package com.example.bank.repository;



import com.example.bank.entity.BankNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "t_bank_number", path = "t_bank_number")
public interface BankNumberRepository extends JpaRepository<BankNumber, Long>, PagingAndSortingRepository<BankNumber, Long> {
}
