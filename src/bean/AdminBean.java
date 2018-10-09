package bean;

public class AdminBean {

	public AdminBean() {
		// TODO Auto-generated constructor stub
	}
	
	private int id;
	private String 
		username,	//	用户名
		password,	//	密码
		salt,		//	用户身份类型
		createDate; //	添加日期
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return "AdminBean [id=" + id + ", username=" + username + ", password=" + password + ", salt=" + salt
				+ ", createDate=" + createDate + "]";
	}
	public AdminBean(int id, String username, String password, String salt, String createDate) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.createDate = createDate;
	}
	
	
	
}
