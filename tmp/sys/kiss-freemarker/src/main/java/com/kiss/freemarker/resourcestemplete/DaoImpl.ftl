package ${parameters.orgName}.${parameters.packageName}.dao.impl;
import com.kiss.dao.impl.DefaultGenricDaoImp;
import ${parameters.orgName}.${parameters.packageName}.entity.${parameters.entity};
import ${parameters.orgName}.${parameters.packageName}.dao.I${parameters.entity}Dao;
import org.springframework.stereotype.Repository;

@Repository("${parameters.entity}Dao")
public class ${parameters.entity}DaoImpl extends DefaultGenricDaoImp<${parameters.entity}> implements I${parameters.entity}Dao {







}
