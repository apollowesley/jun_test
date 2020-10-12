package com.example.bank.repository;



import com.example.bank.entity.Provinces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "t_dict_provinces", path = "t_dict_provinces")
public interface ProvinceRepository extends JpaRepository<Provinces, Long>, PagingAndSortingRepository<Provinces, Long> {
}
