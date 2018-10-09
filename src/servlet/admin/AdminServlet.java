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
		 * ÿһ�����ô� Servlet �ĵط�����Ҫ����һ��method��������ָ����Ҫ�����еĲ���
		 */
		String method = request.getParameter("method");
		HttpSession session = request.getSession();
		Object ss = session.getAttribute(Constants.SESSION_ADMIN_BEAN);
		if(ss!=null)
		{
//			System.out.println(new Date().toLocaleString()+"\t��ǰ�û�Ϊ��"+((AdminBean)ss).getUsername());
		}
		else 
//			System.out.println(new Date().toLocaleString()+"\tδ��¼״̬��");
		
		// ����method��ֵ���ò�ͬ�ķ���ִ�в�ͬ�Ĳ���
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
	 * ɾ��һ���û�����Ҫָ������Ϊ�û���id
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		int id = StringUtil.StringToInt(request.getParameter("id"));
		// id Ϊ1����Ϊ��������Ա�ǲ�����ɾ����
		if(id > 1)
		{
			AdminDao adminDao = new AdminDao();
			adminDao.delete(id);
			// ��Ӧ�ض���鿴�û��б�״̬status=3��ʾɾ��ָ��id���û��ɹ�
			response.sendRedirect(request.getContextPath()+"/AdminServlet?method=list&status=3");
		}
		else if(id == 1)
		{
			// ��Ӧ�ض�����ʾ�û��б�status=1��ʾû�в���Ȩ��
			response.sendRedirect(request.getContextPath()+"/AdminServlet?method=list&status=1");
		}
		else
		{
			// ����ʧ��
			response.sendRedirect(request.getContextPath()+"/AdminServlet?method=list&status=2");
		}
	}

	/**
	 * ����û������޸��û���Ϣ 
	 */
	private void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
//		System.out.println(new Date().toLocaleString()+"\tAdminServlet.addUser()��ʼִ��");
		
		req.setCharacterEncoding("utf-8");
		/*
		 * updateId �����жϵ��õ�ǰ�����ĳ���������û������޸��û���Ϣ
		 */
		String updateId = req.getParameter("updateId");
		
		AdminDao adminDao = new AdminDao();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
//		System.out.println(new Date().toLocaleString()+"\tupdateId = "+updateId+"; username = "+username+"; password="+password);
		// ����һ���û������������û��ĸ�������
		AdminBean adminBean = new AdminBean();
		adminBean.setUsername(username);
		String salt = StringUtil.getRandomString(10);
		String md5pwd = MD5Util.getMD5Code(MD5Util.getMD5Code(password)+salt);
		adminBean.setSalt(salt);
		adminBean.setPassword(md5pwd);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		adminBean.setCreateDate(sdf.format(new Date()));
		
		// �����ǰ����Ϊ�޸��û���Ϣ
		if(!updateId.equals("")){
//			System.out.println(DateUtil.getDateStr(new Date())+"\tAdminServlet.add()ִ���޸�");
			int id = StringUtil.StringToInt(updateId);
			adminBean.setId(id);
//			System.out.println(adminBean);
			boolean flag = true;
			// �ж��û��Ƿ����
			if(!(username.equals(adminDao.getById(id).getUsername())))
			{
				flag = adminDao.checkReg(username);
			}
			// ������û�����
			if(flag)
			{
				adminDao.update(adminBean);
				resp.sendRedirect("AdminServlet?method=list&status=2");
			}
			// ���������
			else
			{
				resp.sendRedirect("admin/addUser.jsp?status=2");
			}
		}
		// ��ǰ����Ϊ�޸��û���Ϣ
		else{
//			System.out.println(DateUtil.getDateStr(new Date())+"\tAdminServlet.add()ִ�����");

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
	 * �û�ע���ǻص��˷���
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
	 * ���û���¼ʱ���ô˷���
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
	 * ��ѯ�û���Ϣ
	 */
	private void listUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { 
//		System.out.println(new Date().toLocaleString()+"\tAdminServlet.listUsers()ִ�п�ʼ��");
		req.setCharacterEncoding("utf-8");
		/*
		 * status ���Ǵ�listUsers.jspҳ�洫�ݹ����Ĳ��������Ǵ����� servlet �������ݹ����Ĳ���
		 * listUser.jspҳ����ɾ�������޸ĺ�������ô˷�������ʱ�Ż���status����
		 */
		String status = req.getParameter("status");
		AdminDao adminDao = new AdminDao();
		
		// �õ���ǰ��ʾ��ҳ�������current pageΪ�գ���ô��ǰҳ��Ĭ�� 0
		int currentPage = StringUtil.StringToInt(req.getParameter("currentPage"));
		// �õ���¼������
		int countSize = adminDao.getCount();
//		System.out.println(new Date().toLocaleString()+"\tlistUsers status="+status+";currentPage = "+currentPage+";countSize = "+countSize);
		
		// ����PagingBean����
		PagingBean pagingBean = new PagingBean(countSize, currentPage, utils.Constants.PAGE_SIZE_10);
		List<AdminBean> adminBeans = adminDao.getListByPage(currentPage * utils.Constants.PAGE_SIZE_10, countSize);
		/*
		 * ��
		 * */
		pagingBean.setPrefixUrl(req.getContextPath() + "/AdminServlet?method=list");
		pagingBean.setAnd(true);
		
		// ����Ҫ��ʾ���û������ŵ�request��������
		req.setAttribute(utils.Constants.SESSION_ADMIN_BEANS, adminBeans);
		req.setAttribute("pagingBean", pagingBean);
		
		// ���ݵ��ô˷�����λ�ò�ͬѡ����תʱ���ݵĲ���
		if (status != null) {
			req.getRequestDispatcher("/admin/listUsers.jsp?status=" + status).forward(req, resp);
		} else {
			req.getRequestDispatcher("/admin/listUsers.jsp").forward(req, resp);
		}
	}
	
	/**
	 * ������Ӧ��Ϊlistuser.jspҳ�淢���޸��û���Ϣ�������ã�ҳ��Ӧ�ô��ݵĲ�������Ҫ�޸ĵ��û���id
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private void toUpdate(HttpServletRequest req,HttpServletResponse resp)throws IOException, ServletException
	{
//		System.out.println(new Date().toLocaleString()+"\tAdminServlet.toUpdate()��ʼִ�У�");
		req.setCharacterEncoding("UTF-8");
		int id = StringUtil.StringToInt(req.getParameter("id"));
//		System.out.println(new Date().toLocaleString()+"\tid="+id);
		if(id > 1){
			/*
			 * 1. �����ݿ��в�ѯ��ָ��id���û���Ϣ
			 * 2. ��ת��addUser.jspҳ����ʾ������û���Ϣ
			 * 3. addUser.jspҳ�潫������д��Ϣ���浽���ݿ� 
			 */
			
			AdminDao adminDao = new AdminDao();
			AdminBean adminBean = adminDao.getById(id);
			
			req.setAttribute(Constants.SESSION_UPDATE_BEAN, adminBean);
			
			req.getRequestDispatcher("/admin/addUser.jsp").forward(req, resp);
		}else if(id == 1){
			// ��һ���û�Ĭ��Ϊ��������Ա�������޸�
			resp.sendRedirect(req.getContextPath());
		}else{
			// 
			resp.sendRedirect("/AdminServlet?method=list&status=2");
		}
	}
	
	
}
