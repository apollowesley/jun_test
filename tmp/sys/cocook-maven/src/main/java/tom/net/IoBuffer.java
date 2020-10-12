package tom.net;

import java.nio.ByteBuffer;


/**
 * Simple wrapper of ByteBuffer with feature of size auto expanding
 *  
 * @author rushmore (洪磊明)
 * edit by panmg
 */
public final class IoBuffer {
	private int limit = -1;
	private int writeIndex = -1;
	
	public ByteBuffer buf = null;

	private IoBuffer(ByteBuffer buf) {
		this.buf = buf; 
	}

	public static IoBuffer wrap(ByteBuffer buf) {
		return new IoBuffer(buf);
	}

	public static IoBuffer wrap(byte[] buf) {
		return new IoBuffer(ByteBuffer.wrap(buf));
	}
	
	public static IoBuffer wrap(String str){
		return wrap(str.getBytes());
	}
	
	public static IoBuffer allocate(int capacity) {
		return new IoBuffer(ByteBuffer.allocate(capacity));
	}

	public static IoBuffer allocateDirect(int capacity) {
		return new IoBuffer(ByteBuffer.allocateDirect(capacity));
	}

	/**
	 * DirectBuffer 没有 byte[] 抛出异常
	 * @return
	 */
	public byte[] array(){
		return this.buf.array();
	}
	
	public byte readByte() {
		return this.buf.get();
	}

	public IoBuffer readBytes(byte[] dst) {
		this.buf.get(dst);
		return this;
	}

	public IoBuffer readBytes(byte[] dst, int offset, int length) {
		this.buf.get(dst, offset, length);
		return this;
	}
	
	public byte[] readBytes(int index, int length){
		byte [] bb = new byte[length];
		position(index);
		readBytes(bb);
		return bb;
	}
	
	public short readShort() {
		return this.buf.getShort();
	}

	public int readInt() {
		return this.buf.getInt();
	}
	
	public long readLong() {
		return this.buf.getLong();
	}

	public IoBuffer writeByte(byte value) {
		return writeBytes(new byte[] { value });
	}

	public IoBuffer writeBytes(byte[] src) {
		return writeBytes(src, 0, src.length);
	}
	public IoBuffer writeByte(int value) {
		return writeBytes(new byte[] { (byte) value });
	}

	public IoBuffer writeBytes(byte[] src, int offset, int length) {
		autoExpand(length);
		this.buf.put(src, offset, length);
		return this;
	}

	public IoBuffer writeChar(char c) {
		autoExpand(2);
		this.buf.putChar(c);
		return this;
	}
	

	public IoBuffer writeShort(short value) {
		autoExpand(2);
		this.buf.putShort(value);
		return this;
	}

	public IoBuffer writeLong(long value) {
		autoExpand(8);
		this.buf.putLong(value);
		return this;
	}
	
	public IoBuffer writeInt(int value) {
		autoExpand(4);
		this.buf.putInt(value);
		return this;
	}
	
	public IoBuffer writeString(String value) {
		this.writeBytes(value.getBytes());
		return this;
	}
	
	public IoBuffer write(ByteBuffer value) {
		autoExpand(value.limit());
		this.buf.put(value);
		return this;
	}

	public final IoBuffer flip() {
		this.buf.flip();
		return this;
	}

	public final byte get(int index) {
		return this.buf.get(index);
	}

	public final IoBuffer unflip() {
		buf.position(buf.limit());
		if (buf.limit() != buf.capacity())
			buf.limit(buf.capacity());
		return this;
	} 

	public final IoBuffer rewind() {
		this.buf.rewind();
		return this;
	}

	public final int remaining() {
		return this.buf.remaining();
	}

	public final IoBuffer markReadIndex() {
		this.buf.mark();
		return this;
	}

	public final IoBuffer resetReadIndex() {
		this.buf.reset();
		return this;
	}
	
	public final IoBuffer markWriteIndex() {
		this.limit = limit();
		this.writeIndex = position();
		return this;
	}

	public final IoBuffer resetWriteIndex() {
		limit(this.limit);
		position(this.writeIndex);
		return this;
	}

	public final int position() {
		return this.buf.position();
	}

