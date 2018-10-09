package utils;


import java.sql.Connection;//连接数据库
import java.sql.DriverManager;//连接数据源
import java.sql.ResultSet;//数据库结果集的数据表
import java.sql.SQLException;//提供关于数据库访问错误或其他错误信息的异常
import java.sql.Statement;//用于执行静态 SQL 语句并返回它所生成结果的对象。

/*数据库的工具类*/
public class DBUtil {
	//哈哈是数据库名字 db_user是数据库用户名 db_password是数据库密码
	public static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static String db_url = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=哈哈";
	public static String db_user = "sa";
	public static String db_password = "123456";
	
	//连接数据库
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(driverName);
			System.out.println("加载驱动成功！");
			conn = DriverManager.getConnection(db_url, db_user, db_password);
			System.out.println("连接数据库成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	//试验是否可以连接
/*	public static void main(String[] args)
	{
		DBUtil d = new DBUtil();
		d.getConn();
	}*/
	
	//关闭数据库
	public static void close(Statement state,Connection conn){
		if(state != null){//释放此 Statement 对象的数据库
			try{
				state.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		if(conn != null){//释放此 Connection 对象的数据库
			try{
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	//关闭数据库
	public static void close(ResultSet rs,Statement state,Connection conn){
		if(rs != null){//释放此 ResultSet 对象的数据库
			try{
				rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		if(state != null){//释放此 Statement 对象的数据库
			try{
				state.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		if(conn != null){//释放此 Connection 对象的数据库
			try{
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}	
