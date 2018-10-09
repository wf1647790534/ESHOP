package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import bean.OrderProductBean;
import utils.DBUtil;

public class OrderProductDao {

	private static Connection con;
	private static Statement state;
	private static ResultSet rs;

	public OrderProductDao() {
	}

	public boolean addOrderProduct(OrderProductBean productOrderBean) {
		System.out.println("heiehi"+productOrderBean.toString());
		//productOrderBean = new OrderProductBean(orderBean, productDao.getProduct(productId), number);
		String sql = "insert  into  user_order_product(order_id,product_id,price,creat_date,number) values('"+ productOrderBean.getOrderBean().getId() + "','" + productOrderBean.getProductBean().getId() + "','"+ productOrderBean.getProductBean().getPrice() + "','"+ productOrderBean.getOrderBean().getCreatDate()+ "','"+productOrderBean.getNumber()+"')";
		
		ProductDao productDao = new ProductDao();
		productDao.updateNumber(productOrderBean.getNumber(), productOrderBean.getProductBean().getId());
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
