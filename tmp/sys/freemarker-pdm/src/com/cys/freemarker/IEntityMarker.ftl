package entity.${package};

import sun.util.calendar.LocalGregorianCalendar;
import java.util.Date;


/**
 *  ${entityComment}实体类接口
 */
 
public interface I${entityName} {

	<#list dataList as var>
	
		/**
			${var.comment}
		*/
		${var.dataType} get${var.newGetSetCode}();
		void set${var.newGetSetCode}(${var.dataType} ${var.newColumn});
		
	</#list>


}
 






