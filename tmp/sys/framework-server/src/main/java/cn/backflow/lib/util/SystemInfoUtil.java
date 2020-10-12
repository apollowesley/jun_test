package cn.backflow.lib.util;

import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * &lt;获取系统信息类&gt;
 *
 * &lt;获取系统信息，如CPU、内存、硬盘使用情况&gt;
 *
 * @version [版本号, 2012-5-9]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SystemInfoUtil {
    private static final OperatingSystemMXBean os = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
    private static final long MB = 1024L * 1024;
    private static final long GB = 1024L * MB;
    private static final long TOTAL_MEM = Math.round((double) os.getTotalPhysicalMemorySize() / GB);

    /**
     * 保留给定的小数位 (使用Math.round)
     *
     * @param source   要调整的浮点娄
     * @param fraction 需要保留的小数位
     */
    private static double fixFraction(double source, int fraction) {
        double pos = Math.pow(10, fraction);
        double tem = Math.round(source * pos);
        return tem / pos;
    }

    /**
     * 返回 CPU使用率, 内存使用率, 可用内存, 总内存 JSON数据
     */
    public static String info() {
        double cpu = os.getSystemCpuLoad();
        if (cpu == -1) cpu = 0;
        double mem = (double) os.getFreePhysicalMemorySize() / os.getTotalPhysicalMemorySize();
        double free = (double) os.getFreePhysicalMemorySize() / GB;
        return new JsonMap()
                .put("cpu", fixFraction(cpu * 100, 0))
                .put("mem", 100 - fixFraction(mem * 100, 0))
                .put("fre", fixFraction(free, 1))
                .put("tol", TOTAL_MEM)
                .toString();
    }

    /**
     * 获取内存使用率
     */
    public static double getFreeMemeryPercentage() {
        double totalVirtualMemory = os.getTotalSwapSpaceSize(); // 总的物理内存+虚拟内存
        double freePhysicalMemory = os.getFreePhysicalMemorySize(); // 剩余的物理内存
        double left = 1 - freePhysicalMemory / totalVirtualMemory;
        return fixFraction(left, 2);
    }

    /**
     * 获取文件系统使用率, 数据格式为:
     * <pre>
     * key -> 文件路径
     * value -> Double数组 [ 已用空间, 剩余空间, 总空间, 可用空间, 使用率 ]
     * </pre>
     */
    public static Map<String, Double[]> getDiskUsagePercentage() {
        Map<String, Double[]> usages = new HashMap<>();
        for (File file : File.listRoots()) {
            String path = file.getPath(); // 磁盘路径
            double free = file.getFreeSpace(); // 空闲空间
            double total = file.getTotalSpace(); // 总空间
            double usable = file.getUsableSpace(); // 可用空间
            double used = total - usable;
            usages.put(path, new Double[]{
                    fixFraction(used / GB, 1), // 已用空间
                    fixFraction(free / GB, 1),  // 剩余空间
                    fixFraction(total / GB, 1), // 总空间
                    fixFraction(usable / GB, 1), // 可用空间
                    fixFraction(used / total, 2), // 使用率
            });
        }
        return usages;
    }

    public static void print() {
        Properties props = System.getProperties();//获取当前的系统属性
        props.list(System.out);//将属性列表输出

        System.out.print("CPU个数:");//Runtime.getRuntime()获取当前运行时的实例
        System.out.println(Runtime.getRuntime().availableProcessors());//availableProcessors()获取当前电脑CPU数量
        System.out.print("虚拟机内存总量:");
        System.out.println(Runtime.getRuntime().totalMemory());//totalMemory()获取java虚拟机中的内存总量
        System.out.print("虚拟机空闲内存量:");
        System.out.println(Runtime.getRuntime().freeMemory());//freeMemory()获取java虚拟机中的空闲内存量
        System.out.print("虚拟机使用最大内存量:");
        System.out.println(Runtime.getRuntime().maxMemory());//maxMemory()获取java虚拟机试图使用的最大内存量
        System.out.println("Java的运行环境版本：" + props.getProperty("java.version"));
        System.out.println("Java的运行环境供应商：" + props.getProperty("java.vendor"));
        System.out.println("Java供应商的URL：" + props.getProperty("java.vendor.url"));
        System.out.println("Java的安装路径：" + props.getProperty("java.home"));
        System.out.println("Java的虚拟机规范版本：" + props.getProperty("java.vm.specification.version"));
        System.out.println("Java的虚拟机规范供应商：" + props.getProperty("java.vm.specification.vendor"));
        System.out.println("Java的虚拟机规范名称：" + props.getProperty("java.vm.specification.name"));
        System.out.println("Java的虚拟机实现版本：" + props.getProperty("java.vm.version"));
        System.out.println("Java的虚拟机实现供应商：" + props.getProperty("java.vm.vendor"));
        System.out.println("Java的虚拟机实现名称：" + props.getProperty("java.vm.name"));
        System.out.println("Java运行时环境规范版本：" + props.getProperty("java.specification.version"));
        System.out.println("Java运行时环境规范供应商：" + props.getProperty("java.specification.vender"));
        System.out.println("Java运行时环境规范名称：" + props.getProperty("java.specification.name"));
        System.out.println("Java的类格式版本号：" + props.getProperty("java.class.version"));
        System.out.println("Java的类路径：" + props.getProperty("java.class.path"));
        System.out.println("加载库时搜索的路径列表：" + props.getProperty("java.library.path"));
        System.out.println("默认的临时文件路径：" + props.getProperty("java.io.tmpdir"));
        System.out.println("一个或多个扩展目录的路径：" + props.getProperty("java.ext.dirs"));
        System.out.println("操作系统的名称：" + props.getProperty("os.name"));
        System.out.println("操作系统的构架：" + props.getProperty("os.arch"));
        System.out.println("操作系统的版本：" + props.getProperty("os.version"));
        System.out.println("文件分隔符：" + props.getProperty("file.separator"));   //在 unix 系统中是＂／＂
        System.out.println("路径分隔符：" + props.getProperty("path.separator"));   //在 unix 系统中是＂:＂
        System.out.println("行分隔符：" + props.getProperty("line.separator"));   //在 unix 系统中是＂/n＂
        System.out.println("用户的账户名称：" + props.getProperty("user.name"));
        System.out.println("用户的主目录：" + props.getProperty("user.home"));
        System.out.println("用户的当前工作目录：" + props.getProperty("user.dir"));
    }
}