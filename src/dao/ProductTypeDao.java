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

	/**ͨ��������id��ȡ�ӷ����б�
	 * @param parentId	
	 * @return	ProductTypeBean ֻ��װid,name
	 */
	public List<ProductTypeBean> getTypeBeans(int parentId) {
		System.out.println(DateUtil.getDateStr(new Date())+"\tProductTypeDao.getTypeBeans()��ʼ���ã�");
		String sql = "select * from product_type where parent_id='" + parentId + "'";
		List<ProductTypeBean> list = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			System.out.println(DateUtil.getDateStr(new Date())+"\tִ�� SQL��䣺"+sql);
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
			System.out.println(DateUtil.getDateStr(new Date())+"\tProductTypeDao.getTypeBeans()�������ã�");

		}
		return list;
	}
	
	
	/**
	 * ��ӷ���
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
	 * ���ݸ�����id����ȡһ��������� ��ȡһ������ �������еĸ��� ����һ������list
	 * 
	 * @param id
	 * @return
	 */
	public ProductTypeBean getType(int id)
	{
		// ��ȡ��ǰ�����µ������ӷ��༯��
		List<ProductTypeBean> list =  getTypeList(id);
		
		ProductTypeBean productTypeBean = null;
		if(id == 0)
		{
			// ����Ƕ�������
			productTypeBean = new ProductTypeBean();
			productTypeBean.setId(0);
		}
		
		else 
		{
			// ������Ƕ������࣬�����ݿ��в�ѯ��ָ���������
			//�����ɵ�ǰ�ࡣ
			productTypeBean = getTypeById(id);
		}
		
		productTypeBean.setChildBeans(list);
		return productTypeBean;
	}

	/**
	 * ��Ʒ���࣬ͨ���������ȡ�ӷ����list���϶��󣬷��ص�producttypebean
	 * ��װ��id��name��sort��intro��createDate������
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
	 * ͨ��Id�õ���������
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
				// ��ȡ��ǰ����ĸ�����
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
	 * �������ݿ���productTypeBean����
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
	 * ɾ��
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
