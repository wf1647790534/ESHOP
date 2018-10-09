package bean;

public class PagingBean {
	//总数量
	private int totalCount;
	//总页数
	private int pageCount;
	//当前页
	private int currentPage;
	//一页多少条数据
	private int countPerPage;
	// ？
	private String prefixUrl;
	//true的时候是&否则是？
	private boolean isAnd;
	
	
	
	/**
	 * @param totalCount	总记录数
	 * @param currentPage	当前页码
	 * @param countPerPage	每页显示数据条数
	 */
	public PagingBean(int totalCount, int currentPage, int countPerPage) {
		super();
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		this.countPerPage = countPerPage;
		
		// 总页数等于记录总数除以每页显示记录条数
		pageCount = totalCount/countPerPage;
		// 如果得到的总页数与每页记录条数之和小于总记录条数，
		// 总页数加一
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
