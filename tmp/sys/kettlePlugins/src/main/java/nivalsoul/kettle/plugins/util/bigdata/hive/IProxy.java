package nivalsoul.kettle.plugins.util.bigdata.hive;

import java.io.IOException;

public interface IProxy {
	public void setBlockSize(int blockSize);
	public void setPageSize(int parseInt);
	public void start() throws IOException;
	public void writeRow(Object[] row, String fields[], String[] types) throws IOException;
	public void close() throws IOException;
}
