package com.dtdream.rdic.saas.utils;

/**
 * Created by Administrator on 2015/6/24.
 */

import com.dtdream.rdic.saas.app.Results;
import com.dtdream.rdic.saas.def.CommonException;
import com.dtdream.rdic.saas.def.Constant;
import com.dtdream.rdic.saas.def.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

import static java.net.URLDecoder.decode;

/**
 * Spring中实现文件上传下载
 * step1：
 *   由于SpringMVC使用的是commons-fileupload实现，依赖以下jar包
 *    - commons-fileupload:commons-fileupload:x.x.x
 *    - commons-io:commons-io:x.x.x
 *   或直接配置Maven依赖spring-web:
 *   <dependency>
 *     <groupId>org.springframework</groupId>
 *     <artifactId>spring-web</artifactId>
 *     <version>4.1.6.RELEASE</version>
 *   </dependency>
 * step2：
 *   在spring-mvc中配置MultipartResolver处理器
 *   <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
 *     <!-- 设置上传文件的最大尺寸为10MB -->
 *     <property name="maxUploadSize">
 *       <value>10000000</value>
 *     </property>
 *   </bean>
 * step3：
 *   在Controller的方法中添加MultipartFile参数。该参数用于接收表单中file组件的内容
 *   编写前台表单。注意enctype="multipart/form-data"以及<input type="file" name="****"/>
 *   如果是单个文件 直接使用MultipartFile 即可
 * step4:
 *   Spring配置文件上传路径
 *   <bean id="fileUtils" class="com.dtdream.rdic.saas.utils.FileUtils">
 *     <property name="rootdir">
 *       <value>/www/upload</value>
 *     </property>
 *   </bean>
 *
 * eg:
 @RequestMapping("/upload.do")
     public ModelAndView upload (HttpServletRequest request) throws Exception {
     List<Map.Entry<String, String>> lstFile;
     lstFile = upload (request, "D:\\upload");
     request.getSession().setAttribute("sss", lstFile);
     //获取modelandview对象
     ModelAndView view = new ModelAndView();
     view.setViewName("listfile");
     return view;
 }

 @RequestMapping(value = "download.do")
     public ModelAndView download (HttpServletRequest request, HttpServletResponse response) throws Exception {
     String strLoadpath = "D:\\upload\\20150626\\c27a1de7-0c73-4c76-b10e-749814f4975f\\industry.PNG";
     download(request, response, strLoadpath);
     return null;
 }

 @RequestMapping(value = "remove.do")
     public ModelAndView upRemove(HttpServletRequest request, HttpServletResponse response) throws Exception {
     String strLoadpath = "D:\\upload\\20150626\\13fd22f5-317e-4757-97a0-ae44bbee5ca1\\捕获.PNG";
     remove (request, strLoadpath);
     return null;
 }
 */
public class FileUtils {
    protected final static Logger logger = LoggerFactory.getLogger(FileUtils.class.getName());
    private String approot = Constant.EMPTY_STRING;
    private String rootdir;

    public static List<String> vo;
    public static List<String> vr;

    public enum PATHPFX {
        CLASSPATH(0, "classpath:"),
        APPROOT(1, "approot:"),
        ;
        private int i;
        private String prot;
        public static PATHPFX[] values = PATHPFX.values();
        PATHPFX(int i, String prot) {
            this.i = i;
            this.prot = prot;
        }
        public int index () {
            return this.i;
        }
        public String protocol () {
            return this.prot;
        }
    }

