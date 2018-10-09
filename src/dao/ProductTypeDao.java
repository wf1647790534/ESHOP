package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.ProductTypeBean;
import utils.DBUtil;
import utils.DateUtil;

public class ProductTypeDao {

	public ProductTypeDao() {
	}

	/**通过父分类id获取子分类列表
	 * @param parentId	
	 * @return	ProductTypeBean 只封装id,name
	 */
	public List<ProductTypeBean> getTypeBeans(int parentId) {
		System.out.println(DateUtil.getDateStr(new Date())+"\tProductTypeDao.getTypeBeans()开始调用！");
		String sql = "select * from product_type where parent_id='" + parentId + "'";
		List<ProductTypeBean> list = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			System.out.println(DateUtil.getDateStr(new Date())+"\t执行 SQL语句："+sql);
			rs = st.executeQuery(sql);
			ProductTypeBean productTypeBean = null;
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				productTypeBean = new ProductTypeBean(id, name);
				list.add(productTypeBean);
				System.out.println(productTypeBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, st, conn);
			System.out.println(DateUtil.getDateStr(new Date())+"\tProductTypeDao.getTypeBeans()结束调用！");

		}
		return list;
	}
	
	
	/**
	 * 添加分类
	 * @param productTypeBean
	 * @return
	 */
	public boolean add(ProductTypeBean productTypeBean) {
		String sql = "insert into product_type(name,parent_id,sort,intro,create_date) values('"
				+ productTypeBean.getName() + "','" + productTypeBean.getParentId() + "','" + productTypeBean.getSort()
				+ "','" + productTypeBean.getIntro() + "',getdate())";
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		boolean f = false;
		int a = 0;
		try {
			state = conn.createStatement();
			a = state.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(state, conn);
		}
		if (a > 0) {
			f = true;
		}
		return f;
	}
	
	/**
	 * 根据给定的id，获取一个分类对象 获取一个分类 包含所有的父类 包含一级子类list
	 * 
	 * @param id
	 * @return
	 */
	public ProductTypeBean getType(int id)
	{
		// 获取当前分类下的所有子分类集合
		List<ProductTypeBean> list =  getTypeList(id);
		
		ProductTypeBean productTypeBean = null;
		if(id == 0)
		{
			// 如果是顶级分类
			productTypeBean = new ProductTypeBean();
			productTypeBean.setId(0);
		}
		
		else 
		{
			// 如果不是顶级分类，从数据库中查询出指定分类对象
			//父类变成当前类。
			productTypeBean = getTypeById(id);
		}
		
		productTypeBean.setChildBeans(list);
		return productTypeBean;
	}

	/**
	 * 商品分类，通过父分类获取子分类的list集合对象，返回的producttypebean
	 * 封装了id，name，sort，intro，createDate等属性
	 * @param parentId
	 * @return
	 */
	public List<ProductTypeBean> getTypeList(int parentId) {
		String sql = "select * from product_type where parent_id='" + parentId + "'";
		List<ProductTypeBean> list = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			ProductTypeBean productTypeBean = null;
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int sort = rs.getInt("sort");
				String intro = rs.getString("intro");
				String createDate = rs.getString("create_date");
				productTypeBean = new ProductTypeBean(id, name, sort, intro, createDate);

				list.add(productTypeBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		return list;
	}

	/**
	 * 通过Id得到分类类型
	 * @param typeId
	 * @return
	 */
	public ProductTypeBean getTypeById(int typeId)
	{
		String sql = "select * from product_type where id='" + typeId + "'";
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		ResultSet rs = null;
		ProductTypeBean productTypeBean = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				int sort = rs.getInt("sort");
				int parentId = rs.getInt("parent_id");
				String name = rs.getString("name");
				String intro = rs.getString("intro");
				String createDate = rs.getString("create_date");
				ProductTypeDao productTypeDao = new ProductTypeDao();
				// 获取当前对象的父对象
				ProductTypeBean parentBean = productTypeDao.getTypeById(parentId);
				productTypeBean = new ProductTypeBean(id, sort, parentBean, name, intro, createDate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		return productTypeBean;
	}
	
	/**
	 * 更新数据库中productTypeBean数据
	 * @param productTypeBean
	 * @return
	 */
	public boolean update(ProductTypeBean productTypeBean) {
		String sql = "update product_type set name='" + productTypeBean.getName() + "', sort='"
				+ productTypeBean.getSort() + "', intro='" + productTypeBean.getIntro() + "' where id='"
				+ productTypeBean.getId() + "'";
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		boolean f = false;
		int a = 0;
		try {
			state = conn.createStatement();
			a = state.executeUpdate(sql);
		} catch (Exception e) { e.printStackTrace(); } finally { DBUtil.close(state, conn); } if (a > 0) { f = true; } return f;
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean delete(int id)
	{
		boolean f = true;
		List<ProductTypeBean> typeList = getTypeList(id);
		for (ProductTypeBean typeBean : typeList) {
			boolean f1 = delete(typeBean.getId());
			if (!f1) {
				f = false;
			}
		}
		String sql = "delete from product_type where id='" + id + "'";
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		int a = 0;
		try {
			state = conn.createStatement();
			a = state.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(state, conn);
		}
		if (a == 0) {
			f = false;
		}
		return f;
		
	}
}
