package ${package}.entity;

import java.util.Date;
import entity.${package}.I${entityName};

/**
 *  ${entityComment}实体类
 */
 
public class ${entityName} implements I${entityName} {

	private static final long serialVersionUID = 1L;
	
	<#list dataList as var>
	
		/*
			${var.comment}
		*/
		private ${var.dataType} ${var.newColumn};		
		
	</#list>
	
	
	<#list dataList as var>
		public ${var.dataType} get${var.newGetSetCode}(){
			return ${var.newColumn};
		}
		
		public void set${var.newGetSetCode}(${var.dataType} ${var.newColumn}){
			this.${var.newColumn} = ${var.newColumn};
		}
		
	</#list>


	@Override
	public String toString() {
		return "${entityName}{" +
		<#list dataList as var>		
				" ${var.newColumn} = " + ${var.newColumn} +
		</#list>		
				'}';
	}

}
 






