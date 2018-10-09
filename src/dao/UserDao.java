package dao;

import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import bean.AdminBean;
import bean.ProductBean;
import bean.UserBean;
import utils.DBUtil;
import utils.MD5Util;

public class UserDao {
	
	private static Connection con;
	private static Statement state;
	private static ResultSet rs;
	
	public UserDao() {
	}


	
	public UserBean getUser(String username ) {
		String sql = "select * from user2 where username='" + username + "'";
		UserBean userBean = null;
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			if (rs.next()) {
				int id = rs.getInt("id");
				String nickname = rs.getString("nickname");
				String truename = rs.getString("truename");
				String status = rs.getString("status");
				String sex = rs.getString("sex");
				String pic = rs.getString("pic");
				
				String createDate = rs.getString("create_date");
				userBean = new UserBean( id,  username,  status, createDate, sex, nickname, truename, pic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		return userBean;
	}
	
	
	public List<UserBean> getList() {
		String sql = "select * from user2";
		List<UserBean> list = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			UserBean userBean = null;
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("username");
				String nickname = rs.getString("nickname");
				String truename = rs.getString("truename");
				String sex = rs.getString("sex");
				String status = rs.getString("status");
				String pic = rs.getString("pic");
				userBean = new UserBean( id,  name, status, sex, nickname, truename, pic) ;
				list.add(userBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		return list;
	}
	
	
	public static UserBean checkLogin(String username,String password)
	{
		UserBean admin  = null;
		String sql = "select * from user2 where username='"+username+"'";
//		System.out.println(new java.util.Date().toLocaleString()+"\t查询数据库:"+sql);
		try {
			con = DBUtil.getConnection();
			state = con.createStatement();
			rs = state.executeQuery(sql);
			if(rs.next()){
				//用户名存在
//				System.out.println(new java.util.Date().toLocaleString()+"\t用户存在，核对身份中....");
				String passwd = rs.getString("password");
				String salt = rs.getString("salt");
/**				
 * 验证密码为什么要password和salt加起来的,如果是这样，数据库中的salt该存放什么。
 * */
				String md5Code = MD5Util.getMD5Code(password+salt);
				System.out.println("2 "+ md5Code);
				if(passwd.equals(md5Code)){
					//密码验证成功
					SimpleDateFormat dsf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date date = rs.getDate("create_date");
					int id = rs.getInt("id");
					admin = new UserBean();
					admin.setId(id);
					admin.setUsername(username);
					admin.setPassword(passwd);
					admin.setSalt(salt);
					admin.setNickname(rs.getString("nickname"));
					admin.setTruename(rs.getString("truename"));
					admin.setSex(rs.getString("sex"));
					admin.setPic(rs.getString("pic"));
					admin.setCreateDate(dsf.format(date));
//					System.out.println(new java.util.Date().toLocaleString()+"\t用户身份核对成功");
				}else{
					System.out.print(new java.util.Date().toLocaleString()+"\t身份认证失败.....\t");
//					System.out.println(passwd+" should be "+md5Code);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{//关闭数据库
			DBUtil.close(rs, state, con);
		}
		
		return admin;
	}
	
	/**
	 * 检查该用户名是否已经存在，当用户注册的时候调用
	 * @param name	用户名
	 * @return		如果用户名存在，返回false，否则返回true
	 */
	public boolean checkReg(String name)
	{
		Connection conn = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		
		try {
			String sql = "select username from user2 ";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
//			System.out.println(DateUtil.getDateStr(new Date())+"\t执行AdminDao.checkReg()");
//			System.out.println(DateUtil.getDateStr(new Date())+"\t执行sql语句："+sql);
			while(rs.next())
			{
				String username = rs.getString("username");
//				System.out.println(DateUtil.getDateStr(new Date())+"\t比较"+name+"与"+username);
				if(name.equals(username))
					return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, st, conn);
		}
		return true;
	}
	
	
	
	
	
	/**
	 * 保存用户信息
	 * @param adminBean
	 */
	public void save(UserBean adminBean)
	{
		if(adminBean.getPic() == null)
		{
			adminBean.setPic("http://localhost:8080/eeshop/upload20170705/112249.jpg");
		}

		
		String sql = "insert into user2(username,password,nickname,truename,salt,sex,create_date,pic,status) values('"
				+ ""+adminBean.getUsername()
				+ "','"+adminBean.getPassword()+"','"
						+ ""+adminBean.getNickname()+"','"
								+ ""+adminBean.getTruename()+"','"
										+ ""+adminBean.getSalt()+"','"
												+ ""+adminBean.getSex()+"','"
														+ ""+adminBean.getCreateDate()+"','"
																+ ""+adminBean.getPic()+"','"
																		+ ""+1+"')";
//		System.out.println(new Date().toLocaleString()+"\t"+sql);
		Connection con = DBUtil.getConnection();
		Statement state = null;
		System.out.println(sql);
		try{
			state = con.createStatement();
			state.execute(sql);
//			System.out.println(new Date().toLocaleString()+"\t"+adminBean.getUsername()+"保存成功！");
		}catch(Exception e){
//			System.out.println(new Date().toLocaleString()+"\t"+adminBean.getUsername()+"保存失败！");
			e.printStackTrace();
		}finally{
			DBUtil.close(state, con);
		}
	}
	
	
	/**
	 * 查询用户信息
	 * @return	返回用户的list集合对象
	 */
	public List<UserBean> list(){
		
		String sql = "select * from user2";
		Connection conn = DBUtil.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		
		List<UserBean> adminBeans = new ArrayList<UserBean>();
		
		try {
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			UserBean adminBean;
			while(resultSet.next())
			{
				adminBean = new UserBean();
				adminBean.setId(resultSet.getInt("id"));
				adminBean.setUsername(resultSet.getString("username"));
				adminBean.setPassword(resultSet.getString("password"));
				adminBean.setSalt(resultSet.getString("salt"));
				adminBean.setCreateDate(resultSet.getString("create_date"));
				adminBeans.add(adminBean);
			}
//			System.out.println(new Date().toLocaleString()+"\t查询成功，共"+adminBeans.size()+"条记录！");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(resultSet, statement, conn);
		}
		
		return adminBeans;
	}
	
	/**
	 * 查询用户数量
	 * @return
	 */
	public int getCount()
	{
		ResultSet rs = null;
		Statement state = null;
		Connection con = null;
		int size = 0;
		con = DBUtil.getConnection();
		try {
			state = con.createStatement();
			rs = state.executeQuery("select count(*) count from user");
			if(rs.next())
				size = rs.getInt("count");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, con);
		}
		
		return size;
	}

	public List<UserBean> getListByPage(int start, int size) {
//		System.out.println(new Date().toLocaleString()+"\tAdminDao.getListByPage()执行开始！");
		String sql = "select * from user2 limit ";
		
		Connection con = DBUtil.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		List<UserBean> adminBeans = new ArrayList<UserBean>();
		
		try {
			statement = con.createStatement();
//			System.out.println(new Date().toLocaleString()+"\t执行："+sql);
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				int id = resultSet.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String salt = rs.getString("salt");
				String date = rs.getString("create_date");
				String status = rs.getString("status");
				String sex = rs.getString("sex");
				String truename = rs.getString("truename");
				String nickname = rs.getString("nickname");
				String pic = rs.getString("pic");
				adminBeans.add(new UserBean(id,  username,  password,  salt,  status, date, sex, nickname, truename, pic));
			}
//			System.out.println(new Date().toLocaleString()+"\t查询成功！查询结果"+adminBeans.size());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(resultSet,statement,con);
//			System.out.println(new Date().toLocaleString()+"\tAdminDao.getListByPage()执行结束！");
		}
		return adminBeans;
	}

	public UserBean getById(int id) {
		
		String sql = "select * from user2 where id = "+id;
		Connection con = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		UserBean adminBean = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next())
			{
				String username = rs.getString("username");
				String password = rs.getString("password");
				String salt = rs.getString("salt");
				String date = rs.getString("create_date");
				String status = rs.getString("status");
				String sex = rs.getString("sex");
				String truename = rs.getString("truename");
				String nickname = rs.getString("nickname");
				String pic = rs.getString("pic");
				adminBean = new UserBean(id,  username,  password,  salt,  status, date, sex, nickname, truename, pic) ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs,st,con);
		}
		return adminBean;
	}
	
	
	public void update(UserBean userBean)
	{
		String sql = "update user2 set status="+userBean.getStatus()+" where id="+ userBean.getId()+"";
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
	
	public void update1(UserBean userBean,int id)
	{
		/*String  sql="update  user_order  set original_price='"+orderBean.getOriginalPrice()+"',price='"+orderBean.getPrice()+"',address='"+orderBean.getAddress()+"',payment_type='"+orderBean.getPaymentType()+"'  where id='"+orderBean.getId()+"'";
		Connection conn = DBUtil.getConnection();*/
		String sql = "update user2 set username='"+userBean.getUsername()+"',password='"+userBean.getPassword()+"',nickname='"+userBean.getNickname()+"',salt='"+userBean.getSalt()+"' where id='"+ id+"'";
//		System.out.println(DateUtil.getDateStr(new Date())+"\t执行更新SQL语句："+sql);
		System.out.println("更新"+sql);
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
	
	
	public void updatePic(UserBean userBean,int id)
	{
		if(userBean.getPic() == null)
	{
			userBean.setPic("http://localhost:8080/eeshop/upload20170705/112249.jpg");
	}

		String sql = "update user2 set pic='"+userBean.getPic()+"' where id='"+ id+"'";

		System.out.println("更新"+sql);
		Connection con = DBUtil.getConnection();
		Statement st = null;
		try {
			st = con.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(st, con);
		}
	}
	
	public void delete(int id)
	{
		String sql = "delete from user where id="+id;
		
		Connection con = DBUtil.getConnection();
		Statement state = null;
		try {
			state = con.createStatement();
			state.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(state, con);
		}
	}
}
