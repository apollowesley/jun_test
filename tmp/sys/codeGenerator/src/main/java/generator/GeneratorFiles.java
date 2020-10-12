package generator;

import utils.FileUtils;
import utils.PropertiesUtils;
import utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-06-01.
 */
public class GeneratorFiles {

    public static void generatorFiles(List<Map<String,Object>> root) throws IOException {
        //mapper文件后缀
        String  daoFileSuffix =PropertiesUtils.getConf("daoFileSuffix");
        daoFileSuffix =StringUtils.isEmpty(daoFileSuffix) ? "Dao" :daoFileSuffix;
        String  packagePrefix =PropertiesUtils.getConf("packagePrefix");
        if(StringUtils.isEmpty(packagePrefix)){
            throw new RuntimeException("packagePrefix is null [包名前缀不能为空]");
        }else{
            for(int i=0;i<root.size();i++){
                root.get(i).put("pakageName", packagePrefix);
                root.get(i).put("daoFileSuffix",daoFileSuffix);
            }
        }
        File directory = new File("");//设定为当前文件夹
        File dir = new File(directory.getCanonicalPath()+"/generator-out");
        FileUtils.delDir(dir);
        System.out.println("生成路径为->"+directory.getCanonicalPath()+"/generator-out");
        dir.mkdir();
        String DirPath = dir.getAbsolutePath();
        File domain = new File(DirPath + "/domain");
        if (!domain.exists()) {
            domain.mkdir();
        }
        File domainCommon = new File(DirPath + "/domain/common");
        if (!domainCommon.exists()) {
            domainCommon.mkdir();
        }
        File service = new File(DirPath + "/service");
        if (!service.exists()) {
            service.mkdir();
        }
        File serviceImpl = new File(service + "/impl");
        if (!serviceImpl.exists()) {
            serviceImpl.mkdir();
        }

        File dao = new File(DirPath + "/dao");
        if (!dao.exists()) {
            dao.mkdir();
        }
        File DaoCommon = new File(DirPath + "/dao/common");
        if (!DaoCommon.exists()) {
            DaoCommon.mkdir();
        }
        File daoMapper = new File(DirPath + "/dao/mapper");
        if (!daoMapper.exists()) {
            daoMapper.mkdir();
        }

        for (int i = 0; i < root.size(); i++) {
            String tableName = (String) root.get(i).get("className");
            String modelName =StringUtils.StringTraslation(tableName);
            root.get(i).put("className",modelName);
            root.get(i).put("tableName",tableName);
            /*******生成各层文件*****/
            System.out.println("正在生成文件：---------表" + tableName+"--->"+modelName);
            GenaratorFile.generatorSigleFile("dao/dao.ftl", root.get(i), dao.getAbsolutePath(),   modelName + daoFileSuffix+".java");
            GenaratorFile.generatorSigleFile("dao/mapper/mapper.ftl", root.get(i), daoMapper.getAbsolutePath(), modelName+ daoFileSuffix+".xml");
            GenaratorFile.generatorSigleFile("common/BaseDAO.ftl", root.get(i), DaoCommon.getAbsolutePath(), "BaseDAO.java");

            GenaratorFile.generatorSigleFile("domain/domain.ftl", root.get(i), domain.getAbsolutePath(), modelName + ".java");
            GenaratorFile.generatorSigleFile("common/BaseDomain.ftl", root.get(i), domainCommon.getAbsolutePath(), "BaseDomain.java");

            GenaratorFile.generatorSigleFile("service/service.ftl", root.get(i), service.getAbsolutePath(), "I" + modelName + "Service.java");
            GenaratorFile.generatorSigleFile("service/impl/serviceImpl.ftl", root.get(i), serviceImpl.getAbsolutePath(), "" + modelName + "ServiceImpl.java");
        }
    }

}
