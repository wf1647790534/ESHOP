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
//		System.out.println(new java.util.Date().toLocaleString()+"\t��ѯ���ݿ�:"+sql);
		try {
			con = DBUtil.getConnection();
			state = con.createStatement();
			rs = state.executeQuery(sql);
			if(rs.next()){
				//�û�������
//				System.out.println(new java.util.Date().toLocaleString()+"\t�û����ڣ��˶������....");
				String passwd = rs.getString("password");
				String salt = rs.getString("salt");
/**				
 * ��֤����ΪʲôҪpassword��salt��������,��������������ݿ��е�salt�ô��ʲô��
 * */
				String md5Code = MD5Util.getMD5Code(password+salt);
				if(passwd.equals(/*password*/md5Code)){
					//������֤�ɹ�
					SimpleDateFormat dsf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date date = rs.getDate("create_date");
					int id = rs.getInt("id");
					admin = new AdminBean();
					admin.setId(id);
					admin.setUsername(username);
					admin.setPassword(passwd);
					admin.setSalt(salt);
					admin.setCreateDate(dsf.format(date));
//					System.out.println(new java.util.Date().toLocaleString()+"\t�û���ݺ˶Գɹ�");
				}else{
					System.out.print(new java.util.Date().toLocaleString()+"\t�����֤ʧ��.....\t");
//					System.out.println(passwd+" should be "+md5Code);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{//�ر����ݿ�
			DBUtil.close(rs, state, con);
		}
		
		return admin;
	}
	
	/**
	 * �����û����Ƿ��Ѿ����ڣ����û�ע���ʱ�����
	 * @param name	�û���
	 * @return		����û������ڣ�����false�����򷵻�true
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
//			System.out.println(DateUtil.getDateStr(new Date())+"\tִ��AdminDao.checkReg()");
//			System.out.println(DateUtil.getDateStr(new Date())+"\tִ��sql��䣺"+sql);
			while(rs.next())
			{
				String username = rs.getString("username");
//				System.out.println(DateUtil.getDateStr(new Date())+"\t�Ƚ�"+name+"��"+username);
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
	 * �����û���Ϣ
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
//			System.out.println(new Date().toLocaleString()+"\t"+adminBean.getUsername()+"����ɹ���");
		}catch(Exception e){
//			System.out.println(new Date().toLocaleString()+"\t"+adminBean.getUsername()+"����ʧ�ܣ�");
			e.printStackTrace();
		}finally{
			DBUtil.close(state, con);
		}
	}
	
	
	/**
	 * ��ѯ�û���Ϣ
	 * @return	�����û���list���϶���
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
//			System.out.println(new Date().toLocaleString()+"\t��ѯ�ɹ�����"+adminBeans.size()+"����¼��");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(resultSet, statement, conn);
		}
		
		return adminBeans;
	}
	
	/**
	 * ��ѯ�û�����
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
//		System.out.println(new Date().toLocaleString()+"\tAdminDao.getListByPage()ִ�п�ʼ��");
		String sql = "select * from admin limit ";
		
		Connection con = DBUtil.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		List<AdminBean> adminBeans = new ArrayList<AdminBean>();
		
		try {
			statement = con.createStatement();
//			System.out.println(new Date().toLocaleString()+"\tִ�У�"+sql);
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
//			System.out.println(new Date().toLocaleString()+"\t��ѯ�ɹ�����ѯ���"+adminBeans.size());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(resultSet,statement,con);
//			System.out.println(new Date().toLocaleString()+"\tAdminDao.getListByPage()ִ�н�����");
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
//		System.out.println(DateUtil.getDateStr(new Date())+"\tִ�и���SQL��䣺"+sql);
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
