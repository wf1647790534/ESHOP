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
import utils.DBUtil;
import utils.MD5Util;

public class AdminDao {

	private static Connection con;
	private static Statement state;
	private static ResultSet rs;

	public AdminDao() {
	}
	
	public static AdminBean checkLogin(String username,String password)
	{
		AdminBean admin  = null;
		String sql = "select * from admin where username='"+username+"'";
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
				if(passwd.equals(/*password*/md5Code)){
					//密码验证成功
					SimpleDateFormat dsf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date date = rs.getDate("create_date");
					int id = rs.getInt("id");
					admin = new AdminBean();
					admin.setId(id);
					admin.setUsername(username);
					admin.setPassword(passwd);
					admin.setSalt(salt);
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
			String sql = "select username from admin ";
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
	public void save(AdminBean adminBean)
	{
		String sql = "insert into admin(username,password,salt,create_date) values('"
				+ ""+adminBean.getUsername()
				+ "','"+adminBean.getPassword()+"','"+adminBean.getSalt()+"','"+adminBean.getCreateDate()+"')";
//		System.out.println(new Date().toLocaleString()+"\t"+sql);
		Connection con = DBUtil.getConnection();
		Statement state = null;
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
	public List<AdminBean> list(){
		
		String sql = "select * from admin";
		Connection conn = DBUtil.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		
		List<AdminBean> adminBeans = new ArrayList<AdminBean>();
		
		try {
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			AdminBean adminBean;
			while(resultSet.next())
			{
				adminBean = new AdminBean();
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
			rs = state.executeQuery("select count(*) count from admin");
			if(rs.next())
				size = rs.getInt("count");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, con);
		}
		
		return size;
	}

	public List<AdminBean> getListByPage(int start, int size) {
//		System.out.println(new Date().toLocaleString()+"\tAdminDao.getListByPage()执行开始！");
		String sql = "select * from admin limit ";
		
		Connection con = DBUtil.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		List<AdminBean> adminBeans = new ArrayList<AdminBean>();
		
		try {
			statement = con.createStatement();
//			System.out.println(new Date().toLocaleString()+"\t执行："+sql);
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				int id = resultSet.getInt("id");
				String username = resultSet.getString("username");
				String password = resultSet.getString("password");
				String salt = resultSet.getString("salt");
				String date = resultSet.getString("create_date");
				adminBeans.add(new AdminBean(id,username,password,salt,date));
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

	public AdminBean getById(int id) {
		
		String sql = "select * from admin where id = "+id;
		Connection con = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		AdminBean adminBean = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next())
			{
				String username = rs.getString("username");
				String password = rs.getString("password");
				String salt = rs.getString("salt");
				String date = rs.getString("create_date");
				adminBean = new AdminBean(id,username,password,salt,date);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs,st,con);
		}
		return adminBean;
	}
	
	
	public void update(AdminBean adminBean)
	{
		String sql = "update admin set username='"+adminBean.getUsername()+"',"
				+ "password='"+adminBean.getPassword()+"',salt='"+adminBean.getSalt()+"' where id='"
				+ adminBean.getId()+"'";
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
	
	public void delete(int id)
	{
		String sql = "delete from admin where id="+id;
		
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
