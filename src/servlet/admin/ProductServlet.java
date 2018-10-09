package servlet.admin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import bean.ProductBean;
import bean.ProductOptionBean;
import dao.ProductDao;
import dao.ProductOptionDao;
import utils.*;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet({ "/ProductServlet", "/admin/product/productServlet" })
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String method = request.getParameter("method");
		
		
		if("add".equals(method)){
			add(request,response);
		}else if("list".equals(method)){
			list(request,response);
		}else if("listDetails".equals(method)){
			listDetails(request,response);
		}else if("update".equals(method)){
			update(request,response);
		}else if("delete".equals(method)){
			delete(request,response);
		}
	}
	
	
	/**
	 * 删除商品
	 * 
	 * @param req
	 * @param resp
	 */
	private void delete(HttpServletRequest req, HttpServletResponse resp) {
		int id = StringUtil.StringToInt(req.getParameter("id"));
		ProductDao productDao = new ProductDao();
		productDao.delete(id);
		try {
			resp.sendRedirect("productServlet?method=list");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void update(HttpServletRequest req, HttpServletResponse resp) {
		int id = StringUtil.StringToInt(req.getParameter("id"));
		ProductDao productDao = new ProductDao();
		ProductBean productBean = productDao.getProduct(id);
		req.setAttribute("productBean", productBean);
		try {
			req.getRequestDispatcher("add.jsp").forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 显示商品详情
	 * 
	 * @param req
	 * @param resp
	 */
	private void listDetails(HttpServletRequest req, HttpServletResponse resp) {
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
			// productOptionBean.getProductPropertyBean();
			productOptionBeans.add(productOptionBean);
		}
		productBean.setProductOptionBeans(productOptionBeans);
		req.setAttribute("productBean", productBean);
		try {
			req.getRequestDispatcher("details.jsp").forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 显示商品
	 * 
	 * @param req
	 * @param resp
	 */
	private void list(HttpServletRequest req, HttpServletResponse resp) {
		ProductDao productDao = new ProductDao();
		List<ProductBean> productBeans = productDao.getList();
		req.setAttribute("productBeans", productBeans);
		try {
			req.getRequestDispatcher("list.jsp").forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加商品
	 * 
	 * @param req
	 * @param resp
	 * @throws UnsupportedEncodingException
	 */
	private void add(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
		req.setCharacterEncoding("utf-8");
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
		try {
			List<FileItem> fileItems = upload.parseRequest(req);
			PrintWriter pw = resp.getWriter();
			ProductBean productBean = new ProductBean();
			for (FileItem item : fileItems) {
				item.getString("utf-8");
				if (item.isFormField()) {
					// 处理表单内容
					processFormField(item, pw, productBean);
				} else {
					// 处理上传文件
					System.out.println("tupian");
					processUploadFile(item, pw, productBean);
				}
			}
			pw.close();
			ProductDao productDao = new ProductDao();
			if (productBean.getId() == 0) {
				productDao.add(productBean);
			} else {
				productDao.update(productBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 处理上传的文件
	 * 
	 * @param item
	 * @param pw
	 * @param productBean
	 */
	private void processUploadFile(FileItem item, PrintWriter pw, ProductBean productBean) {
		String filename = item.getName();
		int index = filename.lastIndexOf(".");
		filename = filename.substring(index + 1, filename.length());
		String picPath = Constants.PIC_SHOW_PATH + DateUtil.getDateStr() + "/" + DateUtil.getTimeStr() + "." + filename;
		long fileSize = item.getSize();
		if ("".equals(filename) && fileSize == 0) {
			return;
		}
		// 新建文件夹，日期为文件夹名，时间为文件名
		File file = new File(Constants.PIC_UPLOAD_PATH + DateUtil.getDateStr());
		System.out.println("图片位置："+Constants.PIC_UPLOAD_PATH + DateUtil.getDateStr());
		file.mkdirs();
		File uploadFile = new File(
				Constants.PIC_UPLOAD_PATH + DateUtil.getDateStr() + "/" + DateUtil.getTimeStr() + "." + filename);
		try {
			item.write(uploadFile);
			productBean.setPic(picPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 处理表单内容
	 * 
	 * @param item
	 * @param pw
	 * @param productBean
	 * @throws UnsupportedEncodingException
	 */
	private void processFormField(FileItem item, PrintWriter pw, ProductBean productBean)
			throws UnsupportedEncodingException {
		String name = item.getFieldName();
		String value = new String(item.getString("utf-8"));
		switch (name) {
		case "id":
			int id = StringUtil.StringToInt(value);
			productBean.setId(id);
			
			break;
		case "name":
			productBean.setName(value);
			break;
		case "productTypeId":
			int productTypeId = Math.max(productBean.getProductTypeId(), StringUtil.StringToInt(value));
			productBean.setProductTypeId(productTypeId);
			break;
		case "originalPrice":
			float originalPrice = StringUtil.strToFlo(value);
			productBean.setOriginalPrice(originalPrice);
			break;
		case "price":
			float price = StringUtil.strToFlo(value);
			productBean.setPrice(price);
			break;
		case "intro":
			productBean.setIntro(value);
			break;
		case "number":
			int number = StringUtil.StringToInt(value);
			productBean.setNumber(number);
			break;
		case "option":
			String options = productBean.getProductProperties();
			if (options == null) {
				productBean.setProductProperties(value);
			} else {
				productBean.setProductProperties(options + "," + value);
			}
			break;
		default:
			break;
		}
	}
}
