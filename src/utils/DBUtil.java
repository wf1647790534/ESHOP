package utils;


import java.sql.Connection;//�������ݿ�
import java.sql.DriverManager;//��������Դ
import java.sql.ResultSet;//���ݿ����������ݱ�
import java.sql.SQLException;//�ṩ�������ݿ���ʴ��������������Ϣ���쳣
import java.sql.Statement;//����ִ�о�̬ SQL ��䲢�����������ɽ���Ķ���

/*���ݿ�Ĺ�����*/
public class DBUtil {
	//���������ݿ����� db_user�����ݿ��û��� db_password�����ݿ�����
	public static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static String db_url = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=����";
	public static String db_user = "sa";
	public static String db_password = "123456";
	
	//�������ݿ�
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(driverName);
			System.out.println("���������ɹ���");
			conn = DriverManager.getConnection(db_url, db_user, db_password);
			System.out.println("�������ݿ�ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	//�����Ƿ��������
/*	public static void main(String[] args)
	{
		DBUtil d = new DBUtil();
		d.getConn();
	}*/
	
	//�ر����ݿ�
	public static void close(Statement state,Connection conn){
		if(state != null){//�ͷŴ� Statement ��������ݿ�
			try{
				state.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		if(conn != null){//�ͷŴ� Connection ��������ݿ�
			try{
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	//�ر����ݿ�
	public static void close(ResultSet rs,Statement state,Connection conn){
		if(rs != null){//�ͷŴ� ResultSet ��������ݿ�
			try{
				rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		if(state != null){//�ͷŴ� Statement ��������ݿ�
			try{
				state.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		if(conn != null){//�ͷŴ� Connection ��������ݿ�
			try{
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}	
