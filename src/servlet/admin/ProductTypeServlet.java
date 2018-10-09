package servlet.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import bean.ProductTypeBean;
import dao.ProductTypeDao;
import utils.DateUtil;
import utils.StringUtil;

@WebServlet("/admin/product/type/productTypeServlet")
public class ProductTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		// 获取操作 
		String method = request.getParameter("method");
		
		if("toAdd".equals(method))
		{
			toAdd(request,response);
		}
		else if("add".equals(method))
		{
			add(request,response);
		}
		else if("getType".equals(method))
		{
			getType(request,response);
		}
		else if("list".equals(method))
		{
			list(request,response);
		}
		else if("update".equals(method))
		{
			update(request,response);
		}
		else if("delete".equals(method))
		{
			delete(request,response);
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = StringUtil.StringToInt(request.getParameter("id"));
		ProductTypeDao productTypeDao = new ProductTypeDao();
		productTypeDao.delete(id);
		response.sendRedirect("productTypeServlet?method=list");
	}

	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = StringUtil.StringToInt(req.getParameter("id"));
		ProductTypeDao productTypeDao = new ProductTypeDao();
		// 获取要跟新的对象
		ProductTypeBean productTypeBean = productTypeDao.getTypeById(id);
		req.setAttribute("productTypeBean", productTypeBean);
		// 获取父分类名称
		List<ProductTypeBean> typeList = productTypeDao.getTypeList(0);
		req.setAttribute("productTypeList", typeList);
		// 跳转到add.jsp页面输入信息
		req.getRequestDispatcher("add.jsp").forward(req, resp);
	}

	/**
	 * 列出指定分类id下的所有子分类
	 */
	private void list(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException
	{
		
		
		int parentId = StringUtil.StringToInt(req.getParameter("id"));

		
		ProductTypeDao productTypeDao = new ProductTypeDao();
		// 根据id查找出指定分类的信息，该productTypeBean中封装了子分类的集合，所以在页面中可以得到子分类信息
		
		ProductTypeBean productTypeBean = productTypeDao.getType(parentId);

		
		req.setAttribute("productTypeBean", productTypeBean);
		//服务器端的重定向可以有两种方式，一是使用HttpServletResponse的sendRedirect()方法，一是使用RequestDispatcher的forward()方法.
		//实现请求转发，通过request对象提供的getRequestDispatche(String path)方法，返回一个requestDispatcher对象。
		req.getRequestDispatcher("list.jsp").forward(req, resp);
	}
	/**
	 * 获取分类，当add.jsp页面中父类下拉列表数据改变时会调用此方法
	 * 实现树形结构
	 */
	private void getType(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int parentId = StringUtil.StringToInt(request.getParameter("id"));
		ProductTypeDao productTypeDao = new ProductTypeDao();
		List<ProductTypeBean> typeList = productTypeDao.getTypeList(parentId);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONString(typeList));
		out.flush();
		out.close();
	}

	/**
	 * 给方法用来add.jsp页面中父类下拉框中的数据
	 */
	private void toAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.err.println(DateUtil.getDateStr(new Date())+"\tProductTypeServlet.toAdd()开始执行！");
		ProductTypeDao productTypeDao = new ProductTypeDao();
		List<ProductTypeBean>typeList = productTypeDao.getTypeBeans(0);
		req.setAttribute("productTypeList", typeList);
		req.getRequestDispatcher("add.jsp").forward(req, resp);
		System.err.println(DateUtil.getDateStr(new Date())+"\tProductTypeServlet.toAdd()执行结束！");
	}

	/**
	 * 添加记录和修改记录
	 */
	private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.err.println(DateUtil.getDateStr(new Date())+"\tProductTypeServlet.add()开始执行！");
		String name = req.getParameter("name");
//		System.out.println(DateUtil.getDateStr(new Date())+"\tname="+name);
		
		int sort = StringUtil.StringToInt(req.getParameter("sort"));
//		System.out.println(DateUtil.getDateStr(new Date())+"\tsort="+sort);
		
		String intro = req.getParameter("desc");
//		System.out.println(DateUtil.getDateStr(new Date())+"\tintro="+intro);
		
		int id = StringUtil.StringToInt(req.getParameter("id"));
//		System.out.println(DateUtil.getDateStr(new Date())+"\tid="+id);
		
		String[] parIds = req.getParameterValues("parentId");
		int parentId = 0;
		for (String parId : parIds) {
			parentId = Math.max(parentId, StringUtil.StringToInt(parId));
//			System.out.println(DateUtil.getDateStr(new Date())+"\tparentId = "+parentId+"\t");
		}
		ProductTypeDao productTypeDao = new ProductTypeDao();
		boolean f;
		if (id == 0) {
			// 添加新纪录
//			System.out.println(DateUtil.getDateStr(new Date())+"\t添加新纪录");
			ProductTypeBean productTypeBean = new ProductTypeBean(sort, parentId, name, intro);
			f = productTypeDao.add(productTypeBean);
		} else {
			// 修改记录
//			System.out.println(DateUtil.getDateStr(new Date())+"\t修改纪录");
			ProductTypeBean productTypeBean = new ProductTypeBean(id, sort, parentId, name, intro);
			f = productTypeDao.update(productTypeBean);
		}
		System.err.println(DateUtil.getDateStr(new Date())+"\tProductTypeServlet.add()执行结束！");
		// 操作结束之后任然返回add.jsp页面
		if (f) {
			resp.sendRedirect("productTypeServlet?method=toAdd&status=true");
		} else {
			resp.sendRedirect("productTypeServlet?method=toAdd&status=false");
		}
	}

}
