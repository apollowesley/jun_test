package org.gen.code.parse;

import org.gen.code.config.Config;

import java.io.*;
import java.util.*;

/**
 * Created by Administrator on 2015/8/12.
 */
public class FileParse extends AbstractParser {

    public FileParse(Config config, Set<Class<?>> klasses, Map<String, Class<?>> querys) {
        super(config, klasses, querys);
    }

    private String project = Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("/target/classes/", "");
    private String javaSrc = "/src/main/java";
    private String resources = "/src/main/resources";
    String daoPath = (this.project + this.javaSrc + File.separator + (this.config.getDaoPackage().replace(".", File.separator))).replace("/", File.separator).replace("\\", File.separator);
    String implPath = (this.project + this.javaSrc + File.separator + (this.config.getDaoImplPackage().replace(".", File.separator))).replace("/", File.separator).replace("\\", File.separator);
    String codecPath = (this.project + this.javaSrc + File.separator + (this.config.getCodecPackage().replace(".", File.separator))).replace("/", File.separator).replace("\\", File.separator);

    {
        final File file = new File(daoPath);
        if (!file.exists()) file.mkdirs();
        File file1 = new File(implPath);
        if (!file1.exists()) file1.mkdirs();
        File file2 = new File(codecPath);
        if (!file2.exists()) file2.mkdirs();
    }

    public void saveDao(String daoName, String dao) {
        String finalPath = daoPath + File.separator + daoName;
        save0(daoName, dao, finalPath);
    }

    public void saveDaoImpl(String daoImplName, String impl) {
        String finalPath = implPath + File.separator + daoImplName;
        save1(daoImplName, impl, finalPath);
    }

    public void saveCodec(String codecName, String impl) {
        String finalPath = codecPath + File.separator + codecName;
        save1(codecName, impl, finalPath);
    }

    public void save1(String name, String string, String finalPath) {
        File file = new File(finalPath);
        StringBuilder b = new StringBuilder();

        System.out.println(string);
        if (file.isFile()) {
            if (name.endsWith("Codec.java")) {
                file.delete();
            } else {
                String fileToString = this.getFileToString(file);
                if (fileToString != null && fileToString.length() > 0) {
                    int i = fileToString.indexOf("{");
                    int i1 = fileToString.lastIndexOf("}");
                    List<String> methods = new ArrayList<>();
                    boolean start = false;
                    String d = new String();
                    for (Object s : new BufferedReader(new StringReader(fileToString.substring(i-1, i1))).lines().toArray()) {
                        String str = (String) s;
                        if ("}".equals(str.trim())) {
                            methods.add(d + "}");
                            d = new String();
                            start = false;
                        }
                        if(str.contains("{")&&!start){
                            start = true;
                        }
                        if (start) {
                            d += str + "\r\n\t";
                        }
                    }
                    methods.remove("}");
                    System.out.println(methods);
                   methods.forEach(m -> {
                       if (!(m.contains("insert(")
                               || m.contains("update(")
                               || m.contains("findOne(")
                               || m.contains("findAll(")
                               || m.contains("merge(")
                               || m.contains("count(")
                               || m.contains("delete(")
                               || m.contains("paserQueryModel(")
                       )) {
                           if (m != null && m.length() > 0 && !m.equals("}")) {
                               if (!";".equals(m.trim()) && !"\\t".equals(m.trim())) {
                                   b.append("\r\n" + m );
                               }
                           }
                       }
                   });
                }
                file.delete();
            }

        }
        createFile(new File(finalPath));
        System.out.println(string);
        String ss = b.toString();
        if (ss != null && !"".equals(ss)) {
            String substring = string.substring(0, string.lastIndexOf("}")-1);
            string = substring+ ss+"\r\n\t}";
        }
        System.out.println(string);
        try (FileOutputStream os = new FileOutputStream(file)) {
            System.out.println("写入" + name);
            os.write(string.getBytes());
        } catch (IOException e) {
            System.out.println("解析出错");
            throw new RuntimeException(e);
        }
    }

    private void save0(String daoName, String dao, String finalPath) {
        File file = new File(finalPath);
        StringBuilder b = new StringBuilder();
        String imp = null;
        if (file.isFile()) {
            String fileToString = this.getFileToString(file);
            if (fileToString != null && file.length() > 0) {
                String[] split2 = fileToString.split("\\{");
                imp = split2[0];
                String[] split = split2[1].split("\\}")[0].split(";");
                Arrays.asList(split).forEach((m) -> {
                    if (!(m.contains("insert(")
                            || m.contains("update(")
                            || m.contains("findOne(")
                            || m.contains("findAll(")
                            || m.contains("merge(")
                            || m.contains("count(")
                            || m.contains("delete(")
                    )) {
                        if (m.trim() != null && m.trim().length() > 0) {
                            if (!";".equals(m.trim()) && !"\\t".equals(m.trim())) {
                                b.append("\r\n\t" + (m + "\r\n").trim());
                            }
                        }
                    }
                });
            }
            file.delete();
        }
        createFile(new File(finalPath));
        String string = b.toString();
        if (string != null && !"".equals(string)) {
            dao = dao.split("\\}")[0] + b.toString() + "\r\n }";
        }
        if (imp != null)
            dao = imp + "{" + dao.split("\\{")[1];
        try (FileOutputStream os = new FileOutputStream(file)) {
            System.out.println("写入" + daoName);
            os.write(dao.getBytes());
        } catch (IOException e) {
            System.out.println("解析出错");
            throw new RuntimeException(e);
        }
    }


    private String getFileToString(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            StringBuilder lines = new StringBuilder();
            bufferedReader.lines().forEach(line -> lines.append(line + "\r\n"));
            return lines.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
