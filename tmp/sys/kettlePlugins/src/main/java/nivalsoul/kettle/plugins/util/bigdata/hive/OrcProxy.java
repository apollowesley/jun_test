package nivalsoul.kettle.plugins.util.bigdata.hive;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.DoubleColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.orc.CompressionKind;
import org.apache.orc.OrcFile;
import org.apache.orc.TypeDescription;
import org.apache.orc.Writer;

import java.io.IOException;

public class OrcProxy implements IProxy{
	private Configuration conf;
	private Path path;
	private TypeDescription schema;
	private int blockSize = 134217728; //块大小:128M
	private Writer writer = null;
	private VectorizedRowBatch batch;
	
	public OrcProxy(Configuration conf, String fileName, TypeDescription schema, boolean cleanOutput)
			throws Exception {
		this.conf = conf;
		this.schema = schema;
		this.path = new Path(fileName);
		FileSystem fs;
		try {
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
		this.blockSize = blockSize;
	}
	
	@Override
	public void setPageSize(int parseInt) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void start() throws IOException, IllegalArgumentException {
		if(schema == null){
			throw new IllegalArgumentException("file schema is not set");
		}
		writer = OrcFile.createWriter(path,
				OrcFile.writerOptions(conf)
		          .setSchema(schema)
		          .stripeSize(67108864)
		          .bufferSize(131072)
		          .blockSize(blockSize)
		          .compress(CompressionKind.ZLIB)
		          .version(OrcFile.Version.V_0_12));
		batch = schema.createRowBatch();
	}
	
	@Override
	public void writeRow(Object[] row, String[] fields, String[] types) throws IOException {
		int rowCount = batch.size++;
		for(int i=0;i<fields.length;i++){
    		switch (types[i]){
    		case "long":
     	    case "integer":
     	    case "short":
     	    case "byte":
    	    	if(row[i]==null) row[i]=0;
    	    	((LongColumnVector) batch.cols[i]).vector[rowCount] = Long.valueOf(row[i].toString());
    	    	break;
     	   case "float":
     	   case "double":
    	    	if(row[i]==null) row[i]=0;
    	    	((DoubleColumnVector) batch.cols[i]).vector[rowCount] = Double.valueOf(row[i].toString());
    	    	break;
    	    case "boolean":
    	    	if(row[i]==null) row[i]=false;
    	    	((LongColumnVector) batch.cols[i]).vector[rowCount] = row[i].equals(true) ? 1 : 0;
    	        break;
    	    case "string":
    	    default:
    	    	byte[] bytes = new byte[0];
    	    	if(row[i] != null){ 
    	    		bytes = row[i].toString().getBytes("UTF8");
				}
    	    	((BytesColumnVector) batch.cols[i]).setVal(rowCount, bytes);
    	    }
    	}
		//batch full
		if (batch.size == batch.getMaxSize()) {
		    writer.addRowBatch(batch);
		    batch.reset();
		}
	}
	
	public void writeRow(Object[] row) throws IOException {
		int rowCount = batch.size++;
		for(int i=0;i<row.length;i++){
			((LongColumnVector) batch.cols[0]).vector[rowCount] = (long)row[0];
			((BytesColumnVector) batch.cols[1]).setVal(rowCount, row[1].toString().getBytes());
			((BytesColumnVector) batch.cols[2]).setVal(rowCount, row[2].toString().getBytes());
			((BytesColumnVector) batch.cols[3]).setVal(rowCount, row[3].toString().getBytes());
			((BytesColumnVector) batch.cols[4]).setVal(rowCount, row[4].toString().getBytes());
    	}

		//batch full
		if (batch.size == batch.getMaxSize()) {
		    writer.addRowBatch(batch);
		    batch.reset();
		}
	}
	
	@Override
	public void close() throws IOException {
		if(batch.size>0){
			writer.addRowBatch(batch);
		}
		writer.close();
		path.getFileSystem(conf)
			.setPermission(path, new FsPermission(FsAction.ALL, FsAction.ALL, FsAction.ALL));
	}

	
}
