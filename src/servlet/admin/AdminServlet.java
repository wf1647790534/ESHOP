package servlet.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import utils.Constants;
import utils.MD5Util;
import utils.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.AdminBean;
import bean.PagingBean;
import dao.AdminDao;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		/*
		 * 每一个调用次 Servlet 的地方都需要传递一个method参数用来指明所要经进行的操作
		 */
		String method = request.getParameter("method");
		HttpSession session = request.getSession();
		Object ss = session.getAttribute(Constants.SESSION_ADMIN_BEAN);
		if(ss!=null)
		{
//			System.out.println(new Date().toLocaleString()+"\t当前用户为："+((AdminBean)ss).getUsername());
		}
		else 
//			System.out.println(new Date().toLocaleString()+"\t未登录状态！");
		
		// 根据method的值调用不同的方法执行不同的操作
		if("login".equals(method)){
			login(request,response);
		}else if("end".equals(method)){
			end(request,response);
		}else if("addUser".equals(method)){
			addUser(request,response);
		}else if ("list".equals(method)) { 
			listUsers(request, response); 
		}else if("toUpdate".equals(method)){
			toUpdate(request,response);
		}else if("delete".equals(method)){
			delete(request,response);
		}
	}

	/**
	 * 删除一个用户，需要指定参数为用户的id
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		int id = StringUtil.StringToInt(request.getParameter("id"));
		// id 为1的作为超级管理员是不可以删除的
		if(id > 1)
		{
			AdminDao adminDao = new AdminDao();
			adminDao.delete(id);
			// 响应重定向查看用户列表，状态status=3表示删除指定id的用户成功
			response.sendRedirect(request.getContextPath()+"/AdminServlet?method=list&status=3");
		}
		else if(id == 1)
		{
			// 响应重定向显示用户列表，status=1表示没有操作权限
			response.sendRedirect(request.getContextPath()+"/AdminServlet?method=list&status=1");
		}
		else
		{
			// 操作失败
			response.sendRedirect(request.getContextPath()+"/AdminServlet?method=list&status=2");
		}
	}

	/**
	 * 添加用户或者修改用户信息 
	 */
	private void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
//		System.out.println(new Date().toLocaleString()+"\tAdminServlet.addUser()开始执行");
		
		req.setCharacterEncoding("utf-8");
		/*
		 * updateId 用来判断调用当前方法的场景是添加用户还是修改用户信息
		 */
		String updateId = req.getParameter("updateId");
		
		AdminDao adminDao = new AdminDao();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
