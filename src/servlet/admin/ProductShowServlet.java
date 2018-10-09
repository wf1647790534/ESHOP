package servlet.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bean.ProductBean;
import bean.ProductOptionBean;
import dao.ProductDao;
import dao.ProductOptionDao;
import utils.StringUtil;

public class ProductShowServlet extends HttpServlet {
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.setCharacterEncoding("utf-8");
		String method = req.getParameter("method");
		if ("sort".equals(method)) {
			sort(req, resp);
		} else if ("search".equals(method)) {
			search(req, resp);
		} else if ("info".equals(method)) {
			info(req, resp);
		}
	}

	private void info(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = StringUtil.StringToInt(req.getParameter("id"));
		ProductDao productDao = new ProductDao();
		ProductBean productBean = productDao.getProduct(id);
		String option = productBean.getProductProperties();
		String[] options = option.split(",");
		ProductOptionDao productOptionDao = new ProductOptionDao();
		List<ProductOptionBean> productOptionBeans = new ArrayList<>();
		for (String item : options) {
			int optionId = StringUtil.StringToInt(item);
			ProductOptionBean productOptionBean = productOptionDao.getOptionById(optionId);
			productOptionBeans.add(productOptionBean);
		}
		productBean.setProductOptionBeans(productOptionBeans);
		req.setAttribute("productBean", productBean);
		// 获取商品评论
		/*
		 * ProductCommentDao productCommentDao = new ProductCommentDao();
		 * List<CommentBean> commentBeans =
		 * productCommentDao.getComsByProduct(id);
		 * req.setAttribute("commentBeans", commentBeans);
		 */
		try {
			req.getRequestDispatcher("info.jsp").forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private void search(HttpServletRequest req, HttpServletResponse resp) {
		String chars = req.getParameter("key");
		ProductDao productDao = new ProductDao();
		List<ProductBean> productBeans = new ArrayList<>();
		productBeans = productDao.getLists(chars);
		req.setAttribute("productBeans", productBeans);
		if (productBeans.size() > 0) {
			try {
				req.getRequestDispatcher("list.jsp").forward(req, resp);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				req.getRequestDispatcher("list.jsp?status=1").forward(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 显示某分类的所有商品
	 * 
	 * @param req
	 * @param resp
	 */
	private void sort(HttpServletRequest req, HttpServletResponse resp) {
		ProductDao productDao = new ProductDao();
		int type_id = StringUtil.StringToInt(req.getParameter("id"));
		List<ProductBean> productBeans = productDao.getListById(type_id);
		req.setAttribute("productBeans", productBeans);
		try {
			if(productBeans.size() > 0)
			{
				req.getRequestDispatcher("list.jsp").forward(req, resp);
			}
			else
			{
				req.getRequestDispatcher("list.jsp?status=1").forward(req, resp);
			}
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}