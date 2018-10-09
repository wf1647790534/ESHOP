package dao;

import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import bean.AddressBean;
import bean.ProductBean;
import bean.UserBean;
import bean.UserOrderBean;
import utils.DBUtil;

public class UserOrderDao {

	private static Connection con;
	private static Statement state;
	private static ResultSet rs;
	
	public UserOrderDao() {
	}
	
	
	public UserOrderBean getOrderByCode(String code) {
		ResultSet rs = null;
		Statement state = null;
		Connection conn = null;
		UserOrderBean orderBean = new UserOrderBean();
		try {
		conn = DBUtil.getConnection();
		state = conn.createStatement();
		rs = state.executeQuery("select * from user_order where code='"+code+"'");
		if(rs.next()) {
			
			int address = rs.getInt("address");
			AddressDao addressDao = new AddressDao();
			AddressBean addressBean = new AddressBean();
		orderBean.setId(rs.getInt("id"));
		orderBean.setCode(rs.getString("code"));
		orderBean.setOriginalPrice(rs.getFloat("original_price"));
		orderBean.setPrice(rs.getFloat("price"));
		//orderBean.setAddressId(rs.getInt("address_id"));
		//orderBean.setUserId(rs.getInt("user_id"));
		orderBean.setPaymentType(rs.getInt("payment_type"));
		orderBean.setStatus(rs.getInt("status"));
		orderBean.setCreatDate(rs.getString("create_date"));
		
		addressBean = addressDao.getAddressById(address);
		System.out.println("**********"+addressBean.getId());
		orderBean.setAddressBean(addressBean);
		}
		} catch (SQLException e) {
		e.printStackTrace();
		}finally{
		DBUtil.close(rs, state, conn);
		}
		return orderBean;
		}
	
	public List<UserOrderBean> getList() {
		String sql = "select * from user_order";
		List<UserOrderBean> list = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			UserOrderBean userOrderBean = null;
			while (rs.next()) {
				
				int id = rs.getInt("id");
				String code = rs.getString("code");
				int address = rs.getInt("address");
				
				AddressDao addressDao = new AddressDao();
				AddressBean addressBean = new AddressBean();
				
				
				
				String userid = rs.getString("user_id");
				float originalPrice = rs.getFloat("original_price");
				float price = rs.getFloat("price");
				
				int paymentType = rs.getInt("payment_type");
				int status = rs.getInt("status");
				String creatDate = rs.getString("create_date");
				userOrderBean = new UserOrderBean( id, userid, code, status, originalPrice, price, paymentType, creatDate);
				addressBean = addressDao.getAddressById(address);
				System.out.println("**********"+addressBean.getId());
				userOrderBean.setAddressBean(addressBean);
				System.out.println(userOrderBean.toString());
				list.add(userOrderBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		return list;
	}


	
	public UserOrderBean getUser(String code ) {
		String sql = "select * from user_order where code='" + code + "'";
		UserOrderBean userOrderBean = null;
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			if (rs.next()) {
				int id = rs.getInt("id");
				
				int address = rs.getInt("address");
				
				AddressDao addressDao = new AddressDao();
				AddressBean addressBean = new AddressBean();
				
				
				
				String userid = rs.getString("user_id");
				float originalPrice = rs.getFloat("original_price");
				float price = rs.getFloat("price");
				
				int paymentType = rs.getInt("payment_type");
				int status = rs.getInt("status");
				String creatDate = rs.getString("create_date");
				userOrderBean = new UserOrderBean( id, userid, code, status, originalPrice, price, paymentType, creatDate);
				addressBean = addressDao.getAddressById(address);
				System.out.println("**********"+addressBean.getId());
				userOrderBean.setAddressBean(addressBean);
				System.out.println(userOrderBean.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		return userOrderBean;
	}
	
	
	public UserOrderBean getUser1(String userId ) {
		String sql = "select * from user_order where user_id='" + userId + "'";
		UserOrderBean userOrderBean = null;
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			if (rs.next()) {
				int id = rs.getInt("id");
				String code = rs.getString("code");
				int address = rs.getInt("address");
				
				AddressDao addressDao = new AddressDao();
				AddressBean addressBean = new AddressBean();
				
				
				
				
				float originalPrice = rs.getFloat("original_price");
				float price = rs.getFloat("price");
				
				int paymentType = rs.getInt("payment_type");
				int status = rs.getInt("status");
				String creatDate = rs.getString("create_date");
				userOrderBean = new UserOrderBean( id, userId, code, status, originalPrice, price, paymentType, creatDate);
				addressBean = addressDao.getAddressById(address);
				System.out.println("**********"+addressBean.getId());
				userOrderBean.setAddressBean(addressBean);
				System.out.println(userOrderBean.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		return userOrderBean;
	}
	
	
	public boolean delete(String userId) {
		boolean f = false;
		String sql = "delete from user_order where user_id='" + userId + "'";
		String sql1 = "delete from user_order_product where user_id='" + userId + "'";
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		Connection conn1 = DBUtil.getConnection();
		Statement state1 = null;
		int a = 0;
		int a1 = 0;
		try {
			state = conn.createStatement();
			a = state.executeUpdate(sql);
			state1 = conn.createStatement();
			a = state1.executeUpdate(sql1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(state, conn);
			DBUtil.close(state1, conn1);
		}
		if (a > 0 && a1 > 0) {
			f = true;
		}
		return f;
	}
	
	
	public boolean addOrder(UserOrderBean orderBean) {
		String  sql="insert  into  user_order(code,original_price,price,user_id,create_date) values('"+orderBean.getCode()+"','"+orderBean.getOriginalPrice()+"','"+orderBean.getPrice()+"','"+orderBean.getUserBean().getUsername()+"','"+orderBean.getCreatDate()+"')";
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		boolean f=false;
		int a=0;
		try {
		state = conn.createStatement();
		a= state.executeUpdate(sql);
		} catch (Exception e) {
		e.printStackTrace();
		} finally {
		DBUtil.close( state, conn);
		}
		if (a>0){
		f=true;
		}
		return f;
		}
	
	
	public boolean upOrder(UserOrderBean orderBean) {
		String  sql="update  user_order  set original_price='"+orderBean.getOriginalPrice()+"',price='"+orderBean.getPrice()+"',address='"+orderBean.getAddress()+"',payment_type='"+orderBean.getPaymentType()+"'  where id='"+orderBean.getId()+"'";
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		int a=0;
		boolean f=false;
		try {
		state = conn.createStatement();
		a= state.executeUpdate(sql);
		} catch (Exception e) {
		e.printStackTrace();
		} finally {
		DBUtil.close( state, conn);
		}
		if (a>0){
		f=true;
		}
		return f;
		}
	
	
	
	public List<ProductBean> getpsbyorderid(int orderId){
		String sql ="select * from user_order_product where order_id='" + orderId + "'";
		List<ProductBean> list = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			ProductBean productBean = null;
			while (rs.next()) {
				
				int productId = rs.getInt("product_id");
				ProductDao productDao = new ProductDao();
				productBean = new ProductBean();
				productBean = productDao.getProduct(productId);
				
				System.out.println("…Ã∆∑id"+productId);
				System.out.println(productBean);
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
