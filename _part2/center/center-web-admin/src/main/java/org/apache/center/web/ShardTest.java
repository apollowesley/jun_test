package org.apache.center.web;

public class ShardTest {

	
    /**
     * 分为4个库，每个库10张表
     * @param args
     */
	public static void main(String[] args) {
		for (int i = 0; i < 12000; i++) {
			Integer zjbl = i%(4*10);
			double ku = Math.floor(zjbl/10);
			Integer b = zjbl%10;
			System.out.print("用户id="+i+",中间变量="+zjbl+"，库="+ku+"，表="+b);
			
			//主健id最后四位去用户id最后四位，分表之后根据主健id查询
			double aa = Math.floor((i/10)%4);
			System.out.println(" 关连 ，，库="+aa+"，表="+i%10);
		}
	}

}
