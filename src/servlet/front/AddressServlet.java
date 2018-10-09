package servlet.front;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import bean.AddressBean;
import bean.UserBean;
import dao.AddressDao;

/**
 * Servlet implementation class AddressServlet
 */
@WebServlet("/front/user/AddressServlet")
public class AddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String method = req.getParameter("method");
		if ("show".equals(method)) {
			show(req, resp);
		} else if ("toadd".equals(method)) {
			toadd(req, resp);
		}else if ("add".equals(method)) {
			add(req, resp);
		}else if ("delete".equals(method)) {
			delete(req, resp);
		}else if ("setdefault".equals(method)) {
			setdefault(req, resp);
		}
	}
	protected void show(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");
		if (userBean == null) {
			userBean = new UserBean();
		}
		AddressDao addressDao =new AddressDao();
		List<AddressBean> addressBeans= new ArrayList<>();
		addressBeans=addressDao.getAddressList(userBean.getId());
		req.setAttribute("addressBeans", addressBeans);
		
		req.getRequestDispatcher("address.jsp?status="+req.getAttribute("status")).forward(req, resp);
		
	}
	
	protected void toadd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		AddressDao addressDao =new AddressDao();
		String type = req.getParameter("type");
		if ("province".equals(type)) {
			List<AddressBean> provinces= new ArrayList<>();
			provinces= addressDao.getprovince();
			resp.setCharacterEncoding("utf-8");
			PrintWriter out = resp.getWriter();
			out.print(JSON.toJSONString(provinces));
			out.flush();
			out.close();
		} else if ("city".equals(type)) {
			int id = Integer.parseInt(req.getParameter("id"));
			List<AddressBean> citys= new ArrayList<>();
			citys= addressDao.getcity(id);
			//System.out.println(id);
			resp.setCharacterEncoding("utf-8");
			PrintWriter out = resp.getWriter();
			out.print(JSON.toJSONString(citys));
			out.flush();
			out.close();
		}else if ("area".equals(type)) {
			int id = Integer.parseInt(req.getParameter("id"));
			List<AddressBean> areas= new ArrayList<>();
			areas= addressDao.getarea(id);
			//System.out.println(id);
			resp.setCharacterEncoding("utf-8");
			PrintWriter out = resp.getWriter();
			out.print(JSON.toJSONString(areas));
			out.flush();
			out.close();
		}	
	}
	
	protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AddressBean addressBean=new AddressBean();
		AddressDao addressDao=new AddressDao();
		
		UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");
		if (userBean == null) {
			userBean = new UserBean();
		}
		addressBean.setName(req.getParameter("name"));
		addressBean.setUser_id(userBean.getId());
		addressBean.setCellphone(req.getParameter("cellphone"));
		addressBean.setAddress(req.getParameter("address"));
		
		int province = 0,city = 0,area = 0;
		try{
			province=Integer.parseInt(req.getParameter("province"));
			city=Integer.parseInt(req.getParameter("city"));
			area=Integer.parseInt(req.getParameter("area"));
			//System.out.println(province+" "+city+" "+area);
			addressDao.add(addressBean, province, city, area);
			show(req, resp);
		}catch(Exception e){
			req.setAttribute("status", 0);
			show(req, resp);
		}
		
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AddressDao addressDao=new AddressDao();
		addressDao.delete(Integer.parseInt(req.getParameter("id")));
		show(req, resp);
	}
	protected void setdefault(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AddressDao addressDao=new AddressDao();
		UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");
		if (userBean == null) {
			userBean = new UserBean();
		}
		addressDao.setdefault(userBean.getId(),Integer.parseInt(req.getParameter("id")));
		show(req, resp);
	}
}
