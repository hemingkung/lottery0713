package cn.itcasta.lottery0713.net.protocol.element;

import org.xmlpull.v1.XmlSerializer;

import cn.itcasta.lottery0713.net.protocol.Element;
/**
 * 获取用户余额请求
 * @author Administrator
 *
 */
public class BalanceElement extends Element {
	private String investvalues;//用户余额

	@Override
	public String getTransactiontype() {
		return "11007";
	}

	@Override
	public void serializer(XmlSerializer serializer) {

	}

	public String getInvestvalues() {
		return investvalues;
	}

	public void setInvestvalues(String investvalues) {
		this.investvalues = investvalues;
	}
	
	

}
