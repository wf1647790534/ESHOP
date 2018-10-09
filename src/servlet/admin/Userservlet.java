package servlet.admin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import bean.AdminBean;
import bean.PagingBean;
import bean.ProductBean;
import bean.ProductOptionBean;
import bean.UserBean;
import dao.AdminDao;
import dao.ProductDao;
import dao.ProductOptionDao;
import dao.UserDao;
import utils.Constants;
import utils.DateUtil;
import utils.MD5Util;
import utils.StringUtil;

/**
 * Servlet implementation class Userservlet
 */
@WebServlet("/frontUser/userServlet")
public class Userservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Userservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String method = request.getParameter("method");
		
		
		if("reg".equals(method)){
			add(request,response);
		}
		else if("login".equals(method)){
			login(request,response);
		}else if("list".equals(method)){
			list(request,response);
		}else if("detailUserlist".equals(method)){
			detailUserlist(request,response);
		}
		else if("detailUserlist1".equals(method)){
			detailUserlist1(request,response);
		}
		else if("update".equals(method)){
			update(request,response);
		}
		else if("updateInfo".equals(method)){
			updateInfo(request,response);
		}
		else if("updatePic".equals(method)){
			updatePic(request,response);
		}
	}


private void updatePic(HttpServletRequest req, HttpServletResponse resp) {
	int id = StringUtil.StringToInt(req.getParameter("id"));
	DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
	ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
	try {
		List<FileItem> fileItems = upload.parseRequest(req);
	//	PrintWriter pw = resp.getWriter();
		UserBean userBean = new UserBean();
		for (FileItem item : fileItems) {
			item.getString("utf-8");
		
				System.out.println("tupian");
				processUploadFile(item, null, userBean);
			
		}
		//pw.close();
		
		UserDao userDao = new UserDao();
		userDao.updatePic(userBean,id);
		UserBean newuserBean = new UserBean();
		newuserBean =  userDao.getById(id);
		req.getSession().setAttribute("userBean", newuserBean);
		resp.sendRedirect("/eeshop/front/user/userInfoDetail.jsp");
		
			
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}



private void updateInfo(HttpServletRequest req, HttpServletResponse resp) {
	int id = StringUtil.StringToInt(req.getParameter("id"));
	DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
	ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
	try {
		@SuppressWarnings("unchecked")
		List<FileItem> fileItems = upload.parseRequest(req);
	//	PrintWriter pw = resp.getWriter();
		UserBean userBean = new UserBean();
		for (FileItem item : fileItems) {
			item.getString("utf-8");
			if (item.isFormField()) {
				// 处理表单内容
				processFormField1(item, null, userBean);
			} else {
				// 处理上传文件
				System.out.println("tupian");
				processUploadFile(item, null, userBean);
			}
		}
		//pw.close();
		
		UserDao userDao = new UserDao();
		boolean flag = userDao.checkReg(userBean.getUsername());
		if(flag){
			userDao.update1(userBean,id);
			UserBean newuserBean = new UserBean();
			newuserBean =  userDao.getById(id);
			req.getSession().setAttribute("userBean", newuserBean);
			resp.sendRedirect("/eeshop/front/user/userInfoDetail.jsp?status=1");
		}else{
			resp.sendRedirect("/eeshop/front/user/userInfoDetail.jsp?status=2");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}
@SuppressWarnings("unused")
private void processFormField1(FileItem item, PrintWriter pw, UserBean userBean)
		throws UnsupportedEncodingException {
	String name = item.getFieldName();
	String value = new String(item.getString("utf-8"));

	switch (name) {
	case "account":
		userBean.setUsername(value);;
		
		break;

	case "password":
		String password = value;
		String salt = StringUtil.getRandomString(10);
		userBean.setSalt(salt);
		String md5pwd = MD5Util.getMD5Code(MD5Util.getMD5Code(password)+salt);
		userBean.setPassword(md5pwd);
		break;
	case "nickname":
		userBean.setNickname(value);
		break;


	default:
		break;
	}

}






private void update(HttpServletRequest req, HttpServletResponse resp) {
	String id = req.getParameter("username");
	UserDao userDao = new UserDao();
	UserBean userBean = userDao.getUser(id);
	System.out.println(userBean.toString());

	if(userBean.getStatus().equals("1"))
	{
		userBean.setStatus("0");
	}
	else if(userBean.getStatus().equals("0"))
	{
		userBean.setStatus("1");
	}
	
	System.out.println("折"+userBean.getStatus());
	userDao.update(userBean);
	list(req,resp);
}


private void detailUserlist(HttpServletRequest req, HttpServletResponse resp) {
	String id = req.getParameter("username");
	UserDao userDao = new UserDao();
	UserBean userBean = userDao.getUser(id);
System.out.println(userBean.toString());
	req.setAttribute("userbean", userBean);
	try {
		
		req.getRequestDispatcher("/admin/frontUser/detailUser.jsp").forward(req, resp);
	} catch (ServletException | IOException e) {
		e.printStackTrace();
	}
}


@SuppressWarnings("unused")
private void detailUserlist1(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
	String id = req.getParameter("username");
	System.out.println("haha"+id);
	UserDao userDao = new UserDao();
	UserBean userBean = userDao.getUser(id);
//System.out.println(userBean);

	req.setAttribute("userBean", userBean);
	try {
		
		if(userBean!=null){
			//resp.sendRedirect("/eeshop/admin/frontUser/blockUser.jsp?status=1");
			req.getRequestDispatcher("/admin/frontUser/blockUser.jsp").forward(req, resp);
		}else{
			req.getRequestDispatcher("/admin/frontUser/blockUser.jsp?status=0").forward(req, resp);
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
}

/*private void detailUserlist2(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
	String username = req.getParameter("username");
	System.out.println("haha"+username);
	UserDao userDao = new UserDao();
	UserBean userBean = userDao.getUser(username);
	int user_id = userBean.getId();

	try {
		
		if(userBean!=null){
			resp.sendRedirect("/eeshop/admin/frontUser/userOrder.jsp");
			
		}else{
			req.getRequestDispatcher("/admin/frontUser/blockUser.jsp?status=0").forward(req, resp);
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
}*/


private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	// TODO Auto-generated method stub
	request.setCharacterEncoding("utf-8");
	String username = request.getParameter("account");
	String password = MD5Util.getMD5Code(request.getParameter("password"));
	System.out.println("1 "+username);
	System.out.println("2 "+password);
	
	UserBean userBean = UserDao.checkLogin(username, password);

	if(userBean != null){
		request.getSession().setAttribute("userName", userBean.getUsername());
		request.getSession().setAttribute("userBean", userBean);
		response.sendRedirect(request.getContextPath()+"/front/productShow/productShowServlet?method=sort&id=0");		
		//response.sendRedirect(request.getContextPath()+"/front/productShow/list.jsp");
		//request.getRequestDispatcher("/front/productShow/list.jsp").forward(request,response);
		//
	}
	else
	{
		response.sendRedirect("/eeshop/front/user/login.jsp?status=2");
	}
	
}



    @SuppressWarnings("unused")
	private void processUploadFile(FileItem item, PrintWriter pw, UserBean userBean) {
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

				userBean.setPic(picPath);

		
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processFormField(FileItem item, PrintWriter pw, UserBean userBean)
			throws UnsupportedEncodingException {
		String name = item.getFieldName();
		String value = new String(item.getString("utf-8"));

		switch (name) {
		case "id":
			 int id = StringUtil.StringToInt(value);
			userBean.setId(id);
			
			break;
		case "account":
	
			userBean.setUsername(value);
			break;
		case "password":
			String password = value;
			String salt = StringUtil.getRandomString(10);
			userBean.setSalt(salt);
			String md5pwd = MD5Util.getMD5Code(MD5Util.getMD5Code(password)+salt);
			userBean.setPassword(md5pwd);
			break;
		case "nickname":
			userBean.setNickname(value);
			break;
		case "truename":
			userBean.setTruename(value);
			break;
		case "sex":
			userBean.setSex(value);
			break;

		default:
			break;
		}
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		userBean.setCreateDate(sdf.format(new Date()));

	}
    
	
	private void add(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
		req.setCharacterEncoding("utf-8");
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
		try {
			List<FileItem> fileItems = upload.parseRequest(req);
		//	PrintWriter pw = resp.getWriter();
			UserBean userBean = new UserBean();
			for (FileItem item : fileItems) {
				item.getString("utf-8");
				if (item.isFormField()) {
					// 处理表单内容
					processFormField(item, null, userBean);
				} else {
					// 处理上传文件
					System.out.println("tupian");
					processUploadFile(item, null, userBean);
				}
			}
			//pw.close();
			
			UserDao userDao = new UserDao();
			boolean flag = userDao.checkReg(userBean.getUsername());
			if(flag){
				userDao.save(userBean);
				resp.sendRedirect("/eeshop/front/user/add.jsp?status=1");
			}else{
				resp.sendRedirect("/eeshop/front/user/add.jsp?status=2");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void list(HttpServletRequest req, HttpServletResponse resp) {
		UserDao usertDao = new UserDao();
		List<UserBean> userBeans = usertDao.getList();
		req.setAttribute("userBeans", userBeans);
		try {
			req.getRequestDispatcher("/admin/frontUser/listUusers.jsp").forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}
