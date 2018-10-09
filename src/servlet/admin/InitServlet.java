package servlet.admin;

import java.io.FileInputStream;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import utils.Constants;

/**
 * Servlet implementation class InitServlet
 */

public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("��̨���ó�ʼ����ʼ");
		String path;
		FileInputStream fis;
		try {
			// ��ȡ conf.properties �ļ�
			path = InitServlet.class.getResource("/").getPath();
			fis = new FileInputStream(path + "conf.properties");
			Properties properties = new Properties();
			properties.load(fis);
			fis.close();
			// ���� Constants ���ͼƬ�ϴ���ص�����
			String picUploadPath = properties.getProperty("pic_upload_path");
			if (picUploadPath != null) {
				Constants.PIC_UPLOAD_PATH = picUploadPath;
			}
			String picShowPath = properties.getProperty("pic_show_path");
			if (picShowPath != null) {
				Constants.PIC_SHOW_PATH = picShowPath;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
