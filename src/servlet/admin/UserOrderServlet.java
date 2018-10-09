package servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ProductBean;
import bean.UserBean;
import bean.UserOrderBean;
import dao.ProductDao;
import dao.UserDao;
import dao.UserOrderDao;
import utils.StringUtil;

/**
 * Servlet implementation class UserOrderServlet
 */
@WebServlet("/frontUser/userOrderServlet")
public class UserOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;



    public UserOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String method = request.getParameter("method");
		/*if("userOrderlist".equals(method)){ userOrderlist(request,response); }
		else*/
			  if("list".equals(method))
			  { list(request,response); }
			  else if("detailUserlist".equals(method))
			  { detailUserlist(request,response); }
			  else if("detailUserlist1".equals(method))
			  { detailUserlist1(request,response); }
			  else if("detailUserlist2".equals(method))
			  { detailUserlist2(request,response); }
			  else if("delete".equals(method))
			  { delete(request,response); }
		/*
		 * if("reg".equals(method)){ add(request,response); } else
		 * if("login".equals(method)){ login(request,response); }else
		 * if("list".equals(method)){ list(request,response); }else
		 * if("detailUserlist".equals(method)){
		 * detailUserlist(request,response); } else
		 * if("detailUserlist1".equals(method)){
		 * detailUserlist1(request,response); } else
		 * if("update".equals(method)){ update(request,response); } }
		 */
	}

	
	private void delete(HttpServletRequest req, HttpServletResponse resp) {
		String userId = req.getParameter("username");
		UserOrderDao userOrderDao = new UserOrderDao();
		UserOrderBean userOrderBean = userOrderDao.getUser1(userId);
		//String product = userOrderBean.getProductId();
		userOrderDao.delete(userId);

		list(req,resp);
		
	}
	
	
	private void detailUserlist(HttpServletRequest req, HttpServletResponse resp) {
		String code = req.getParameter("code");
		UserDao userDao = new UserDao();
		UserOrderDao userOrderDao = new UserOrderDao();
		UserOrderBean userOrderBean = userOrderDao.getUser(code);
		String username = userOrderBean.getUserId();
		
		List<ProductBean> productBeans = null;
		if(userOrderBean != null)
		{
			productBeans = userOrderDao.getpsbyorderid(userOrderBean.getId());
			userOrderBean.setProductBeans(productBeans);
			System.out.println("zou1");
		}
	
		
		
		
		UserBean userBean = userDao.getUser(username);
		System.out.println(username);
		req.setAttribute("userOrderBean", userOrderBean);
		//req.setAttribute("productBeans", productBeans);
		//req.setAttribute("userBean", userBean);
		try {
			
			req.getRequestDispatcher("/admin/frontUser/orderDetail.jsp").forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private void detailUserlist1(HttpServletRequest req, HttpServletResponse resp) {
		String username = req.getParameter("username");
		
		UserDao userDao = new UserDao();
		
		UserOrderDao userOrderDao = new UserOrderDao();
		UserBean userBean = userDao.getUser(username);
		
		UserOrderBean userOrderBean = userOrderDao.getUser1(username);
		List<ProductBean> productBeans = null;
		if(userOrderBean != null)
		{
			 productBeans = userOrderDao.getpsbyorderid(userOrderBean.getId());
			userOrderBean.setProductBeans(productBeans);
			System.out.println("zou2");
		}
		
		
		req.setAttribute("userOrderBean", userOrderBean);
		req.setAttribute("userBean", userBean);
		try {
			
			
			if (userOrderBean != null) {
				
				req.getRequestDispatcher("/admin/frontUser/deleOrder.jsp").forward(req, resp);
			} else if(userBean != null) {
				req.getRequestDispatcher("/admin/frontUser/deleOrder.jsp?status=1").forward(req, resp);
			}
			else
			{
				req.getRequestDispatcher("/admin/frontUser/deleOrder.jsp?status=0").forward(req, resp);
			}
			
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void detailUserlist2(HttpServletRequest req, HttpServletResponse resp) {
		String username = req.getParameter("username");
		
		UserDao userDao = new UserDao();
		
		UserOrderDao userOrderDao = new UserOrderDao();
		UserBean userBean = userDao.getUser(username);
		
		UserOrderBean userOrderBean = userOrderDao.getUser1(username);
		List<ProductBean> productBeans = null;
		if(userOrderBean != null)
		{
			 productBeans = userOrderDao.getpsbyorderid(userOrderBean.getId());
			userOrderBean.setProductBeans(productBeans);
			System.out.println("zou2");
		}
		
		
		req.setAttribute("userOrderBean", userOrderBean);
		req.setAttribute("userBean", userBean);
		try {
			
			
			if (userOrderBean != null) {
				
				req.getRequestDispatcher("/admin/frontUser/orderDetail.jsp").forward(req, resp);
			} else if(userBean != null) {
				req.getRequestDispatcher("/admin/frontUser/orderDetail.jsp?status=1").forward(req, resp);
			}
			else
			{
				req.getRequestDispatcher("/admin/frontUser/orderDetail.jsp?status=0").forward(req, resp);
			}
			
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private void list(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("**********************zheli");
		UserOrderDao userOrderDao = new UserOrderDao();
		List<UserOrderBean> UserOrderBeans = userOrderDao.getList();
		req.setAttribute("UserOrderBeans", UserOrderBeans);
		try {
			req.getRequestDispatcher("/admin/frontUser/userOrder.jsp").forward(req, resp);
			
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
/*	private void userOrderlist(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		String username = req.getParameter("username");
		System.out.println("haha"+username);
		UserDao userDao = new UserDao();
		UserBean userBean = userDao.getUser(username);
		int user_id = userBean.getId();
		
		
		UserOrderDao userOrderDao = new UserOrderDao();
		UserOrderBean userOrderBean = userOrderDao.getUser(user_id);
		// System.out.println(userBean);

		req.setAttribute("userOrderBean", userOrderBean);
		try {

			if (userOrderBean != null) {
				// resp.sendRedirect("/eeshop/admin/frontUser/blockUser.jsp?status=1");
				req.getRequestDispatcher("/admin/frontUser/userOrder.jsp?").forward(req, resp);
			} else if(userBean != null) {
				req.getRequestDispatcher("/admin/frontUser/userOrde.jsp?status=1").forward(req, resp);
			}
			else
			{
				req.getRequestDispatcher("/admin/frontUser/userOrde.jsp?status=0").forward(req, resp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
}
