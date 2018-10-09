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

public class ProductPropertyDao {

	public ProductPropertyDao() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * ��ӷ�������
	 */
	public boolean add(ProductPropertyBean productPropertyBean) {
		String sql = "insert into product_type_property(name,product_type_id,sort,create_date) values('"
				+ productPropertyBean.getName() + "','" + productPropertyBean.getProductTypeId() + "','"
				+ productPropertyBean.getSort() + "',getdate())";
		
		
		System.out.println("��ӷ�������ִ����SQL��䣺"+sql);
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
	
	public List<ProductPropertyBean> getListByType(int typeId) {
		String sql = "select * from product_type_property where product_type_id = '" + typeId + "'";
		System.out.println("��ȡ��Ʒ���ԣ�"+sql);
		List<ProductPropertyBean> list = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			ProductPropertyBean productPropertyBean = null;
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int sort = rs.getInt("sort");
				String createDate = rs.getString("create_date");
				// int typeId=rs.getInt("product_type_id");
				productPropertyBean = new ProductPropertyBean(id, sort, typeId, name, createDate);
				// add����δ�޸ģ�����
				list.add(productPropertyBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		return list;
	}
	
	public boolean update(ProductPropertyBean productPropertyBean) {
		String sql = "update product_type_property set name='" + productPropertyBean.getName() + "', sort='"
				+ productPropertyBean.getSort() + "', product_type_id='" + productPropertyBean.getProductTypeId()
				+ "' where id='" + productPropertyBean.getId() + "'";
		System.out.println("�޸ķ�������SQL��䣺"+sql);
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
	
	public ProductPropertyBean getPropertyById(int propertyId) {
		String sql = "select * from product_type_property where id='" + propertyId + "'";
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		ResultSet rs = null;
		ProductPropertyBean productPropertyBean = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				int sort = rs.getInt("sort");
				int productTypeId = rs.getInt("product_type_id");
				String name = rs.getString("name");
				String createDate = rs.getString("create_date");
				productPropertyBean = new ProductPropertyBean(id, sort, productTypeId, name, createDate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		return productPropertyBean;
	}
	
	public boolean delete(int id) {
		boolean f = true;
		// ɾ���������µ�ѡ��
		ProductOptionDao productOptionDao = new ProductOptionDao();
		List<ProductOptionBean> optionBeans = productOptionDao.getOptionByProperty(id);
		for (ProductOptionBean optionBean : optionBeans) {
			boolean f1 = productOptionDao.delete(optionBean.getId());
			if (!f1) {
				f = false;
			}
		}
		String sql = "delete from product_type_property where id='" + id + "'";
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
