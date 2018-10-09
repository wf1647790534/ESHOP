package bean;

public class UserBean {
	private int id;
	private String 
		username,	//	用户名
		password,	//	密码
		salt,		//	用户身份类型
		status,     //  用户状态
		createDate, //	创建时间
	 	sex,		//	性别
	 	nickname,	//	昵称
	 	truename,	//	真实姓名
	 	pic;		//	头像


	
	public UserBean(int id, String username, String password, String salt, String status,String createDate,String sex,String nickname,String truename,String pic) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.status = status;
		this.createDate = createDate;
		this.sex = sex;
		this.nickname = nickname;
		this.truename = truename;
		this.pic = pic ;
		
	}
	public UserBean(int id, String username, String status,String createDate,String sex,String nickname,String truename,String pic) {
		this.id = id;
		this.username = username;
		
		
		this.status = status;
		this.createDate = createDate;
		this.sex = sex;
		this.nickname = nickname;
		this.truename = truename;
		this.pic = pic ;
		
	}
	public UserBean(int id, String name,String status,String sex,String nickname,String truename,String pic) {
		this.setId(id);
		this.setUsername(name);
		this.setPic(pic);
		this.setSex(sex);
		this.setNickname(nickname);
		this.setStatus(status);
		this.setTruename(truename);
	}
	
	@Override
	public String toString() {
		return "UserBean [id=" + id + ", username=" + username + ", password=" + password + ", salt=" + salt
				+ ", status=" + status + ", createDate=" + createDate + ", sex=" + sex + ", nickname=" + nickname
				+ ", truename=" + truename + ", pic=" + pic + "]";
	}
	public UserBean() {
		// TODO Auto-generated constructor stub
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getTruename() {
		return truename;
	}
	public void setTruename(String truename) {
		this.truename = truename;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	
	
}
