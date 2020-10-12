package nivalsoul.kettle.plugins.util.bigdata.hive;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.fs.permission.FsPermission;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 输出到文本文件代理类
 *
 * @Author 空山苦水禅人
 * @Date 2018/8/5 16:24
 */
public class TextProxy implements IProxy{
    private Configuration conf;
    private Path path;
    private String fieldSeparator = "\t";
    private String lineSeparator = "\n";
    FileSystem fs;
    FSDataOutputStream fos = null;
    OutputStreamWriter osw = null;
    BufferedWriter bw = null;
    
    public TextProxy(Configuration conf, String fileName, String fieldSeparator,
                     String lineSeparator, boolean cleanOutput) throws IOException {
        this.conf = conf;
        this.fieldSeparator = fieldSeparator;
        this.lineSeparator = lineSeparator;
        try {
            path = new Path(fileName);
            fs = path.getFileSystem(conf);
            if (!fs.exists(path.getParent())) {
                fs.mkdirs(path.getParent(), FsPermission.getDirDefault());
            }
            fs.setPermission(path.getParent(), new FsPermission(FsAction.ALL, FsAction.ALL, FsAction.ALL));
            if (cleanOutput && fs.exists(path)) {
                fs.delete(path, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    @Override
    public void setBlockSize(int blockSize) {
        
    }

    @Override
    public void setPageSize(int parseInt) {

    }

    @Override
    public void start() throws IOException {
        fos = fs.create(path);
        osw = new OutputStreamWriter(fos, "UTF-8");
        bw = new BufferedWriter(osw);
    }

    @Override
    public void writeRow(Object[] row, String[] fields, String[] types) throws IOException {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < fields.length; i++) {
            Object value = row[i];
            if (value == null) {
                sb.append("\\N");
            } else {
                sb.append(value.toString().replaceAll("\t", "    ")
                        .replaceAll("\r\n"," ")
                        .replaceAll("\n"," "));
            }
            sb.append(fieldSeparator);
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(lineSeparator);
        bw.append(sb.toString());
    }

    @Override
    public void close() throws IOException {
        bw.flush();
        closeIO(bw,osw,fos);
    }

    public static void closeIO(Closeable... closeables){
        if(closeables != null){
            for(Closeable io : closeables){
                if(io !=null ) {
                    try {
                        io.close();
                    } catch (Exception e) {
                        io = null;
                    }
                }
            }
        }
    }
}
