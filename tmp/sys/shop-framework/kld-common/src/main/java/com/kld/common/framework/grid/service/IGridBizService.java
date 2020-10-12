 package com.kld.common.framework.grid.service;

 import com.kld.common.framework.dto.GridQueryPara;

 import java.util.List;

 import javax.servlet.http.HttpServletRequest;



 public interface IGridBizService<T> {

 public List<T> getGridData(GridQueryPara queryParam,HttpServletRequest request);
 }
