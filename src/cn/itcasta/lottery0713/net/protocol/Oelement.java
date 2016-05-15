package cn.itcasta.lottery0713.net.protocol;
/**
 * 服务器回复的信息
 * @author Administrator
 *
 */
public class Oelement {
	/*<errorcode>0</errorcode>
    <errormsg>操作成功</errormsg>*/
	
	private String errorcode;//服务器处理结果的状态值
	private String errormsg;//服务器处理结果的状态描述信息
	
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	public String getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	
	
	
	
	
}