//		System.out.println(new Date().toLocaleString()+"\tupdateId = "+updateId+"; username = "+username+"; password="+password);
		// 创建一个用户对象并且设置用户的各个属性
		AdminBean adminBean = new AdminBean();
		adminBean.setUsername(username);
		String salt = StringUtil.getRandomString(10);
		String md5pwd = MD5Util.getMD5Code(MD5Util.getMD5Code(password)+salt);
		adminBean.setSalt(salt);
		adminBean.setPassword(md5pwd);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		adminBean.setCreateDate(sdf.format(new Date()));
		
		// 如果当前场景为修改用户信息
		if(!updateId.equals("")){
//			System.out.println(DateUtil.getDateStr(new Date())+"\tAdminServlet.add()执行修改");
			int id = StringUtil.StringToInt(updateId);
			adminBean.setId(id);
//			System.out.println(adminBean);
			boolean flag = true;
			// 判断用户是否存在
			if(!(username.equals(adminDao.getById(id).getUsername())))
			{
				flag = adminDao.checkReg(username);
			}
			// 如果该用户存在
			if(flag)
			{
				adminDao.update(adminBean);
				resp.sendRedirect("AdminServlet?method=list&status=2");
			}
			// 如果不存在
			else
			{
				resp.sendRedirect("admin/addUser.jsp?status=2");
			}
		}
		// 当前场景为修改用户信息
		else{
//			System.out.println(DateUtil.getDateStr(new Date())+"\tAdminServlet.add()执行添加");

			boolean flag = adminDao.checkReg(username);
			if(flag){
				adminDao.save(adminBean);
				resp.sendRedirect("admin/addUser.jsp?status=1");
			}else{
				resp.sendRedirect("admin/addUser.jsp?status=2");
			}
		}
		
	}

	/**
	 * 用户注销是回调此方法
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void end(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String status = request.getParameter("status");
		if(status != null && status.equals("1")){
			request.getSession().invalidate();
			response.sendRedirect(request.getContextPath()+"/admin/login.jsp");
		}
	}

	/**
	 * 当用户登录时调用此方法
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String username = request.getParameter("username");
		String password = MD5Util.getMD5Code(request.getParameter("password"));
		AdminBean adminbean = AdminDao.checkLogin(username, password);
		
		if(adminbean != null){
			request.getSession().setAttribute(Constants.SESSION_ADMIN_BEAN, adminbean);
			response.sendRedirect(request.getContextPath()+"/admin/main.jsp");
		}else{
			response.sendRedirect(request.getContextPath()+"/admin/login.jsp?status=1");
		}
	}

	/*
	 * 查询用户信息
	 */
	private void listUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { 
//		System.out.println(new Date().toLocaleString()+"\tAdminServlet.listUsers()执行开始！");
		req.setCharacterEncoding("utf-8");
		/*
		 * status 不是从listUsers.jsp页面传递过来的参数，而是从其他 servlet 方法传递过来的参数
		 * listUser.jsp页面点击删除或者修改后，最后会调用此方法，此时才会有status参数
		 */
		String status = req.getParameter("status");
		AdminDao adminDao = new AdminDao();
		
		// 得到当前显示的页数，如果current page为空，那么当前页数默认 0
		int currentPage = StringUtil.StringToInt(req.getParameter("currentPage"));
		// 得到记录总条数
		int countSize = adminDao.getCount();
//		System.out.println(new Date().toLocaleString()+"\tlistUsers status="+status+";currentPage = "+currentPage+";countSize = "+countSize);
		
		// 创建PagingBean对象
		PagingBean pagingBean = new PagingBean(countSize, currentPage, utils.Constants.PAGE_SIZE_10);
		List<AdminBean> adminBeans = adminDao.getListByPage(currentPage * utils.Constants.PAGE_SIZE_10, countSize);
		/*
		 * ？
		 * */
		pagingBean.setPrefixUrl(req.getContextPath() + "/AdminServlet?method=list");
		pagingBean.setAnd(true);
		
		// 将需要显示的用户对象存放到request作用域中
		req.setAttribute(utils.Constants.SESSION_ADMIN_BEANS, adminBeans);
		req.setAttribute("pagingBean", pagingBean);
		
		// 根据调用此方法的位置不同选择跳转时传递的参数
		if (status != null) {
			req.getRequestDispatcher("/admin/listUsers.jsp?status=" + status).forward(req, resp);
		} else {
			req.getRequestDispatcher("/admin/listUsers.jsp").forward(req, resp);
		}
	}
	
	/**
	 * 本方法应该为listuser.jsp页面发送修改用户信息请求后调用，页面应该传递的参数有需要修改的用户的id
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private void toUpdate(HttpServletRequest req,HttpServletResponse resp)throws IOException, ServletException
	{
//		System.out.println(new Date().toLocaleString()+"\tAdminServlet.toUpdate()开始执行！");
		req.setCharacterEncoding("UTF-8");
		int id = StringUtil.StringToInt(req.getParameter("id"));
//		System.out.println(new Date().toLocaleString()+"\tid="+id);
		if(id > 1){
			/*
			 * 1. 从数据库中查询出指定id的用户信息
			 * 2. 跳转到addUser.jsp页面显示查出的用户信息
			 * 3. addUser.jsp页面将重新填写信息保存到数据库 
			 */
			
			AdminDao adminDao = new AdminDao();
			AdminBean adminBean = adminDao.getById(id);
			
			req.setAttribute(Constants.SESSION_UPDATE_BEAN, adminBean);
			
			req.getRequestDispatcher("/admin/addUser.jsp").forward(req, resp);
		}else if(id == 1){
			// 第一个用户默认为超级管理员，不可修改
			resp.sendRedirect(req.getContextPath());
		}else{
			// 
			resp.sendRedirect("/AdminServlet?method=list&status=2");
		}
	}
	
	
}
