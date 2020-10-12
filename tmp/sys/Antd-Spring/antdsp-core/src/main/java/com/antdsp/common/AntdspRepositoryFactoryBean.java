package com.antdsp.common;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import com.antdsp.dao.jpa.AntdspBaseRepositoryImpl;

public class AntdspRepositoryFactoryBean<R extends JpaRepository<T, ID> , T , ID extends Serializable> extends JpaRepositoryFactoryBean<R, T, ID> {

	public AntdspRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
	}

	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
//		return super.createRepositoryFactory(entityManager);
		return new BaseRepositoryFactory<>(entityManager);
		
	}
	
	private static class BaseRepositoryFactory<T , ID extends Serializable> extends JpaRepositoryFactory{
		private final EntityManager em;
		
		public BaseRepositoryFactory(EntityManager em) {
			super(em);
			this.em = em;
		}

		@Override
		protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information,
				EntityManager entityManager) {
			
			return new AntdspBaseRepositoryImpl<>((Class<T>) information.getDomainType(), entityManager);
		}

		@Override
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return AntdspBaseRepositoryImpl.class;
		}
		
	}
	

}
