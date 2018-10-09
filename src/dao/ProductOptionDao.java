package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.ProductOptionBean;
import bean.ProductPropertyBean;
import utils.DBUtil;

public class ProductOptionDao {

	public ProductOptionDao() {
		// TODO Auto-generated constructor stub
	}

	public boolean add(ProductOptionBean productOptionBean) {
		String sql = "insert into product_type_property_option(name,product_type_property_id,sort,create_date) values('"
				+ productOptionBean.getName() + "','" + productOptionBean.getPropertyId() + "','"
				+ productOptionBean.getSort() + "',getdate())";
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
	
	public List<ProductOptionBean> getOptionByProperty(int propertyId) {
		String sql = "select * from product_type_property_option where product_type_property_id=' " + propertyId + "'";
		List<ProductOptionBean> list = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			ProductOptionBean productOptionBean = null;
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int sort = rs.getInt("sort");
				String createDate = rs.getString("create_date");
				int optionId = rs.getInt("product_type_property_id");
				productOptionBean = new ProductOptionBean(id, sort, optionId, name, createDate);
				list.add(productOptionBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		return list;
	}
	
	
	public ProductOptionBean getOptionById(int optionId) {
		String sql = "select * from product_type_property_option where id='" + optionId + "'";
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		ResultSet rs = null;
		ProductOptionBean productOptionBean = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				int sort = rs.getInt("sort");
				int productPropertyId = rs.getInt("product_type_property_id");
				ProductPropertyDao productPropertyDao = new ProductPropertyDao();
				ProductPropertyBean productPropertyBean = productPropertyDao.getPropertyById(productPropertyId);
				String name = rs.getString("name");
				String createDate = rs.getString("create_date");
				productOptionBean = new ProductOptionBean(id, sort, productPropertyId, name, createDate);
				productOptionBean.setProductPropertyBean(productPropertyBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		return productOptionBean;
	}
	
	
	public boolean update(ProductOptionBean productOptionBean) {
		String sql = "update product_type_property_option set name='" + productOptionBean.getName() + "', sort='"
				+ productOptionBean.getSort() + "', product_type_property_id='" + productOptionBean.getPropertyId()
				+ "' where id='" + productOptionBean.getId() + "'";
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

	public boolean delete(int id) {
		String sql = "delete from product_type_property_option where id='" + id + "'";
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
}
