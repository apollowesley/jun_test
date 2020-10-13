
/**
 * 
 * ${remark!}操作相关
 * 
 * @author 
 * @version Bessky V200R001 ${.now?string["yyyy年MM月dd日"]}
 * @since Bessky V200R001C01
 */ 
public interface ${entityCamelName}Service
{

	int insertSelective(${entityCamelName} item);
	
	int deleteByPrimaryKey(Integer ${primaryProperty});
	
	int updateByPrimaryKeySelective(${entityCamelName} item);
	
	int updateByPrimaryKey(${entityCamelName} item);
	
	${entityCamelName} selectByPrimaryKey(Integer ${primaryProperty});
	
	Page<${entityCamelName}> selectPageList(PageData pd);

}
