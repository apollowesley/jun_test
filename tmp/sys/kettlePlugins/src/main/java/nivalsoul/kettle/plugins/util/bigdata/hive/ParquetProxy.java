package nivalsoul.kettle.plugins.util.bigdata.hive;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.hadoop.ParquetFileWriter.Mode;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.schema.MessageType;

import java.io.IOException;

public class ParquetProxy implements IProxy{
	private Configuration conf;
	private Path path;
	private ParquetWriter<Group> writer = null;
	private MessageType schema = null;
	private SimpleGroupFactory f;
	private Mode writeMode = Mode.CREATE;
	private int blockSize = 134217728; //块大小:128M
	private int pageSize = 1048576; //页大小：1024K

	public ParquetProxy(Configuration conf, String fileName, MessageType schema, 
			SimpleGroupFactory f, boolean cleanOutput) throws Exception {
		path = new Path(fileName);
		this.conf = conf;
		this.schema = schema;
		this.f = f;
		FileSystem fs;
		try {
			fs = path.getFileSystem(conf);
			if (!fs.exists(path.getParent())) {
			    fs.mkdirs(path.getParent(), FsPermission.getDirDefault());
			}
			fs.setPermission(path.getParent(), new FsPermission(FsAction.ALL, FsAction.ALL, FsAction.ALL));
			if (cleanOutput && fs.exists(path)) {
				fs.delete(path, true);
				writeMode = Mode.OVERWRITE;
		    }else{
		    	writeMode = Mode.CREATE;
		    }
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	@Override
	public void start() throws IllegalArgumentException, IOException {
		if(schema == null){
			throw new IllegalArgumentException("file schema is not set");
		}
		writer = ExampleParquetWriter.builder(path)
				.withConf(conf)
		        .withType(schema)
		        .withCompressionCodec(CompressionCodecName.UNCOMPRESSED)
		        .withWriteMode(writeMode)
		        .withRowGroupSize(blockSize)
		        .withPageSize(pageSize)
		        .build();
	}
	
	public void writeRow(Group group) throws IOException {
		writer.write(group);
	}
	
	@Override
	public void writeRow(Object[] row, String[] fields, String[] types) throws IOException {
		Group group = f.newGroup();
    	for(int i=0;i<fields.length;i++){
    		switch (types[i]){
    	    case "long":
    	    	group.add(fields[i], row[i]!=null ? (long)row[i] : 0);
    	    	break;
    	    case "integer":
    	    case "short":
    	    case "byte":
    	    	group.add(fields[i], row[i]!=null ? (int)row[i] : 0);
    	    	break;
    	    case "float":
    	    case "double":
    	    	group.add(fields[i], row[i]!=null ? Double.valueOf(row[i].toString()) : 0);
    	        break;
    	    case "boolean":
    	    	group.add(fields[i], row[i]!=null ? (boolean)row[i] : false);
    	        break;
    	    case "string":
    	    default:
    	    	group.add(fields[i], row[i]!=null ? row[i].toString() : "");
    	    }
    	}
    	writer.write(group);
	}
	
	@Override
	public void close() throws IOException {
		if(writer != null){
			writer.close();
			path.getFileSystem(conf)
				.setPermission(path, new FsPermission(FsAction.ALL, FsAction.ALL, FsAction.ALL));
		}
	}
}
