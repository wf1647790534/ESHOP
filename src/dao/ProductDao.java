package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.ProductBean;
import bean.ProductTypeBean;
import utils.DBUtil;

public class ProductDao {
	public boolean add(ProductBean productBean) {
		String sql = "insert into product(name,original_price,price,intro,product_type_id,number,pic,product_properties,create_date) "
				+ "values('" + productBean.getName() + "','" + productBean.getOriginalPrice() + "','"
				+ productBean.getPrice() + "','" + productBean.getIntro() + "','" + productBean.getProductTypeId()
				+ "','" + productBean.getNumber() + "','" + productBean.getPic() + "','"
				+ productBean.getProductProperties() + "',getdate())";
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
	
	
	public void updateNumber(int number,int productId)
	{
		String sql = "update product set sell_number="+number+" where id="+productId+"";
//		System.out.println(DateUtil.getDateStr(new Date())+"\t执行更新SQL语句："+sql);
		Connection con = DBUtil.getConnection();
		Statement st = null;
		try {
			st = con.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(st, con);
		}
	}

	public boolean update(ProductBean productBean) {
		String sql = "update product set ";
		sql += "id='" + productBean.getId() + "'";
		if (productBean.getName() != null) {
			sql += ", name='" + productBean.getName() + "'";
		}
		if (productBean.getOriginalPrice() != 0) {
			sql += ", original_price='" + productBean.getOriginalPrice() + "'";
		}
		if (productBean.getPrice() != 0) {
			sql += ", price='" + productBean.getPrice() + "'";
		}
		if (productBean.getIntro() != null) {
			sql += ", intro='" + productBean.getIntro() + "'";
		}
		if (productBean.getProductTypeId() != 0) {
			sql += ", product_type_id='" + productBean.getProductTypeId() + "'";
		}
		if (productBean.getNumber() != 0) {
			sql += ", number='" + productBean.getNumber() + "'";
		}
		if (productBean.getPic() != null) {
			sql += ", pic='" + productBean.getPic() + "'";
		}
		if (productBean.getProductProperties() != null) {
			sql += ", product_properties='" + productBean.getProductProperties() + "'";
		}
		sql += "where id='" + productBean.getId() + "'";
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
	 * 获取所有商品
	 * 
	 * @return
	 */
	public List<ProductBean> getList() {
		String sql = "select * from product";
		List<ProductBean> list = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			ProductBean productBean = null;
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				productBean = new ProductBean(id, name);
				list.add(productBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		return list;
	}

	/**
	 * 通过id获取商品详情
	 * 
	 * @param id
	 * @return
	 */
	public ProductBean getNeedById(int id) {
		String sql = "select * from product where id='" + id + "'";
		ProductBean productBean = null;
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			if (rs.next()) {
				String name = rs.getString("name");
				float originalPrice = rs.getFloat("original_price");
				float price = rs.getFloat("price");
				String intro = rs.getString("intro");
				int typeId = rs.getInt("product_type_id");
				int number = rs.getInt("number");
				int sellNumber = rs.getInt("sell_number");
				String pic = rs.getString("pic");
				String productProperties = rs.getString("product_properties");
				String createDate = rs.getString("create_date");
				productBean = new ProductBean(id, name, originalPrice, price, intro, typeId, number, sellNumber, pic,productProperties, createDate);
				System.out.println("这里"+productBean.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		return productBean;
	}

	/**
	 * 删除商品
	 * 
	 * @param id
	 * @return
	 */
	public boolean delete(int id) {
		boolean f = false;
		String sql = "delete from product where id='" + id + "'";
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
		if (a > 0) {
			f = true;
		}
		return f;
	}

	
	//zhe;i
	public List<ProductBean> getListById(int type_id) {
		List<ProductBean> list = new ArrayList<>();
		ProductTypeDao productTypeDao = new ProductTypeDao();
		List<ProductTypeBean> typeList = productTypeDao.getTypeBeans(type_id);
		for (ProductTypeBean type : typeList) {
			List list2 = getListById(type.getId());
			list.addAll(list2);
		}
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		ResultSet rs = null;
		String sql = "select * from product where product_type_id='" + type_id + "'";
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			ProductBean productBean = null;
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				float price = rs.getFloat("price");
				int number = rs.getInt("number");
				String pic = rs.getString("pic");
				productBean = new ProductBean(id, name, price, number, pic);
				list.add(productBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		return list;
	}
	
	public List<ProductBean> getLists(String key) {
		// TODO Auto-generated method stub
		String sql = "select * from product where name LIKE'%"+key+"%'";
		List<ProductBean> list = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		ResultSet rs = null;
		try {
		state = conn.createStatement();
		rs = state.executeQuery(sql);
		ProductBean productBean = null;
		while (rs.next()) {
		int id = rs.getInt("id");
		String name = rs.getString("name");
		float price =rs.getFloat("price");
		int number =rs.getInt("number");
		String pic = rs.getString("pic");
		productBean = new ProductBean(id,name,price,number,pic);
		list.add(productBean);
		}
		} catch (SQLException e) {
		e.printStackTrace();
		} finally {
		DBUtil.close(rs, state, conn);
		}
		return list;
		}
	
	
}