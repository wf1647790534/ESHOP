package servlet.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import bean.ProductOptionBean;
import bean.ProductTypeBean;
import dao.ProductOptionDao;
import dao.ProductTypeDao;
import utils.StringUtil;

/**
 * Servlet implementation class ProductOptionServlet
 */
@WebServlet({ "/ProductOptionServlet", "/admin/product/option/productOptionServlet" })
public class ProductOptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");

		if ("toAdd".equals(method)) {
			toAdd(request, response);
		} else if ("add".equals(method)) {
			add(request, response);
		} else if ("list".equals(method)) {
			list(request, response);
		} else if ("showOption".equals(method)) {
			showOption(request, response);
		} else if ("update".equals(method)) {
			update(request, response);
		}else if ("delete".equals(method)) {
			delete(request, response);
		}
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int id = StringUtil.StringToInt(req.getParameter("id"));
		ProductOptionDao productOptionDao = new ProductOptionDao();
		productOptionDao.delete(id);
		resp.sendRedirect("productOptionServlet?method=list");
	}

	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = StringUtil.StringToInt(req.getParameter("id"));
		ProductOptionDao productOptionDao = new ProductOptionDao();
		ProductOptionBean productOptionBean = productOptionDao.getOptionById(id);
		req.setAttribute("productOptionBean", productOptionBean);
		ProductTypeDao productTypeDao = new ProductTypeDao();
		List<ProductTypeBean> productTypeList = productTypeDao.getTypeList(0);
		req.setAttribute("productTypeList", productTypeList);
		req.getRequestDispatcher("add.jsp").forward(req, resp);
	}

	private void showOption(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int propertyId = StringUtil.StringToInt(req.getParameter("id"));
		ProductOptionDao productOptionDao = new ProductOptionDao();
		List<ProductOptionBean> optionList = productOptionDao.getOptionByProperty(propertyId);
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		out.print(JSON.toJSONString(optionList));
		out.flush();
		out.close();
	}

	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductTypeDao productTypeDao = new ProductTypeDao();
		String sta = req.getParameter("status");
		List<ProductTypeBean> typeList = productTypeDao.getTypeList(0);
		req.setAttribute("productTypeList", typeList);
		if (sta != null && "1".equals(sta)) {
			req.getRequestDispatcher("list.jsp?status=1").forward(req, resp);
		} else {
			req.getRequestDispatcher("list.jsp").forward(req, resp);
		}
	}

	private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int id = StringUtil.StringToInt(req.getParameter("id"));
		String name = req.getParameter("name");
		int sort = StringUtil.StringToInt(req.getParameter("sort"));
		int productPropertyId = StringUtil.StringToInt(req.getParameter("PropertyId"));
		ProductOptionDao productOptionDao = new ProductOptionDao();
		boolean f;
		if (id == 0) {
			// 增加
			ProductOptionBean productOptionBean = new ProductOptionBean(sort, productPropertyId, name);
			f = productOptionDao.add(productOptionBean);
			if (f) {
				resp.sendRedirect("productOptionServlet?method=toAdd&status=1");
			} else {
				System.out.println("添加商品属性选项写入数据库失败");
			}
		} else {
			// 修改
			ProductOptionBean productOptionBean = new ProductOptionBean(id, sort, productPropertyId, name);
			f = productOptionDao.update(productOptionBean);
			if (f) {
				resp.sendRedirect("productOptionServlet?method=list&status=1");
			} else {
				System.out.println("修改商品属性选项写入数据库失败");
			}
		}
	}

	private void toAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductTypeDao productTypeDao = new ProductTypeDao();
		String sta = req.getParameter("status");
		List<ProductTypeBean> typeList = productTypeDao.getTypeList(0);
		req.setAttribute("productTypeList", typeList);
		if (sta != null && "1".equals(sta)) {
			req.getRequestDispatcher("add.jsp?status=1").forward(req, resp);
		} else {
			req.getRequestDispatcher("add.jsp").forward(req, resp);
		}
	}
}
