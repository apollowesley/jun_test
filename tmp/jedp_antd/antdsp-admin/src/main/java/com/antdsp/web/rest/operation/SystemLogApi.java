package com.antdsp.web.rest.operation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antdsp.common.annotation.OperationLog;
import com.antdsp.common.exception.AntdspErrorException;
import com.antdsp.common.pagination.PaginationData;
import com.antdsp.dao.jpa.system.SystemLogJpa;
import com.antdsp.data.entity.system.SystemLog;
import com.antdsp.utils.excelutil.Column;
import com.antdsp.utils.excelutil.ColumnRender;
import com.antdsp.utils.excelutil.ExcelUtil;

@RestController
@RequestMapping("/operation/log")
public class SystemLogApi {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemLogApi.class);
	
	@Autowired
	private SystemLogJpa systemLogJpa;
	
	@GetMapping
	public PaginationData<SystemLog> list(int page, int count, Long startTime, Long endTime){
		
		Specification<SystemLog> specification = new Specification<SystemLog>() {

			@Override
			public Predicate toPredicate(Root<SystemLog> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				
				List<Predicate> predicates = new ArrayList<>();  
				if(startTime != null) {

					if(endTime != null) {
						predicates.add(criteriaBuilder.between(root.get("created") , new Date( startTime) , new Date (endTime)));
					}else {
						predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("created"), new Date(startTime)));
					}
				}else if(endTime != null){
					predicates.add(criteriaBuilder.lessThan(root.get("lastModified"),new Date(endTime)));
				}
				
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			
		};
		
		return this.systemLogJpa.list(specification, page, count);
	}
	
	@GetMapping("/export")
	public void export(HttpServletResponse response, Long startTime, Long endTime) {
		
		Specification<SystemLog> specification = new Specification<SystemLog>() {

			@Override
			public Predicate toPredicate(Root<SystemLog> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();  
				if(startTime != null) {

					if(endTime != null) {
						predicates.add(criteriaBuilder.between(root.get("created") , new Date( startTime) , new Date (endTime)));
					}else {
						predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("created"), new Date(startTime)));
					}
				}else if(endTime != null){
					predicates.add(criteriaBuilder.lessThan(root.get("lastModified"),new Date(endTime)));
				}
				
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			
		};
		
		List<SystemLog> data = this.systemLogJpa.findAll(specification);
		List<Column> columns = new ArrayList<>();
		
		columns.add(new Column("用户名", "optorName", null));
		columns.add(new Column("用户操作", "optName", null));
		columns.add(new Column("请求URI", "optURI", null));
		columns.add(new Column("请求方式", "optType", null));
		columns.add(new Column("请求参数", "optDetail", null));
		columns.add(new Column("耗时(毫秒)", "runTime",  new ColumnRender() {
			@Override
			public String render(String value) {
				return value + "ms";
			}
			
		}));
		columns.add(new Column("操作IP", "optorIp", null));
		columns.add(new Column("创建时间", "created", null));
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename=systemlog.xlsx");
		
		try {
			ExcelUtil.dataToResponse(columns, data, response.getOutputStream());
		}catch(Exception e) {
			logger.error("系统日志下载失败", e);
			throw new AntdspErrorException("系统日志下载失败");
		}
	}

}
