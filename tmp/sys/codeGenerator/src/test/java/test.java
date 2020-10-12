
import db.MySQLTableComment;
import db.model.TableFiledModel;
import generator.GeneratorFiles;
import model.FiledModel;
import utils.FileUtils;
import generator.GenaratorFile;
import utils.PropertiesUtils;


import java.io.*;

import java.util.*;

/**
 * Created by Administrator on 2017-05-29.
 */
public class test {

    public static void main(String[] args) throws Exception {
      //初始化配置文件
        PropertiesUtils.PropertiesUtilsInstance();
        PropertiesUtils.getPropertiesReader();
       // System.out.print(PropertiesUtils.getConf("daoFileSuffix"));
        List<Map<String,Object>> list= new ArrayList();
        List tableNames = MySQLTableComment.getAllTableName();
       Map<String, List<TableFiledModel>> tables= MySQLTableComment.getColumnCommentByTableName(tableNames);
       for(String table:tables.keySet()){
           String tableName =table;
           List<TableFiledModel> tableFiledModels =tables.get(table);
           Map<String, Object> root = new HashMap<String, Object>();
           root.put("className", tableName);////
           List<FiledModel> attrs = new ArrayList<FiledModel>();
           for ( TableFiledModel tableFiledModel:tableFiledModels){
               attrs.add(new FiledModel(tableFiledModel.getFiledComment(), tableFiledModel.getFiledName(), tableFiledModel.getFiledType(),tableFiledModel.getImportClass()));
               root.put("importClass", tableFiledModel.getImportClass());
           }
           root.put("attrs", attrs);
           list.add(root);

       }
        GeneratorFiles.generatorFiles(list);


    }
}

