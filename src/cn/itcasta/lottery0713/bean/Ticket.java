package cn.itcasta.lottery0713.bean;

/**
 * 封装当前用户的投注
 * 
 * @author Administrator
 * 
 */
public class Ticket {
	private String red;// 用户选择的红球
	private String blue;// 用户选择的蓝球
	private Integer num;// 注数
	public String getRed() {
		return red;
	}
	public void setRed(String red) {
		this.red = red;
	}
	public String getBlue() {
		return blue;
	}
	public void setBlue(String blue) {
		this.blue = blue;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	
	
	

}
