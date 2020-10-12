<#include "java_copyright.include">
package ${basepackage}.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

<#include "java_author.include">
@NoRepositoryBean
public interface BaseRepositoryTools<T, I extends Number> extends JpaRepository<T, I>, JpaSpecificationExecutor<T> {


}