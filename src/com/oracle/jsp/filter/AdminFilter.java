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


public class AdminFilter implements Filter {
	private Set<String> urls = new HashSet<String>();

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
//		HttpServletRequest request = (HttpServletRequest) req;
//		HttpServletResponse response = (HttpServletResponse) resp;
//		String path = request.getServletPath();
//		// ��ȡ���ʵ�ַ
//		// �ù����Ǵ����̨����Ա��¼���ж�
//		if (path.startsWith("/admin")) {
//			// ��ĳЩ��ַ��Ҫ�Ź��������¼��ע��
//			if (urls.contains(path)) {
//				chain.doFilter(request, response);
//			} else {
//				AdminBean adminBean = (AdminBean) request.getSession().getAttribute(Constants.SESSION_ADMIN_BEAN);
//				if (adminBean != null) {
//					// �ѵ�¼���Ź�
//					// �������ҵ�������Ź�
//					chain.doFilter(request, response);
//				} else {
//					// û�е�¼�����ص�¼ҳ��
//					response.sendRedirect(request.getContextPath() + "/admin/login.jsp");
//				}
//			}
//		}
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		urls.add("/admin/login.jsp");
		urls.add("/admin/adminServlet");
		urls.add("/admin/addUser.jsp");
	}
}