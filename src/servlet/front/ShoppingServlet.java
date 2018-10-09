
/**
 * Servlet implementation class ShoppingServlet
 */
package servlet.front;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.UserOrderBean;
import bean.AddressBean;
import bean.OrderProductBean;
import bean.ProductBean;
import bean.ProductTypeBean;
import bean.UserBean;

import dao.UserOrderDao;
import utils.DateUtil;
import utils.StringUtil;
import dao.AddressDao;
import dao.OrderProductDao;
import dao.ProductDao;

import dao.ProductTypeDao;

@WebServlet("/front/shopping/shoppingServlet")
public class ShoppingServlet extends HttpServlet {
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String method = req.getParameter("method");
		if ("toCart".equals(method)) {
			toCart(req, resp);
		} else if ("addItem".equals(method)) {
			addItem(req, resp);
		} else if ("del".equals(method)) {
			del(req, resp);
		} else if ("addOrder".equals(method)) {
			addOrder(req, resp);
		} else if ("makeOrder".equals(method)) {
			makeOrder(req, resp);
		}
	}

	/**
	 * 添加商品到购物车 cookie
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException 
	 */

	private void addItem(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");
		PrintWriter out = resp.getWriter();
		ProductDao productDao = new ProductDao();
		if (userBean == null) {
		
			int data = 0;
			out.print(data);
			out.flush();
			out.close();
		}
		else
		{
			int data = 1;

			int productId = StringUtil.StringToInt(req.getParameter("productId"));
			String number = req.getParameter("num");
			int num = StringUtil.StringToInt(req.getParameter("num"));
			if(productDao.getProduct(productId).getNumber()<num)
			{
				data = 2;
				
				/*req.setAttribute("oneproductBean", productDao.getProduct(productId));
				req.getRequestDispatcher("cart.jsp?status=5").forward(req, resp);*/
			}
			// 构造cookie，前面是名字，后面是值 Cookie(String name, String value)
			Cookie cookie = new Cookie("items_" + userBean.getId() + "_" + productId, number);
			cookie.setPath("/");// cookie可在同一应用服务器内共享，“/”指的是根目录
			resp.addCookie(cookie);
			out.print(data);
			out.flush();
			out.close();
		}
		/*	PrintWriter out = resp.getWriter();
	/*	out.print("data");
		out.flush();
		out.close();*/
	}

	/**
	 * 删除购物车商品 cookie
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void del(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");
		// 游客访问
		if (userBean == null) {
			userBean = new UserBean();
		}
		int productId = StringUtil.StringToInt(req.getParameter("productId"));
		Cookie cookie = new Cookie("items_" + userBean.getId() + "_" + productId, null);
		cookie.setMaxAge(0);// 设置生命周期为0，表示将要删除
		cookie.setPath("/");
		resp.addCookie(cookie);
		resp.sendRedirect("shoppingServlet?method=toCart");
	}

	/**
	 * 转到购物车
	 * 
	 * @param req
	 * @param resp
	 */
	private void toCart(HttpServletRequest req, HttpServletResponse resp) {
		UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");
		if (userBean == null) {
			try {
				req.getRequestDispatcher("cart.jsp?status=3").forward(req, resp);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			ProductTypeDao productTypeDao = new ProductTypeDao();
			List<ProductTypeBean> typeBeans = productTypeDao.getTypeBeans(0);
			req.setAttribute("typeBeans", typeBeans);
			// 存储订单
			ProductDao productDao = new ProductDao();
			List<OrderProductBean> productOrderBeans = new ArrayList<>();
			OrderProductBean productOrderBean;
			Cookie[] cookies = req.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().startsWith("items_" + userBean.getId())) {
						String[] arr = cookie.getName().split("_");
						if (arr.length == 3) {
							int productId = StringUtil.StringToInt(arr[2]);
							int number = StringUtil.StringToInt(cookie.getValue());
							productOrderBean = new OrderProductBean(productDao.getProduct(productId), number);
							productOrderBeans.add(productOrderBean);
						}
					}
				}
			}
			req.setAttribute("productOrderBeans", productOrderBeans);
			
			if(productOrderBeans.size()>0)
			{
				try {
					req.getRequestDispatcher("cart.jsp?status=1").forward(req, resp);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			}
			else
			{
				try {
					req.getRequestDispatcher("cart.jsp?status=2").forward(req, resp);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			}
		}
		

	}

	/**
	 * 确认订单
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void makeOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");
		if (userBean == null) {
			userBean = new UserBean();
		}
		
		/*
		 转存地址 */ AddressDao addressDao=new AddressDao();
		 System.out.println("获取地址信息");
		 List<AddressBean> addressBeans = addressDao.getAddressList(userBean.getId());
		 for(AddressBean a : addressBeans){
			 System.out.println(a);
			 if(a.getStatus() == 0){
				 AddressBean t = a;
				 addressBeans.remove(a);//将a 删掉
				 addressBeans.add(0, t);//将a移动到第一个
				 break;
			 }
		 }
		 System.out.println(addressBeans.size());
		 System.out.println(addressBeans);
		 req.setAttribute("addressBeans", addressBeans);
		
		float sum = 0;
		float oSum = 0;
		ProductDao productDao = new ProductDao();
		ProductBean productBean = new ProductBean();
		List<OrderProductBean> productOrderBeans = new ArrayList<>();
		List<ProductBean> productBeans = new ArrayList<>();
		OrderProductBean productOrderBean;
		String[] items = req.getParameterValues("sub");
		

		
		
		
		if(items != null)
		{
			//sub是value="${pItem.productBean.id}_${pItem.number}">
			for (String item : items) {
				String[] arr = item.split("_");
				if (arr.length == 2) {
					int productId = StringUtil.StringToInt(arr[0]);
					int number = StringUtil.StringToInt(arr[1]);
				    productBean = productDao.getProduct(productId);
				    productBeans.add(productBean);
					productOrderBean = new OrderProductBean(productBean, number);
					productOrderBeans.add(productOrderBean);
					if(productBean.getNumber()<number)
					{
						
						req.setAttribute("oneproductBean", productDao.getProduct(productId));
						req.getRequestDispatcher("cart.jsp?status=5").forward(req, resp);
					}
					sum += productDao.getProduct(productId).getPrice() * number;
					oSum += productDao.getProduct(productId).getOriginalPrice() * number;
				}
			}
			
			
			
			req.setAttribute("productOrderBeans", productOrderBeans);
			
			
			req.setAttribute("sum", sum);
			req.setAttribute("oSum", oSum);
			
/*			 for(ProductBean a : productBeans){
					if(a.getNumber()<number)
					{
						
						req.setAttribute("oneproductBean", productDao.getProduct(productId));
						req.getRequestDispatcher("cart.jsp?status=5").forward(req, resp);
					}
			 }*/
			
/**/
			if(sum > 0)
			{
				try {
					req.getRequestDispatcher("order.jsp").forward(req, resp);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			}
			else
			{
				try {
					req.getRequestDispatcher("cart.jsp?status=4").forward(req, resp);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			}
		}
		else
		{
			try {
				resp.sendRedirect("cart.jsp?status=2");
			} catch ( IOException e) {
				e.printStackTrace();
			}
		}

		
		
		/*if (productOrderBeans.size() > 0) {
			try {
				req.getRequestDispatcher("order.jsp").forward(req, resp);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				resp.sendRedirect("cart.jsp?status=2");
			} catch ( IOException e) {
				e.printStackTrace();
			}
		}*/
		




	}

	/**
	 * 提交订单
	 * 
	 * @param req
	 * @param resp
	 */
	private void addOrder(HttpServletRequest req, HttpServletResponse resp) {
		UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");
		if (userBean == null) {
			userBean = new UserBean();
		}
		String code = DateUtil.getDateStr() + DateUtil.getTimeStr() + userBean.getId();
		
		
		UserOrderBean orderBean = new UserOrderBean(code, userBean);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		orderBean.setCreatDate(sdf.format(new Date()));
		UserOrderDao orderDao = new UserOrderDao();
		orderDao.addOrder(orderBean);
		//此时只有code 和 时间
		
		orderBean = orderDao.getOrderByCode(code);//获取order_id！
		// 存储订单
		ProductDao productDao = new ProductDao();
		OrderProductDao orderProductDao = new OrderProductDao();
		OrderProductBean productOrderBean;
		float sum = 0;
		float oSum = 0;
		//	sub是value="${pItem.productBean.id}_${pItem.number}">
		String[] items = req.getParameterValues("sub");
		for (String item : items) {
			String[] arr = item.split("_");
			if (arr.length == 2) {
				int productId = StringUtil.StringToInt(arr[0]);
				int number = StringUtil.StringToInt(arr[1]);
				System.out.println("ai"+orderBean.getId());
				productOrderBean = new OrderProductBean(orderBean, productDao.getProduct(productId), number);
				//添加了数量，时间，商品id，订单id
				orderProductDao.addOrderProduct(productOrderBean);
				
				
				sum += productDao.getProduct(productId).getPrice();
				oSum += productDao.getProduct(productId).getOriginalPrice();
				// 删除 cookie
				Cookie cookie = new Cookie("items_" + userBean.getId() + "_" + productId, null);
				cookie.setPath("/");
				cookie.setMaxAge(0);
				resp.addCookie(cookie);
			}
		}
		orderBean.setPrice(sum);
		orderBean.setOriginalPrice(oSum);
		// 付款方式
		int payway = StringUtil.StringToInt(req.getParameter("payway"));
		orderBean.setPaymentType(payway);
		
		// 地址
		int addressId = StringUtil.StringToInt(req.getParameter("addr"));
		orderBean.setAddress(addressId);
		//添加原价和现价
		orderDao.upOrder(orderBean);
		try {
			resp.sendRedirect(req.getContextPath()+"/front/productShow/productShowServlet?method=sort&id=0");
			//response.sendRedirect(request.getContextPath()+"/front/productShow/list.jsp");
			//req.getRequestDispatcher("/front/productShow/productShowServlet?method=sort&id=0").forward(req, resp);
		} catch ( IOException e) {
			e.printStackTrace();
		}
	}
}
