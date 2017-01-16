package bean;

public class Vehtotal {
	
	private String province;
	private int total;
	public Vehtotal(String province, int total) {
		super();
		this.province = province;
		this.total = total;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}



}
