
 
/**
 * 
 * ${remark!}操作相关
 * 
 * @author 
 * @version Bessky V200R001 ${.now?string["yyyy年MM月dd日"]}
 * @since Bessky V200R001C01
 */
@Service  
public class ${entityCamelName}ServiceImpl  implements ${entityCamelName}Service {

	@Autowired
	private ${entityCamelName}Mapper ${entityName}Mapper;

	@Override
    public int insertSelective(${entityCamelName} item)
    {
        return ${entityName}Mapper.insertSelective(item);
    }
    
    @Override
    public int deleteByPrimaryKey(Integer ${primaryProperty})
    {
        return ${entityName}Mapper.deleteByPrimaryKey(${primaryProperty});
    }
        
    @Override
    public int updateByPrimaryKeySelective(${entityCamelName} item)
    {
        return ${entityName}Mapper.updateByPrimaryKeySelective(item);
    }
    
    @Override
    public int updateByPrimaryKey(${entityCamelName} item)
    {
        return ${entityName}Mapper.updateByPrimaryKey(item);
    }
    
	@Override
    public ${entityCamelName} selectByPrimaryKey(Integer ${primaryProperty})
    {
        return ${entityName}Mapper.selectByPrimaryKey(${primaryProperty});
    }
    
	@Override
    public Page<${entityCamelName}> selectPageList(PageData pd)
    {
        return ${entityName}Mapper.selectPageList(pd);
    }
}
