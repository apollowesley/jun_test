package ${parameters.orgName}.${parameters.packageName}.service.impl;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import com.kiss.service.impl.GenericServiceImpl;
import ${parameters.orgName}.${parameters.packageName}.entity.${parameters.entity};
import ${parameters.orgName}.${parameters.packageName}.service.I${parameters.entity}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("${parameters.entity}Service")
public class ${parameters.entity}ServiceImpl extends GenericServiceImpl<${parameters.entity}> implements I${parameters.entity}Service{


}

