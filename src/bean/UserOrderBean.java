package bean;

import java.util.List;

public class UserOrderBean {
	private int id;
	private List<ProductBean> productBeans;
	
	private AddressBean addressBean;
	private String code,creatDate;
	private float originalPrice,
				  price;
	private String userId,productId;
	private int paymentType;
	private int status,address;
	private UserBean userBean;
	public UserBean getUserBean() {
		return userBean;
	}

	public UserOrderBean(String code, UserBean userBean) {
		this.setCode(code);
		this.setUserBean(userBean);
		}
	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
	public AddressBean getAddressBean() {
		return addressBean;
	}

	public void setAddressBean(AddressBean addressBean) {
		this.addressBean = addressBean;
	}
	
	public List<ProductBean> getProductBeans() {
		return productBeans;
	}

	public void setProductBeans(List<ProductBean> productBeans) {
		this.productBeans = productBeans;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public UserOrderBean() {
		// TODO Auto-generated constructor stub
	}
	
	public UserOrderBean( int  id, String userid,String  code,int status,float originalPrice,float price,int paymentType,String creatDate)
	{
		this.id = id;
		this.userId = userid;
		this.code = code;
		this.status = status;
		
		this.originalPrice = originalPrice;
		this.price = price;
		this.creatDate = creatDate;
		this.paymentType = paymentType;
	}
	
	public int getId() {
		return id;
	}
	@Override
	public String toString() {
		return "UserOrderBean [id=" + id + ", code=" + code + ", address=" + address + ", creatDate=" + creatDate
				+ ", originalPrice=" + originalPrice + ", price=" + price + ", userId=" + userId + ", paymentType="
				+ paymentType + ", status=" + status + "]";
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}

	public float getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(float originalPrice) {
		this.originalPrice = originalPrice;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	

}
