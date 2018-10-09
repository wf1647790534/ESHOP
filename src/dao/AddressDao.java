package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import bean.AddressBean;
import bean.ProductBean;
import utils.DBUtil;

public class AddressDao {

	
	private static Connection con;
	private static Statement state;
	private static ResultSet rs;
	public AddressBean getAddressById(int addressId) {
		String sql = "select * from user_address where id='"+addressId+"'";
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		ResultSet rs = null;
		AddressBean addressBean = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			
			System.out.println("到这 了" + sql);
			while (rs.next()) {
				addressBean = new AddressBean();
				int Id = rs.getInt("id");
				System.out.println("*******************"+Id);
				addressBean.setId(Id);
				addressBean.setAddress(rs.getString("address"));
				addressBean.setCellphone(rs.getString("cellphone"));

				addressBean.setCreate_date(rs.getString("create_date"));
				addressBean.setName(rs.getString("name"));
				int user_id = rs.getInt("user_id");
				addressBean.setUser_id(user_id);
				
				Connection con3=DBUtil.getConnection();
				Statement state3=con3.createStatement();
				String sql2="select P.name province ,C.name city,A.name area from province P join city C on P.id=C.province_id join area A on C.id=A.city_id where  A.id='"+rs.getInt("region")+"'";
				ResultSet rs3=state3.executeQuery(sql2);
				while(rs3.next())
				{
					addressBean.setArea(rs3.getString("area"));
					addressBean.setProvince(rs3.getString("province"));
					addressBean.setCity(rs3.getString("city"));
				}
				DBUtil.close(rs3, state3, con3);
			}	
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}

		return addressBean;
	}
	
	public List<AddressBean> getAddressList(int id) {
		String sql = "select * from user_address where user_id='"+id+"'";
		List<AddressBean> list = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			AddressBean addressBean = null;
			System.out.println("到这 了" + sql);
			while (rs.next()) {
				addressBean = new AddressBean();
				int Id = rs.getInt("id");
				System.out.println("*******************"+Id);
				addressBean.setId(Id);
				addressBean.setAddress(rs.getString("address"));
				addressBean.setCellphone(rs.getString("cellphone"));

				addressBean.setCreate_date(rs.getString("create_date"));
				addressBean.setName(rs.getString("name"));
				int user_id = rs.getInt("user_id");
				addressBean.setUser_id(user_id);
				
				Connection con3=DBUtil.getConnection();
				Statement state3=con3.createStatement();
				String sql2="select P.name province ,C.name city,A.name area from province P join city C on P.id=C.province_id join area A on C.id=A.city_id where  A.id='"+rs.getInt("region")+"'";
				ResultSet rs3=state3.executeQuery(sql2);
				while(rs3.next())
				{
					addressBean.setArea(rs3.getString("area"));
					addressBean.setProvince(rs3.getString("province"));
					addressBean.setCity(rs3.getString("city"));
				}
				DBUtil.close(rs3, state3, con3);
			}	
				
				
/*				int city = rs.getInt("city");
				addressBean.setCity(city);
				int province = rs.getInt("province");
				addressBean.setProvince(province);
				int region = rs.getInt("region");
				addressBean.setRegion(region);
				int status = rs.getInt("status");
				addressBean.setStatus(status);*/

				//System.out.println(addressBean);
				list.add(addressBean);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		return list;
	}
	
	public List<AddressBean> getprovince(){
		String sql = "select * from province ";
		List<AddressBean> provinces = new ArrayList<AddressBean>();
		try {
			con = DBUtil.getConnection();
			state = con.createStatement();
			rs = state.executeQuery(sql);
			while(rs.next())
			{
				AddressBean province=new AddressBean();
				province.setName(rs.getString("name"));
				province.setId(rs.getInt("id"));
				provinces.add(province);
			}
//
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, con);
		}
		
		return provinces;
	}
	
	
	public List<AddressBean> getcity(int id){
		String sql = "select * from city where province_id='"+id+"'";
		List<AddressBean> citys = new ArrayList<AddressBean>();
		try {
			con = DBUtil.getConnection();
			state = con.createStatement();
			rs = state.executeQuery(sql);
			while(rs.next())
			{
				AddressBean city=new AddressBean();
				city.setName(rs.getString("name"));
				city.setId(rs.getInt("id"));
				citys.add(city);
			}
//
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, con);
		}
		
		return citys;
	}
	
	public List<AddressBean> getarea(int id){
		String sql = "select * from area where city_id='"+id+"'";
		List<AddressBean> areas = new ArrayList<AddressBean>();
		try {
			con = DBUtil.getConnection();
			state = con.createStatement();
			rs = state.executeQuery(sql);
			while(rs.next())
			{
				AddressBean area=new AddressBean();
				area.setName(rs.getString("name"));
				area.setId(rs.getInt("id"));
				areas.add(area);
			}
//
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, con);
		}
		
		return areas;
	}
	
	
	
	public void add(AddressBean address,int province,int city,int area)
	{
		String sql = "insert into [user_address] (name,user_id,cellphone,province,city,region,address,create_date) values('"
				+address.getName()+ "','"+address.getUser_id()+"','"+address.getCellphone()+"','"+province
				+ "','"+city+ "','"+area+ "','"+address.getAddress()+ "',"+" getdate() "+
				")";
		Connection con = DBUtil.getConnection();
		Statement state = null;
		try{
			state = con.createStatement();
			state.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(state, con);
		}
	}
	
	public void delete(int id)
	{
		String sql = "delete from user_address where id='"+id+"'";
		Connection con = DBUtil.getConnection();
		Statement state = null;
		try{
			state = con.createStatement();
			state.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(state, con);
		}
	}
	
	public void setdefault(int user_id,int id)
	{
		String sql = "update user_address set status ='0' where user_id='"+user_id+"'"
					 +"update user_address set status ='1' where id='"+id+"'";
		Connection con = DBUtil.getConnection();
		Statement state = null;
		try{
			state = con.createStatement();
			state.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(state, con);
		}
	}
}
