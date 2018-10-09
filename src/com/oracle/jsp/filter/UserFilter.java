package com.oracle.jsp.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bean.UserBean;
import utils.Constants;

public class UserFilter implements Filter {
	private Set<String> urls = new HashSet<String>();

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
//		HttpServletRequest request = (HttpServletRequest) req;
//		HttpServletResponse response = (HttpServletResponse) resp;
//		String path = request.getServletPath();
//		// ��ȡ���ʵ�ַ
//		// �ù����Ǵ���ǰ̨��¼���ж�
//		// ·��Ϊfront ǰ̨���е�·��
//		if (path.startsWith("/front")) {
//			// ��ĳЩ��ַ��Ҫ�Ź��������¼��ע��
//			if (urls.contains(path)) {
//				chain.doFilter(request, response);
//			} else {
//				UserBean userBean = (UserBean) request.getSession().getAttribute(Constants.SESSION_USER_BEAN);
//				if (userBean != null) {
//					// �ѵ�¼���Ź�
//					// �������ҵ�������Ź�
					chain.doFilter(req, resp);
//				} else {
//					// û�е�¼�����ص�¼ҳ��
//					response.sendRedirect(request.getContextPath() + "/front/user/login.jsp");
//				}
//			}
//		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		urls.add("/front/user/login.jsp");
		urls.add("/front/user/userServlet");
		urls.add("/front/user/add.jsp");
	}
}