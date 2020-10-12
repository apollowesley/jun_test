package com.antdsp.web.rest.operation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antdsp.common.AntdspResponse;
import com.antdsp.common.pagination.PaginationData;
import com.antdsp.dao.jpa.ArticleJpa;
import com.antdsp.data.entity.Article;
import com.antdsp.data.entity.User;
import com.antdsp.utils.ShiroUtils;

@RestController
@RequestMapping("/operation/article")
public class ArticleApi {
	
	@Autowired
	private ArticleJpa articleJpa;

	@GetMapping
	public PaginationData<Article> list(int page, int count, String title){
		
		
		Specification<Article> specification = new Specification<Article>() {

			@Override
			public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if(!StringUtils.isEmpty(title)) {
					List<Predicate> predicates = new ArrayList<>();
					predicates.add(criteriaBuilder.like(root.get("title"), "%"+title+"%"));
					return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
				return null;
			}
		};
		return this.articleJpa.list(specification, page, count);
	}
	
	@GetMapping("/{id:\\d+}")
	public Article detail(@PathVariable("id") Long id) {
		
		Article article = this.articleJpa.findById(id).orElse(new Article());
		return article;
	}
	
	@PostMapping("")
	@Transactional
	public AntdspResponse add(@RequestBody Article article) {
		User current = ShiroUtils.currentUser();
		
		article.setCreator(current.getLoginname());
		article.setModifier(current.getLoginname());
		article.onPreInsert();
		articleJpa.save(article);
		return AntdspResponse.success();
	}
	
	@PutMapping("/{id:\\d+}")
	@Transactional
	public AntdspResponse update(@RequestBody Article article) {
		Article oldArticle = articleJpa.findById(article.getId()).orElse(null);
		if(oldArticle == null) {
			return AntdspResponse.error("为找到对应信息");
		}
		User current = ShiroUtils.currentUser();
		oldArticle.setContent(article.getContent());
		oldArticle.setTitle(article.getTitle());
		oldArticle.setSubTitle(article.getSubTitle());
		oldArticle.setModifier(current.getLoginname());
		oldArticle.setArticleFrom(article.getArticleFrom());
		oldArticle.onPreUpdate();
		
		articleJpa.save(oldArticle);
		return AntdspResponse.success();
		
	}
	
	@DeleteMapping("/{id:\\d+}")
	@Transactional
	public AntdspResponse del(@PathVariable("id") Long id) {
		
		Article oldArticle = articleJpa.findById(id).orElse(null);
		if(oldArticle == null) {
			return AntdspResponse.error("为找到对应信息");
		}
		
		this.articleJpa.delete(oldArticle);
		return AntdspResponse.success();
	}
}
