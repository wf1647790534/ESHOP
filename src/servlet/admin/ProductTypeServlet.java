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
		// ��ȡ���� 
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
		// ��ȡҪ���µĶ���
		ProductTypeBean productTypeBean = productTypeDao.getTypeById(id);
		req.setAttribute("productTypeBean", productTypeBean);
		// ��ȡ����������
		List<ProductTypeBean> typeList = productTypeDao.getTypeList(0);
		req.setAttribute("productTypeList", typeList);
		// ��ת��add.jspҳ��������Ϣ
		req.getRequestDispatcher("add.jsp").forward(req, resp);
	}

	/**
	 * �г�ָ������id�µ������ӷ���
	 */
	private void list(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException
	{
		
		
		int parentId = StringUtil.StringToInt(req.getParameter("id"));

		
		ProductTypeDao productTypeDao = new ProductTypeDao();
		// ����id���ҳ�ָ���������Ϣ����productTypeBean�з�װ���ӷ���ļ��ϣ�������ҳ���п��Եõ��ӷ�����Ϣ
		
		ProductTypeBean productTypeBean = productTypeDao.getType(parentId);

		
		req.setAttribute("productTypeBean", productTypeBean);
		//�������˵��ض�����������ַ�ʽ��һ��ʹ��HttpServletResponse��sendRedirect()������һ��ʹ��RequestDispatcher��forward()����.
		//ʵ������ת����ͨ��request�����ṩ��getRequestDispatche(String path)����������һ��requestDispatcher����
		req.getRequestDispatcher("list.jsp").forward(req, resp);
	}
	/**
	 * ��ȡ���࣬��add.jspҳ���и��������б����ݸı�ʱ����ô˷���
	 * ʵ�����νṹ
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
	 * ����������add.jspҳ���и����������е�����
	 */
	private void toAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.err.println(DateUtil.getDateStr(new Date())+"\tProductTypeServlet.toAdd()��ʼִ�У�");
		ProductTypeDao productTypeDao = new ProductTypeDao();
		List<ProductTypeBean>typeList = productTypeDao.getTypeBeans(0);
		req.setAttribute("productTypeList", typeList);
		req.getRequestDispatcher("add.jsp").forward(req, resp);
		System.err.println(DateUtil.getDateStr(new Date())+"\tProductTypeServlet.toAdd()ִ�н�����");
	}

	/**
	 * ��Ӽ�¼���޸ļ�¼
	 */
	private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.err.println(DateUtil.getDateStr(new Date())+"\tProductTypeServlet.add()��ʼִ�У�");
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
			// ����¼�¼
//			System.out.println(DateUtil.getDateStr(new Date())+"\t����¼�¼");
			ProductTypeBean productTypeBean = new ProductTypeBean(sort, parentId, name, intro);
			f = productTypeDao.add(productTypeBean);
		} else {
			// �޸ļ�¼
//			System.out.println(DateUtil.getDateStr(new Date())+"\t�޸ļ�¼");
			ProductTypeBean productTypeBean = new ProductTypeBean(id, sort, parentId, name, intro);
			f = productTypeDao.update(productTypeBean);
		}
		System.err.println(DateUtil.getDateStr(new Date())+"\tProductTypeServlet.add()ִ�н�����");
		// ��������֮����Ȼ����add.jspҳ��
		if (f) {
			resp.sendRedirect("productTypeServlet?method=toAdd&status=true");
		} else {
			resp.sendRedirect("productTypeServlet?method=toAdd&status=false");
		}
	}

}
