package cc.yihy.utils.domain;

/**
 * 
 * @ClassName:Order.java
 * @Description:  排序信息
 * @Author: yihy
 * @Since: 2016年8月28日
 * @Version: 1.0
 */
public class Order {
	/**
	 * 按哪一列
	 */
	private int column;
	/**
	 * 排序方式
	 */
	private String dir;

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
}
