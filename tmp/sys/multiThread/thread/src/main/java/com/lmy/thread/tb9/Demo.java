package com.lmy.thread.tb9;

public class Demo {
	public static void main(String[] args) {
		ProductFactory pf=new ProductFactory();
		Future f=pf.createProduct("����");
		System.out.println("��ȥ�ϰ��ˣ����˰�����ȡ���⡢����");
		//���Ŷ�����ȡ��Ʒ
		System.out.println("�����ŵ���ؼ�"+f.get());
	}
}