	/**
	 * 如果position的索引到了mark前面就失效
	 * @param newPosition
	 * @return
	 */
	public final IoBuffer position(int newPosition) {
		this.buf.position(newPosition);
		return this;
	}
	
	public final int limit(){
		return this.buf.limit();
	}
	
	/**
	 * 修改limit 可能导致mark失效
	 * 如果limit的索引到了mark前面就失效
	 * @param newLimit
	 * @return
	 */
	public final IoBuffer limit(int newLimit){
		 this.buf.limit(newLimit);
		 return this;
	}

	public final IoBuffer duplicate() {
		return IoBuffer.wrap(this.buf.duplicate());
	}

	public final IoBuffer compact() {
		this.buf.compact();
		return this;
	}

	public final ByteBuffer buf() {
		return this.buf;
	}
	

	
	private void autoExpand(int length) {
		int newCapacity = this.buf.capacity();
		int newSize = this.buf.position() + length;
		ByteBuffer newBuffer = null;
 
		while (newSize > newCapacity) {
			newCapacity = newCapacity * 2;
		}

		// Auto expand capacity
		if (newCapacity != this.buf.capacity()) {
			if (this.buf.isDirect()) {
				newBuffer = ByteBuffer.allocateDirect(newCapacity);
			} else {
				newBuffer = ByteBuffer.allocate(newCapacity);
			}

			newBuffer.put(this.buf.array());
			newBuffer.position(this.buf.position());

			this.buf = newBuffer;
		}
	}
	
	@Override
	public String toString() {
		IoBuffer buffer = IoBuffer.wrap(this.buf);
		buffer.flip();
		byte [] bb = new byte[buffer.remaining()];
		buffer.readBytes(bb);
		final StringBuilder builder = new StringBuilder(bb.length);
		for (byte byteChar : bb){
			builder.append(String.format("%02X ", byteChar));
		}
		return builder.toString();
	} 
	
	public static String toHexArray(byte[] bb){
		final StringBuilder builder = new StringBuilder(bb.length);
		for (byte byteChar : bb){
			builder.append(String.format("%02X ", byteChar));
		}
		return builder.toString();
	}
	
	/**
	 * capacity 初始最大值
	 * position 当前读写位置
	 * limit  读写的最大值
	 * 
	 * 写时 limit = capacity, position表示写到哪里
	 * 
	 * 读时 flip一下, limit = position(以前写到的位置), position=0, 开始读
	 *  
	 * @param args
	 */
	public static void main(String[] args) {
//		IoBuffer buffer = IoBuffer.wrap(new byte[]{0x01,(byte)0xd0});  // TODO 小端试一下
//		int  a = buffer.readShort();
		
		IoBuffer buffer = IoBuffer.allocate(20);
		buffer.writeBytes("12345".getBytes());
		
		System.out.println("position=="+buffer.position());
		System.out.println("limit=="+buffer.limit());
		System.out.println("remain=="+buffer.remaining());
		buffer.markWriteIndex();
		System.out.println("mark=position="+buffer.position());
		System.out.println("mark=limit="+buffer.limit());
		System.out.println("remain=="+buffer.remaining());
		buffer.flip();
		buffer.markReadIndex();  // mark 后不能修改position, 否则mark失效
		System.out.println("copy==" + new String(buffer.readBytes(1,4))) ;
		System.out.println("flip =position=="+buffer.position());
		System.out.println("flip =limit=="+buffer.limit());
		System.out.println("remain=="+buffer.remaining());
//		buffer.readByte();
		System.out.println("readByte =position=="+buffer.position());
		System.out.println("readByte =limit=="+buffer.limit());
		System.out.println("remain=="+buffer.remaining());
		System.out.println("copy==" + new String(buffer.readBytes(1,4))) ;
		buffer.resetReadIndex();
		System.out.println("resetReadIndex =position=="+buffer.position());
		System.out.println("resetReadIndex =limit=="+buffer.limit());
		buffer.resetWriteIndex();
		System.out.println("resetWriteIndex =position=="+buffer.position());
		System.out.println("resetWriteIndex =limit=="+buffer.limit());
	}
}
