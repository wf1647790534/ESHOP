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

import bean.ProductPropertyBean;
import bean.ProductTypeBean;
import dao.ProductPropertyDao;
import dao.ProductTypeDao;
import utils.StringUtil;

/**
 * Servlet implementation class ProductPropertyServlet
 */
@WebServlet({ "/ProductPropertyServlet", "/admin/product/property/productPropertyServlet" })
public class ProductPropertyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");

		if ("toAdd".equals(method)) {
			toAdd(request, response);
		} else if ("add".equals(method)) {
			add(request, response);
		} else if ("getType".equals(method)) {
			getType(request, response);
		} else if ("list".equals(method)) {
			list(request, response);
		} else if ("getProperty".equals(method)) {
			getProperty(request, response);
		} else if ("update".equals(method)) {
			update(request, response);
		}else if ("delete".equals(method)) {
			delete(request, response);
		}
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int id = StringUtil.StringToInt(req.getParameter("id"));
		ProductPropertyDao productPropertyDao = new ProductPropertyDao();
		productPropertyDao.delete(id);
		resp.sendRedirect("productPropertyServlet?method=list");
	}

	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = StringUtil.StringToInt(req.getParameter("id"));
		ProductPropertyDao productPropertyDao = new ProductPropertyDao();
		ProductPropertyBean productPropertyBean = productPropertyDao.getPropertyById(id);
		req.setAttribute("productPropertyBean", productPropertyBean);
		ProductTypeDao productTypeDao = new ProductTypeDao();
		List<ProductTypeBean> productTypeList = productTypeDao.getTypeList(0);
		req.setAttribute("productTypeList", productTypeList);
		req.getRequestDispatcher("add.jsp").forward(req, resp);
	}

	private void getProperty(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int typeId = StringUtil.StringToInt(req.getParameter("id"));
		ProductPropertyDao productPropertyDao = new ProductPropertyDao();
		List<ProductPropertyBean> propertyList = productPropertyDao.getListByType(typeId);
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		out.print(JSON.toJSONString(propertyList));
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
		String[] parIds = req.getParameterValues("productTypeId");
		int productTypeId = 0;
		for (String parId : parIds) {
			productTypeId = Math.max(productTypeId, StringUtil.StringToInt(parId));
		}
		ProductPropertyDao productPropertyDao = new ProductPropertyDao();
		boolean f;
		if (id == 0) {
			// 增加
			ProductPropertyBean productPropertyBean = new ProductPropertyBean(sort, productTypeId, name);
			f = productPropertyDao.add(productPropertyBean);
			if (f) {
				resp.sendRedirect("productPropertyServlet?method=toAdd&status=1");
			} else {
				System.out.println("添加属性写入数据库失败");
			}
		} else {
			// 修改
			ProductPropertyBean productPropertyBean = new ProductPropertyBean(id, sort, productTypeId, name);
			f = productPropertyDao.update(productPropertyBean);
			if (f) {
				resp.sendRedirect("productPropertyServlet?method=list&status=1");
			} else {
				System.out.println("修改属性写入数据库失败");
			}
		}
	}

	private void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductTypeDao productTypeDao = new ProductTypeDao();
		String status = request.getParameter("status");
		List<ProductTypeBean> typeList = productTypeDao.getTypeList(0);
		request.setAttribute("productTypeList", typeList);

		if ("1".equals(status)) {
			request.getRequestDispatcher("add.jsp?status=1").forward(request, response);
		} else {
			request.getRequestDispatcher("add.jsp").forward(request, response);
		}
	}

	private void getType(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int parentId = StringUtil.StringToInt(req.getParameter("id"));
		ProductTypeDao productTypeDao = new ProductTypeDao();
		List<ProductTypeBean> typeList = productTypeDao.getTypeList(parentId);
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		out.print(JSON.toJSONString(typeList));
		out.flush();
		out.close();
	}
}