    static {
        try {
            String classpath = decode(FileUtils.class.getClassLoader().getResource("").getPath(), "UTF-8");
            vo = new ArrayList<>();
            vr = new ArrayList<>();
            vo.add(PATHPFX.CLASSPATH.index(), PATHPFX.CLASSPATH.protocol());
            vo.add(PATHPFX.APPROOT.index(), PATHPFX.APPROOT.protocol());

            vr.add(PATHPFX.CLASSPATH.index(), classpath);
            vr.add(PATHPFX.APPROOT.index(), Constant.EMPTY_STRING);
        } catch (UnsupportedEncodingException e) {
            logger.error("解码路径失败：", e);
        }
    }


    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    public List<Map.Entry<String, String>> upload (HttpServletRequest request) throws CommonException {
        String relative = mkdirUuid();
        String realPath = joinpath(rootdir, relative);
        MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
        Iterator<String> strNames = mr.getFileNames();
        if (null == strNames){
            throw new CommonException(Results.FAIL_FILE_NO_NAME);
        }

        List<Map.Entry<String, String>> lstFileOut = new ArrayList<>();
        List<MultipartFile> lstFile;
        AbstractMap.SimpleEntry entry;
        String fName;
        StringBuilder sb = new StringBuilder();
        while (strNames.hasNext()) {
            lstFile = mr.getFiles(strNames.next());
            for (MultipartFile f : lstFile) {
                fName = f.getOriginalFilename().trim();
                sb.append("上传文件：[")
                    .append(f.getOriginalFilename())
                    .append("/").append(f.getContentType())
                    .append("/").append(f.getSize())
                    .append("]");
                logger.debug(sb.toString());
                if (fName.isEmpty() || (f.getSize() <= 0)) {
                    continue;
                }

                /**
                 * 上传文件到服务器
                 */
                String strFull = joinpath(realPath, fName);
                try {
                    f.transferTo(new File(strFull));
                } catch (IOException e) {
                    logger.error("上传文件失败：", e);
                    throw new CommonException(Results.FAIL_FILE_UPLOAD);
                }
                entry = new AbstractMap.SimpleEntry(f.getOriginalFilename(), joinpath(relative,fName));
                lstFileOut.add(entry);
            }
        }

        return lstFileOut;
    }

    /**
     * 下载特定文件
     * @param request
     * @param response
     * @param absolute
     * @throws Exception
     */
    public void download (HttpServletRequest request, HttpServletResponse response, String absolute) throws CommonException {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("下载文件失败：", e);
            throw new CommonException(Results.FAIL_FILE_UPLOAD);
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        String realpath = joinpath(rootdir, absolute);

        //获取文件的长度
        File file = new File(realpath);
        if(! file.exists()){
            logger.error("文件不存在！");
            throw new CommonException(Results.FAIL_FILE_NONEXIST);
        }

        long fileLength = file.length();
        //设置文件输出类型
        response.setContentType("application/octet-stream;charset=utf-8");
        try {
            response.setHeader
                         (
                             "Content-disposition",
                             "attachment; filename=".concat(URLEncoder.encode(file.getName(), "UTF-8"))
                         );
        } catch (UnsupportedEncodingException e) {
            response.setHeader
                         (
                             "Content-disposition",
                             "attachment; filename=".concat(file.getName())
                         );
            logger.error("不支持的URL编码类型：", e);
        }
        //设置输出长度
        response.setHeader("Content-Length", String.valueOf(fileLength));
        //获取输入流
        try {
            bis = new BufferedInputStream(new FileInputStream(realpath));
        } catch (FileNotFoundException e) {
            logger.error("文件不存在：", e);
            IoUtils.close(bis);
            throw new CommonException(Results.FAIL_FILE_NONEXIST);
        }
        //输出流
        try {
            bos = new BufferedOutputStream(response.getOutputStream());
        } catch (IOException e) {
            logger.error("获取输出流失败！", e);
            IoUtils.close(bos);
            throw new CommonException(Results.FAIL_IO_OPEN);
        }

        byte[] buff = new byte[4096];
        int bytesRead;
        try {
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            IoUtils.close(bis);
            IoUtils.close(bos);
        } catch (IOException e) {
            logger.error("写入输出流失败！", e);
            IoUtils.close(bis);
            IoUtils.close(bos);
            throw new CommonException(Results.FAIL_IO_WRITE);
        }
    }

    /**
     * 删除特定文件，如果absolute指向目录则不会删除，如果文件为目录下最后一个文件，则尝试删除父目录，递归深度为2
     * @param absolute
     * @return
     */
    public Result rm (String absolute) {
        String realpath = joinpath(rootdir,absolute);
        File file = new File(realpath);
        if (! file.exists()){
            return Result.SUCCESS;
        }

        if (file.isDirectory()) {
            return Result.FAILURE_FILE_ISDIR;
        }

        File parent = file.getParentFile();
        file.delete();

        File[] files = parent.listFiles();
        if (null == files || files.length <= 0){
            File parent2 = parent.getParentFile();
            parent.delete();
            files = parent2.listFiles();
            if (null == files || files.length <= 0)
                parent2.delete();
        }
        return Result.SUCCESS;
    }

    /**
     * 删除文件或目录
     * @param absolute
     */
    public void rmdirs (String absolute) {
        String realpath = joinpath(rootdir, absolute);
        File file = new File(realpath);
        if (file.exists()) {
            if (file.isDirectory()) {
                rmdirs(file);
            } else {
                /**
                 * 最后删除该文件或目录
                 */
                file.delete();
            }
        }
    }

