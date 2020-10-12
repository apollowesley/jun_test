package com.foo.common.base.utils;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.common.base.*;
import com.google.common.collect.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.validator.routines.DoubleValidator;
import org.apache.commons.validator.routines.IntegerValidator;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Weeks;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class FooUtils {

    private final static String DES = "DES";
    private final static String key = "x!%^&2azdd";
    public final static String global_test_file_working_directory_str = "";

    public final static File global_test_file_working_directory = new File(
            global_test_file_working_directory_str);

    private static Map<String, String> globalPropertiesMap;
    private static BiMap<String, Integer> excelLetter2IntBiMap;

    public static Gson getGson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls()
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson;
    }

    public static boolean isProjectSupportJdk8() {
        try {
            Sets.newConcurrentHashSet();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * renmae fileName is just copy a file to a new location.
     *
     * @param srcFile  srcFile
     * @param destFile destFile
     * @throws IOException IOException
     */
    public static void renameFileName(File srcFile, File destFile)
            throws IOException {
        FileUtils.copyFile(srcFile, destFile);
    }

    public static int getDaysBetweenPeriod(DateTime startDateTime, DateTime endDateTime, boolean isReturnWeekend) {
        Preconditions.checkArgument(endDateTime.isAfter(startDateTime));
        int result = 0;
        int endDayOfWeek = endDateTime.dayOfWeek().get();
        if (endDayOfWeek == 6 || endDayOfWeek == 7) {
            endDateTime = endDateTime.plusWeeks(1).withDayOfWeek(1);
        }
        int weeksBetweenPeriod = Weeks.weeksBetween(startDateTime.withDayOfWeek(1), endDateTime.withDayOfWeek(1)).getWeeks();
        int daysBetweenPeriod = Days.daysBetween(startDateTime, endDateTime).getDays();
        if (isReturnWeekend) {
            result = weeksBetweenPeriod * 2;
        } else {
            result = daysBetweenPeriod - weeksBetweenPeriod * 2;
        }
        logger.info("startDateTime is:{}", startDateTime);
        logger.info("endDateTime is:{}", endDateTime);
        logger.info("weeksBetweenPeriod is:{}", weeksBetweenPeriod);
        logger.info("result is:{}", result);
        return result;
    }

    public static int getDaysFromPeriod(DateTime startDateTime, int plusDays, boolean isReturnWeekend) {
        DateTime endDateTime = startDateTime.plusDays(plusDays);
        return getDaysBetweenPeriod(startDateTime, endDateTime, isReturnWeekend);
    }


    public static String toJson(Object object) {
        return getGson().toJson(object);
    }

    public static String getGlobalArtifactId() {
        final String propertyName = "artifactId";
        final String s = getGlobalProperties().get(propertyName);
        Preconditions.checkNotNull(s, "Please config:[" + propertyName
                + "] in file:[global-config.properties]");
        return s;
    }

    /**
     * Concat gitHome and artifactId to index global artifact path.
     *
     * @return global artifact path such as:【/Users/Steve/tools/myGit/kitty】
     */
    public static String getGlobalArtifactPath() {
        final String globalArtifactPath = FilenameUtils.concat(getGlobalGitHome(), getGlobalArtifactId());
        return globalArtifactPath;
    }

    public static File getGlobalPomFile() {
        final String pomFilePath = getGlobalGitHome() + getFileSeparator() +
                getGlobalArtifactId() + getFileSeparator() + "pom.xml";
        File pomFile = new File(pomFilePath);
        Preconditions.checkNotNull(pomFile.isFile(), "Please config:[pom.xml] in path:" +
                pomFilePath + "");
        return pomFile;
    }

    public static String getGlobalMvnBash() {
        final String propertyName = "mvnBash";
        final String s = getGlobalProperties().get(propertyName);
        Preconditions.checkNotNull(s, "Please config:[" + propertyName
                + "] in file:[global-config.properties]");
        return s;
    }


    public static String getGlobalGitHome() {
        final String propertyName = "gitHome";
        final String s = getGlobalProperties().get(propertyName);
        Preconditions.checkNotNull(s, "Please config:[" + propertyName
                + "] in file:[global-config.properties]");
        return s;
    }

    public static void copyDirectory(String srcDirPath, String targetDirPath)
            throws Exception {
        if (IsMacOs()) {
            Process p = executeCommand(
                    " cp -r " + srcDirPath + " " + targetDirPath + " ");
            StringWriter sWriterSuccess = new StringWriter();
            IOUtils.copy(p.getInputStream(), sWriterSuccess, "utf-8");
        } else {
            FileUtils.copyDirectory(new File(srcDirPath),
                    new File(targetDirPath));
        }
    }

    public final static Map<String, String> getGlobalProperties() {
        if (globalPropertiesMap != null) {
            return globalPropertiesMap;
        }
        Properties properties = new Properties();
        final String globalConfigPropertiesFileName = "global-config.properties";
        try {
            properties.load(new ClassPathResource(globalConfigPropertiesFileName).getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Read configuration of:【"
                    + globalConfigPropertiesFileName + "】 error.");
        }
        Map<String, String> resultMap = Maps.newHashMap();
        properties.keySet().forEach(t -> resultMap.put(t.toString(), properties.getProperty(t.toString())));
        return resultMap;
    }


    public static String getSeparateLine() {
        return "------------------------------------------------------------------------";
    }

    public static File getTestDirectory() {
        return global_test_file_working_directory;
    }

    public static int getInteger(Object object, int defaultInteger) {
        if (isInteger(object))
            return Integer.valueOf(object + "");
        else
            return defaultInteger;
    }

    public static String extractIntegerFromString(String object) {
        return object.replaceFirst(".*?(\\d+).*", "$1");
    }

    public static String getString(Object object) {
        return Strings
                .nullToEmpty(MoreObjects.firstNonNull(object, "").toString());
    }

    public static boolean isInteger(Object code) {
        if (code == null) {
            return false;
        }
        return IntegerValidator.getInstance().isValid(code.toString());
    }

    public static int getDirectoryFilesNumber(File myDirectory)
            throws FileSystemException {

        Preconditions.checkArgument(myDirectory.isDirectory(),
                "you should passed in directory here.");

        List<File> files = (List<File>) FileUtils.listFiles(myDirectory,
                TrueFileFilter.TRUE, TrueFileFilter.TRUE);

        return files.size();

    }

    /**
     * 统计目录下的所有文件数目和文件夹数目,并且打印文件或者目录的名称
     *
     * @param myDirectory destination target dir.
     * @throws FileSystemException error.
     */
    public static void printDirectoryFilesAndDirs(File myDirectory)
            throws FileSystemException {

        Preconditions.checkArgument(myDirectory.isDirectory(),
                "you should passed in directory here.");

        List<File> files = (List<File>) FileUtils.listFiles(myDirectory,
                TrueFileFilter.TRUE, TrueFileFilter.TRUE);
        List<File> folders = (List<File>) FileUtils.listFilesAndDirs(
                myDirectory, FileFilterUtils.directoryFileFilter(),
                TrueFileFilter.TRUE);

        logger.info("Contains: {} files, {} folders.", files.size(),
                folders.size());
        logger.trace("Contains: files:{}", files.toString());
        logger.trace("Contains: directorys:{}", folders.toString());
    }


    public static Logger getRootLogger() {
        return LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    }

    public static File getTestJavaFile() {
        return FileUtils.getFile(global_test_file_working_directory,
                "test.java");
    }

    public static File getTestLogFile() {
        return FileUtils.getFile(global_test_file_working_directory, "log.txt");
    }

    public static File getTestExcel2003File() {
        return FileUtils.getFile(global_test_file_working_directory,
                "test.xls");
    }


    final static Logger logger = LoggerFactory.getLogger("common");

    public static void info(String message, Object... object) {
        logger.info(message, object);
    }


    public static void printList(List<?> list) {
        for (Object string : list) {
            System.out.println(string);
        }
    }

    public static String parseMagnetStr(String source) {
        int magnetProtocolLength = 60;

        int x = source.indexOf("magnet:?xt=", 0);

        if (x == -1) {
            System.out.print("No magnet exist，reutrn");
            return "";
        }
        return source.substring(x, x + magnetProtocolLength);

    }

    public static String toDateFromYear2Second(DateTime dateTime) {
        return dateTime.toString("yyyy-MM-dd HH:mm:ss");
    }


    public static Date toDateFromYear2Second(String date) {
        return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
                .parseDateTime(date).toDate();
    }


    public static <T> List<T> nullToEmpty(List<T> list) {
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return Lists.newArrayList();
        }
    }

    public static String formatDouble(String result) {
        return String.format("%1$.2f", Double.parseDouble(result));
    }

    public static String formatDouble(double result) {
        return String.format("%1$.2f", result);
    }

    /**
     * 判定是否是数字
     */
    public static boolean isNum(String code) {
        return DoubleValidator.getInstance().isValid(code);
    }

    /**
     * 判断是否是字符
     */
    public static boolean isCharacter(String code) {
        return Pattern.compile("[A-Za-z]*").matcher(code).matches();
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 格式化100000为100,000样式的字符串
     *
     * @param number number
     */
    public static String formartNumber(Object number) {
        DecimalFormat dec = new DecimalFormat();
        dec.setGroupingUsed(true);
        dec.setGroupingSize(3);
        return dec.format(number);
    }

    /**
     * 得到c不匹配周模式的ron表达式，例如56 20 11 26 7 ? 2013
     *
     * @param myDateTime myDateTime
     */
    public static String getCronExpressionFromDateTime(DateTime myDateTime) {
        return "" + myDateTime.getSecondOfMinute() + " "
                + myDateTime.getMinuteOfHour() + " " + myDateTime.getHourOfDay()
                + " " + myDateTime.getDayOfMonth() + " "
                + myDateTime.getMonthOfYear() + " ? " + myDateTime.getYear()
                + "";
    }

    /**
     * 得到oracle方言的时间查询片段，例如<br>
     * <code>TO_DATE('2013-07-26 11:24:50','YYYY-MM-DD HH24:MI:SS')</code>
     *
     * @param dateTime dateTime
     */
    public static String getOracleSearchFragment(DateTime dateTime) {
        return "TO_DATE('" + FooUtils.toDateFromYear2Second(dateTime)
                + "','YYYY-MM-DD HH24:MI:SS')";
    }


    /**
     * 打印系统环境变量
     */
    public static void printSystemEnv() {
        for (Entry<String, String> entry : System.getenv().entrySet()) {
            logger.info("Key:{},value:{}", entry.getKey(), entry.getValue());
        }

    }

    /**
     * 打印系统环境变量
     */
    public static String getSystemDesktopPath() {
        return MoreObjects.firstNonNull(System.getenv("DESKTOP"),
                "C:\\Users\\Steve\\Desktop");
    }


    /**
     * 如果路径不存在就创建路径，如果存在则忽略该路径
     *
     * @param fullFilePath fullFilePath
     */
    public static void forceMkdir(String fullFilePath) throws IOException {

        File myFile = new File(fullFilePath);
        if (!myFile.isDirectory()) {
            logger.warn(
                    "directory of:{} does not exist,we will create it for you.",
                    fullFilePath);
            FileUtils.forceMkdir(myFile);
            return;
        }
        logger.info("directory of:{} already exist,we will jump.",
                fullFilePath);
    }

    /**
     * 获得windows系统盘符，例如：D:
     *
     * @param path 必须是包含盘符的路径
     */
    public static String getWindowsDrivePartition(String path) {
        return FilenameUtils.getFullPathNoEndSeparator(path).substring(0, 2);
    }

    public static void cleanDirectory(File dir) throws IOException {
        if (dir == null || !dir.isDirectory()) {
            logger.error("your args of:{} is not a directory,just jump it.",
                    dir);
            return;
        }
        FileUtils.cleanDirectory(dir);
    }

    public static File getClassPathResourceFile(String resourceName)
            throws IOException {
        return new ClassPathResource(resourceName).getFile();
    }

    public static InputStream getClassPathResourceInputStream(
            String resourceName) {
        try {
            return new ClassPathResource(resourceName).getInputStream();
        } catch (IOException e) {
            logger.error("error:{}", e);
        }
        return null;
    }

    public static Properties getPropertiesFromInputStream(
            InputStream inputStream) throws IOException {
        Properties p = new Properties();
        p.load(inputStream);
        return p;
    }

    public static Properties getPropertiesFromResourceFile(String resourceName)
            throws IOException {
        return getPropertiesFromInputStream(
                getClassPathResourceInputStream(resourceName));
    }

    public static Properties getPropertiesFromResourceFileSilent(
            String resourceName) {
        try {
            return getPropertiesFromInputStream(
                    getClassPathResourceInputStream(resourceName));
        } catch (IOException e) {
            logger.error("resource file of:{} does not exist.", resourceName);
        }
        return null;
    }

    public static String generateCreateDatabaseSql(String databaseName) {
        return "CREATE DATABASE `" + databaseName
                + "` DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci";
    }


    public static String getJavaClassFieldTypeString(Field field) {

        String fieldTypeName = field.getType().getName();

        if (fieldTypeName.equals("java.util.Date")) {
            return "Date";
        } else if (fieldTypeName.equals("java.lang.String")) {
            return "String";
        } else if (fieldTypeName.equals("int")) {
            return "int";
        } else if (fieldTypeName.equals("double")) {
            return "double";
        } else if (fieldTypeName.equals("java.lang.Integer")) {
            return "int";
        } else {
            logger.error("error type on filed:{} for type:{}", field.getName(),
                    fieldTypeName);
            throw new UnsupportedOperationException(
                    "error type on field:" + field.getName());
        }
    }

    public static boolean IsMacOs() {
        return SystemUtils.IS_OS_MAC;
    }

    public static boolean IsWindowsOs() {
        return SystemUtils.IS_OS_WINDOWS;
    }

    public static String getUserHome() {
        return SystemUtils.getUserHome().toString();
    }

    /**
     * 获得全局测试文件夹
     *
     * @return
     */
    public static String getGlobalAutoGeneratedDirectoryStr() {
        if (IsMacOs()) {
            return SystemUtils.getUserHome() + "/tmp/test/autoGenerated";
        }
        return "d:\\tmp\\test\\autoGenerated";
    }

    public static String getFileSeparator() {
        return SystemUtils.FILE_SEPARATOR;
    }

    /**
     * execute command,support os-x and windows system only.
     *
     * @param myCommand
     * @throws IOException
     */
    public static Process executeCommand(String myCommand) throws IOException {
        if (SystemUtils.IS_OS_MAC) {
            return Runtime.getRuntime()
                    .exec(new String[]{"/bin/bash", "-c", myCommand});
        }
        return Runtime.getRuntime().exec(myCommand);
    }

    /**
     * @param scopes   用于模板的键值对，具体请参考Mustache文档
     * @param template 以resources目录为根路径的模板地址，例如/myTmpl/myTmpl.jsp
     */
    public static String generateDataWithMustache(Map<String, Object> scopes,
                                                  String template) throws Exception {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(template);
        Writer writer = new StringWriter();
        mustache.execute(writer, scopes);
        writer.flush();
        String result = writer.toString();
        return result;
    }

    /**
     * Writes a String to a file creating the file if it does not exist. NOTE: As from v1.3, the parent directories of the file will be created if they do not exist.
     *
     * @param file the file to write
     * @param data the content to write to the file
     * @throws IOException
     */
    public static void writeStringToFile(File file, String data)
            throws IOException {
        FileUtils.writeStringToFile(file, data, Charset.forName("utf-8"));
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data 加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) {
        try {
            Md5PasswordEncoder encoder = new Md5PasswordEncoder();
            String salt = key;
            return encoder.encodePassword(data, salt);
        } catch (Exception e) {
            logger.error("encrypt error here", e);
            return "";
        }
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data 加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data) throws Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf, key.getBytes());
        return new String(bt);
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }

    /**
     * Intend for excel column mapping,for an example,Z(ignore case) mapping to 25.
     * <p>
     * Support regular expressions only as:[a-zA-Z][a-zA-Z]?
     *
     * @param excelColumnLabel
     * @return
     */
    public static int getExcelColumn(String excelColumnLabel) {
        Preconditions.checkNotNull(excelColumnLabel);
        Preconditions.checkArgument(Pattern.compile("[a-zA-Z][a-zA-Z]?").matcher(excelColumnLabel).matches(), "Support [a-z][a-z]? only.");
        initExcelLetter2IntMap();
        excelColumnLabel = excelColumnLabel.toLowerCase();
        if (excelColumnLabel.length() == 1) {
            return excelLetter2IntBiMap.get(excelColumnLabel);
        }
        return (excelLetter2IntBiMap.get(excelColumnLabel.substring(0, 1)) + 1) * 26 +
                excelLetter2IntBiMap.get(excelColumnLabel.substring(1, 2));
    }

    public static String getExcelColumnLabel(int columnNum) {
        Preconditions.checkArgument(columnNum >= 0 && columnNum <= 701, "support A to ZZ only.");
        initExcelLetter2IntMap();
        final int charLength = excelLetter2IntBiMap.size();
        Map<Integer, String> myMap = excelLetter2IntBiMap.inverse();
        if (columnNum >= 0 && columnNum < charLength) {
            return myMap.get(columnNum).toUpperCase();
        }
        int a = columnNum / charLength;
        int b = columnNum % charLength;
        final String result = myMap.get(a - 1) + myMap.get(b);
        return result.toUpperCase();

    }

    private static void initExcelLetter2IntMap() {
        if (excelLetter2IntBiMap != null) {
            return;
        }
        final char startChar = 'a';
        excelLetter2IntBiMap = HashBiMap.create(26);
        int x = 0;
        for (int i = 0; i < 26; i++) {
            excelLetter2IntBiMap.put(String.valueOf((char) (startChar + x)), x++);
        }
    }

    public static void main(String[] args) {
        initExcelLetter2IntMap();
        logger.info("{}", excelLetter2IntBiMap);
    }

    public static String getExcelCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return toDateFromYear2Second(new DateTime(cell.getDateCellValue()));
            } else {
                return cell.getNumericCellValue() + "";
            }
        } else {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            return cell.getRichStringCellValue().toString().trim();
        }
    }


}
