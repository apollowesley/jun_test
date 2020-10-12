package kiang.teb;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Copyright (c) 2014 by kiang
 *
 * @author kiang
 * @version 0.1-pre
 */
public class Benchmark {
    public static void main(String[] args) throws Exception {
        String date = new SimpleDateFormat("yyy-MM-dd hh:mm:ss").format(new Date());
        Properties properties = TebUtilities.getProperties();
        boolean[] binaries;
        String stream = properties.getProperty("stream");
        if ("byte".equals(stream)) {
            binaries = new boolean[]{true};
        } else if ("char".equals(stream)) {
            binaries = new boolean[]{false};
        } else {
            binaries = new boolean[]{true, false};
        }
        String locate = properties.getProperty("locate");
        String[] targets = TebUtilities.splitToken(properties.getProperty("target"), ";");
        String[] engines = TebUtilities.splitToken(properties.getProperty("engine"), ";");
        File cmd, rst;
        Process process;
        String name, exec;
        TebResult[] results = new TebResult[engines.length];
        File rpt = new File(locate, "RPT" + properties.getProperty("thread") + ".html");
        BufferedWriter bw = TebReport.writeHead(rpt, properties);
        try {
            for (int i = 0, r = targets.length; i < r; i++) {
                for (int j = 0, s = binaries.length; j < s; j++) {
                    for (int k = 0, t = engines.length; k < t; k++) {
                        name = properties.getProperty(engines[k] + ".name", "").trim();
                        System.out.println("Test [" + engines[k] + "=" + name + "]@[" + targets[i] + "]@[" + (binaries[j] ? "byte" : "char") + " stream]...");
                        cmd = new File(locate, engines[k] + ".bat");
                        rst = new File(locate, engines[k] + ".txt");
                        rst.delete();
                        Benchmark.writeCmd(properties, binaries[j], targets[i], engines[k], cmd);
                        exec = "cmd /c " + locate + File.separatorChar + engines[k] + ".bat";
                        process = Runtime.getRuntime().exec(exec);
                        process.waitFor();
                        cmd.delete();
                        Benchmark.readResult(rst, results[k] = new TebResult());
                        rst.delete();
                        results[k].setName(name);
                        results[k].setSite(properties.getProperty(engines[k] + ".site", "").trim());
                    }
                    Arrays.sort(results, new Comparator<TebResult>() {
                        @Override
                        public int compare(TebResult o1, TebResult o2) {
                            if (o1.getTime() < o2.getTime()) {
                                return -1;
                            } else if (o1.getTime() > o2.getTime()) {
                                return 1;
                            } else {
                                return 0;
                            }
                        }
                    });
                    TebReport.writeBody(bw, properties, binaries[j], targets[i], results, date);
                }
            }
        } finally {
            TebReport.writeTail(bw);
        }
    }

    private static void writeCmd(Properties properties, boolean binary, String target, String engine, File cmd) throws Exception {
        if (!cmd.getParentFile().exists()) {
            cmd.getParentFile().mkdirs();
        }
        if (!cmd.exists()) {
            cmd.createNewFile();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(cmd));
        try {
            String javaHome = System.getenv("JAVA_HOME");
            if (javaHome != null && javaHome.trim().length() > 0) {
                bw.write("@set JAVA_HOME=");
                bw.write(javaHome);
                bw.newLine();
            }
            bw.write("@set PATH=.;%JAVA_HOME%\\bin;");
            bw.newLine();
            bw.write("@set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\\lib\\tools.jar");
            bw.newLine();
            bw.write("@set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\\lib\\dt.jar");
            bw.newLine();
            bw.write("@set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\\jre\\lib\\rt.jar");
            bw.newLine();
            addJars(bw, properties.getProperty("locate"));
            addJars(bw, properties.getProperty("library"));
            bw.write("@set CLASSPATH=%CLASSPATH%;" + cmd.getParentFile().getAbsolutePath() + ";");
            bw.newLine();
            bw.write("@set binary=" + binary);
            bw.newLine();
            bw.write("@set thread=" + properties.getProperty("thread", "1"));
            bw.newLine();
            bw.write("@set record=" + properties.getProperty("record", "20"));
            bw.newLine();
            bw.write("@set period=" + properties.getProperty("period", "10"));
            bw.newLine();
            bw.write("@set warmed=" + properties.getProperty("warmed", "500"));
            bw.newLine();
            bw.write("@set looped=" + properties.getProperty("looped", "10000"));
            bw.newLine();
            bw.write("@set locate=" + properties.getProperty("locate", ""));
            bw.newLine();
            bw.write("@set source=" + properties.getProperty("source", "UTF-8"));
            bw.newLine();
            bw.write("@set target=" + target);
            bw.newLine();
            bw.write("@set tester=" + engine);
            bw.newLine();
            bw.write("@set simple=" + properties.getProperty(engine + ".test"));
            bw.newLine();
            bw.write("@\"%JAVA_HOME%\"\\bin\\java");
            bw.write(" ");
            bw.write(properties.getProperty("option", ""));
            bw.write(" ");
            bw.write(Performer.class.getName());
            bw.newLine();
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    private static void addJars(BufferedWriter bw, String lib) throws Exception {
        if (lib.trim().length() == 0) {
            return;
        }
        File[] files = new File(lib).listFiles();
        if (files == null) {
            return;
        }
        for (int i = 0, n = files.length; i < n; i++) {
            if (!files[i].getName().endsWith(".jar")) {
                continue;
            }
            bw.write("@set CLASSPATH=%CLASSPATH%;");
            bw.write(files[i].getAbsolutePath());
            bw.newLine();
        }
    }

    private static void readResult(File rst, TebResult result) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(rst));
        List<Long> launch = new ArrayList<Long>();
        List<Long> finish = new ArrayList<Long>();
        List<Long> mfs = new ArrayList<Long>();
        try {
            String line;
            result.setOnceIo(Long.parseLong(br.readLine().substring(4)));
            result.setMassIo(Long.parseLong(br.readLine().substring(4)));
            result.setOnceOut(Long.parseLong(br.readLine().substring(4)));
            result.setMassOut(Long.parseLong(br.readLine().substring(4)));
            while ((line = br.readLine()) != null) {
                if (line.startsWith("bot:")) {
                    launch.add(Long.parseLong(line.substring(4)));
                } else if (line.startsWith("eot:")) {
                    finish.add(Long.parseLong(line.substring(4)));
                } else if (line.startsWith("mfs:")) {
                    mfs.add(Long.parseLong(line.substring(4)));
                }
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
        Collections.sort(mfs);
        long mx = 0;
        int size = mfs.size();
        for (int i = 0; i < size; i++) {
            mx += mfs.get(i);
        }
        result.setPermMem(size == 0? 0 : mx / size);
        result.setTime(Collections.max(finish) - Collections.min(launch));
    }
}
