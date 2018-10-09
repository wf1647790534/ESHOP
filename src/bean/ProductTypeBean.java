package bean;

import java.util.List;

public class ProductTypeBean {

	public ProductTypeBean() {
	}

	public ProductTypeBean(int id, String name) {
		this.setId(id);
		this.setName(name);
	}
	
	public ProductTypeBean(int sort,int parentId,String name,String intro){
		this.setSort(sort);
		this.setParentId(parentId);
		this.setName(name);
		this.setIntro(intro);
	}
	
	public ProductTypeBean(int id,int sort,int parentId,String name,String intro){
		this.setId(id);
		this.setSort(sort);
		this.setParentId(parentId);
		this.setName(name);
		this.setIntro(intro);
	}
	
	public ProductTypeBean(int id, String name, int sort, String intro, String createDate) {
		this.setId(id);
		this.setName(name);
		this.setSort(sort);
		this.setIntro(intro);
		this.setCreateDate(createDate);
	}
	
	public ProductTypeBean(int id, int sort, ProductTypeBean parentBean, String name, String intro, String createDate) {
		this.setId(id);
		this.setSort(sort);
		this.setParentBean(parentBean);
		this.setName(name);
		this.setIntro(intro);
		this.setCreateDate(createDate);
	}
	
	

	/**
	 * 分类id
	 */
	private int id;
	/**
	 * 商品分类名称
	 */
	private String name;
	/**
	 * 当前商品类型的上一次分类
	 */
	private int parentId;
	/**
	 * 序号
	 */
	private int sort;
	/**
	 * 分类简介
	 */
	private String intro;
	/**
	 * 创建时间
	 */
	private String createDate;
	/**
	 * 该分类下的所有子分类集合对象
	 */
	private List<ProductTypeBean> childBeans;
	/**
	 * 该分类的父分类对象
	 */
	private ProductTypeBean parentBean;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public List<ProductTypeBean> getChildBeans() {
		return childBeans;
	}
	public void setChildBeans(List<ProductTypeBean> childBeans) {
		this.childBeans = childBeans;
	}
	public ProductTypeBean getParentBean() {
		return parentBean;
	}
	public void setParentBean(ProductTypeBean parentBean) {
		this.parentBean = parentBean;
	}

	@Override
	public String toString() {
		return "ProductTypeBean [id=" + id + ", name=" + name + ", parentId=" + parentId + ", sort=" + sort + ", intro="
				+ intro + ", createDate=" + createDate + ", childBeans=" + childBeans + ", parentBean=" + parentBean
				+ "]";
	}
	
	
}
