

/**
 * 
 * ${remark!}操作相关
 * 
 * @author 
 * @version Bessky V200R001 ${.now?string["yyyy年MM月dd"]}
 * @since Bessky V200R001C01
 */
public interface ${entityCamelName}Mapper
{
	int insertSelective(${entityCamelName} item);
	
 	int deleteByPrimaryKey(Integer ${primaryProperty});

    int updateByPrimaryKeySelective(${entityCamelName} item);
    
    int updateByPrimaryKey(${entityCamelName} item);

    ${entityCamelName} selectByPrimaryKey(Integer ${primaryProperty});
    
    Page<${entityCamelName}> selectPageList(PageData pd);
}