    /**
     * 递归删除目录
     * @param dir
     */
    public void rmdirs (File dir) {
        if (null == dir)
            return;
        if (! dir.exists())
            return;
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; ++ i) {
            if (files[i].isDirectory())
                rmdirs(files[i]);
            files[i].delete();
        }
        dir.delete();
        return;
    }

    /**
     * 创建文件夹,如果有则不创建
     *
     * @param absolute
     */
    public boolean mkdir (String absolute) {
        String realpath = new String(rootdir).concat(absolute);
        boolean flag = true;
        File file = new File(realpath);
        if (!file.exists()) {
            file.mkdirs();
            flag = false;
        }
        return flag;
    }

    /**
     * 判断目录是否为空
     * @param dir
     * @return
     */
    public boolean isEmpty (File dir) {
        if (null != dir && dir.exists()) {
            if (dir.isDirectory()) {
                File[] list = dir.listFiles();
                if (list == null || list.length == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断某文件夹下是否存在文件，存在返回true
     *
     * @param absolute
     * @return
     */
    public boolean isEmpty (String absolute) {
        String realpath = new String(rootdir).concat(absolute);
        File file = new File(realpath);
        return isEmpty(file);
    }

    /**
     * 复制整个文件夹内容
     * @param src String 原文件相对路径 如：${rootdir}/fqf
     * @param target String 目标文件相对路径 如：${rootdir}/fqf/ff
     * @return boolean
     */
    public void dupdir (String src, String target) {
        String pathfrom = new String(rootdir).concat(src);
        String pathto = new String(rootdir).concat(target);
        try {
            (new File(pathto)).mkdir(); // 如果文件夹不存在 则建立新文件夹
            File file = new File(pathfrom);
            String[] subFiles = file.list();
            File temp = null;
            for (int i = 0; i < subFiles.length; i++) {
                if (pathfrom.endsWith(File.separator)) {
                    temp = new File(pathfrom + subFiles[i]);
                } else {
                    temp = new File(pathfrom + File.separator + subFiles[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(pathto + File.separator + (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {// 如果是子文件夹
                    dupdir(pathfrom + "/" + subFiles[i], pathto + "/" + subFiles[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();
        }
    }

    /**
     *
     * @Method: mkdirUuid
     * @Description: 为防止一个目录下面出现太多文件，将文件存储到不同的文件夹中
     * @return 新的存储目录
     */
    public String mkdirUuid (){
        Date date = new Date();
        /**
         * ${rootdir}/20150629/f6d6641c-5aec-48c3-8e4f-17775144c1a3/${relative}
         */
        StringBuffer dir = new StringBuffer();
        dir.append(File.separator).append(String.format("%tY%tm%td", date, date, date))
            .append(File.separator).append(UUID.randomUUID().toString());
        String fullpath = joinpath(rootdir, dir.toString());
        File file = new File(fullpath);
        if (! file.exists()) {
            file.mkdirs();
        }

        return dir.toString();
    }

    /**
     *
     * @param path
     * @param subpath
     * @return
     */
    public String joinpath (String path, String subpath) {
        if (path.endsWith(File.separator) || path.endsWith("/") || path.endsWith("\\")) {
            /**
             * path以/或\结尾则需要根据subpath是否以/或\开头拼装
             */
            if (subpath.startsWith(File.separator) || subpath.startsWith("/") || subpath.startsWith("\\"))
                return "".concat(path).concat(subpath.substring(1, subpath.length()));
            else
                return "".concat(path).concat(subpath);
        } else {
            /**
             * path不以路径分隔符结束则根据subpath第一个字符决定拼装
             */
            if (subpath.startsWith(File.separator) || subpath.startsWith("/") || subpath.startsWith("\\"))
                return "".concat(path).concat(subpath);
            else
                return "".concat(path).concat(File.separator).concat(subpath);
        }
    }

    public String realpath (String path) {
        int index;
        String vprefix, vreplace;
        for (int i = 0; i < PATHPFX.values.length; ++ i) {
            vprefix = vo.get(i);
            if (path.startsWith(vprefix)) {
                vreplace = vr.get(i);
                if (vprefix.equals(PATHPFX.APPROOT.protocol())) {
                    if (KitUtils.check(this.approot).failure()) {
                        logger.error("未配置FileUtils.approot");
                        return path;
                    } else
                        vreplace = this.approot;
                }
                return joinpath(vreplace, path.substring(vprefix.length()));
            }
        }
        return path;
    }

    public String getRootdir() {
        return rootdir;
    }

    public void setRootdir(String rootdir) {
        this.rootdir = rootdir;
    }

    public String getApproot() {
        return approot;
    }

    public void setApproot(String approot) {
        this.approot = approot;
    }
}