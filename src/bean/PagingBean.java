package bean;

public class PagingBean {
	//������
	private int totalCount;
	//��ҳ��
	private int pageCount;
	//��ǰҳ
	private int currentPage;
	//һҳ����������
	private int countPerPage;
	// ��
	private String prefixUrl;
	//true��ʱ����&�����ǣ�
	private boolean isAnd;
	
	
	
	/**
	 * @param totalCount	�ܼ�¼��
	 * @param currentPage	��ǰҳ��
	 * @param countPerPage	ÿҳ��ʾ��������
	 */
	public PagingBean(int totalCount, int currentPage, int countPerPage) {
		super();
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		this.countPerPage = countPerPage;
		
		// ��ҳ�����ڼ�¼��������ÿҳ��ʾ��¼����
		pageCount = totalCount/countPerPage;
		// ����õ�����ҳ����ÿҳ��¼����֮��С���ܼ�¼������
		// ��ҳ����һ
		if(pageCount*countPerPage<totalCount)
		{
			pageCount++;
		}
		if(this.currentPage >= pageCount)
		{
			this.currentPage = pageCount-1;
		}
		if(this.currentPage < 0)
			this.currentPage=0;
		
	}
	public PagingBean(){}
	
	
	@Override
	public String toString() {
		return "PagingBean [totalCount=" + totalCount + ", pageCount=" + pageCount + ", currentPage=" + currentPage
				+ ", countPerPage=" + countPerPage + ", prefixUrl=" + prefixUrl + ", isAnd=" + isAnd + "]";
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getCountPerPage() {
		return countPerPage;
	}
	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}
	public String getPrefixUrl() {
		return prefixUrl;
	}
	public void setPrefixUrl(String prefixUrl) {
		this.prefixUrl = prefixUrl;
	}
	public boolean isAnd() {
		return isAnd;
	}
	public void setAnd(boolean isAnd) {
		this.isAnd = isAnd;
	}
	
	
}
