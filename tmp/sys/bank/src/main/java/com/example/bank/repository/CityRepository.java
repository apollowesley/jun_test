package com.example.bank.repository;

import com.example.bank.entity.Citys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "t_dict_citys", path = "t_dict_citys")
public interface CityRepository extends JpaRepository<Citys, Long>, PagingAndSortingRepository<Citys, Long> {
}
