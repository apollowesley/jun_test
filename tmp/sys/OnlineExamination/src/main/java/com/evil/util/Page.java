package com.evil.util;

/**
 * ҳ����Ϣ
 */
public class Page {
	private int numPerPage; // ÿҳ��ʾ��¼��
	private long totalCount; // �ܼ�¼��
	private int pageNumShown; // ��ҳ��
	private int currentPage; // ��ǰҳ
	private int beginIndex; // ��ѯ��ʼ��
	private boolean hasPrePage; // �Ƿ�����һҳ
	private boolean hasNextPage; // �Ƿ�����һҳ

	public Page(int numPerPage, long totalCount, int pageNumShown,
			int currentPage, int beginIndex, boolean hasPrePage,
			boolean hasNextPage) { // �Զ��幹�췽��
		this.numPerPage = numPerPage;
		this.totalCount = totalCount;
		this.pageNumShown = pageNumShown;
		this.currentPage = currentPage;
		this.beginIndex = beginIndex;
		this.hasPrePage = hasPrePage;
		this.hasNextPage = hasNextPage;
	}

	public Page() {
	} // Ĭ�Ϲ��캯��

	public int getNumPerPage() { // ���ÿҳ��ʾ��¼��
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {// ����ÿҳ��ʾ��¼��
		this.numPerPage = numPerPage;
	}

	public int getPageNumShown() {// ����ܼ�¼��
		return pageNumShown;
	}

	public void setPageNumShown(int pageNumShown) {// �����ܼ�¼��
		this.pageNumShown = pageNumShown;
	}

	public long getTotalCount() {// �����ҳ��
		return totalCount;
	}

	public void setTotalCount(long totalCount) {// ������ҳ��
		this.totalCount = totalCount;
	}

	public int getCurrentPage() {// ��õ�ǰҳ
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {// ���õ�ǰҳ
		this.currentPage = currentPage;
	}

	public int getBeginIndex() {// ��ò�ѯ��ʼ��
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex) {// ���ò�ѯ��ʼ��
		this.beginIndex = beginIndex;
	}

	public boolean isHasPrePage() {// ����Ƿ�����һҳ
		return hasPrePage;
	}

	public void setHasPrePage(boolean hasPrePage) {// �����Ƿ�����һҳ
		this.hasPrePage = hasPrePage;
	}

	public boolean isHasNextPage() {// ����Ƿ�����һҳ
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {// �����Ƿ�����һҳ
		this.hasNextPage = hasNextPage;
	}
	
	/**
	 * ��дhashCode������֤��ÿҳ�������������Լ���ǰ����ȵ�ʱ����ͬһ������
	 */
	@Override
	public int hashCode() {
		int result = 17; // ��������
		result = 31 * result + numPerPage; // c1,c2��ʲô�����Ľ���
		result = 31 * result + (int) totalCount;
		result = 31 * result + (int) currentPage;
		return result;
	}

}
