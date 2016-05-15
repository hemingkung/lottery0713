package cn.itcasta.lottery0713.net.protocol.element;

import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

import cn.itcasta.lottery0713.net.protocol.Element;
import cn.itcasta.lottery0713.net.protocol.Leaf;

public class BettingElement extends Element {
	
	
	/**
	 * lotteryid string * 玩法编号
issue string * 期号（当前销售期）
lotterycode string * 投注号码，注与注之间^分割
lotterynumber string  注数
lotteryvalue string  方案金额，以分为单位
appnumbers string  倍数
issuesnumbers string  追期
issueflag int * 是否多期追号 0否，1多期
bonusstop int * 中奖后是否停止：0不停，1停

	 */
	
	private Leaf lotteryid=new Leaf("lotteryid");
	private Leaf issue = new Leaf("issue");// 期号（当前销售期）
	private Leaf lotteryvalue = new Leaf("lotteryvalue");// 方案金额，以分为单位
	private Leaf lotterynumber = new Leaf("lotterynumber");// 注数
	private Leaf appnumbers = new Leaf("appnumbers");// 倍数
	private Leaf issuesnumbers = new Leaf("issuesnumbers");// 追期
	private Leaf lotterycode = new Leaf("lotterycode");// 投注号码，注与注之间^分割
	private Leaf issueflag = new Leaf("issueflag");// 是否多期追号 0否，1多期
	private Leaf bonusstop = new Leaf("bonusstop", "1");// 中奖后是否停止：0不停，1停

	
	
	/******************************封装解析的结果***********************************/
/*	tradevalue	string	*	实际扣费金额
	actvalue	int	*	用户账户余额
*/
	
	private String tradevalue;
	private String actvalue;
	
	
	

	public String getTradevalue() {
		return tradevalue;
	}

	public void setTradevalue(String tradevalue) {
		this.tradevalue = tradevalue;
	}

	public String getActvalue() {
		return actvalue;
	}

	public void setActvalue(String actvalue) {
		this.actvalue = actvalue;
	}

	@Override
	public String getTransactiontype() {
		return "12006";
	}

	@Override
	public void serializer(XmlSerializer serializer) {
		try {
			serializer.startTag(null, "element");
			lotteryid.serializer(serializer);
			issue.serializer(serializer);
			lotteryvalue.serializer(serializer);
			lotterynumber.serializer(serializer);
			appnumbers.serializer(serializer);
			issuesnumbers.serializer(serializer);
			lotterycode.serializer(serializer);
			issueflag.serializer(serializer);
			bonusstop.serializer(serializer);
			serializer.endTag(null, "element");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	public Leaf getLotteryid() {
		return lotteryid;
	}

	public Leaf getIssue() {
		return issue;
	}

	public Leaf getLotteryvalue() {
		return lotteryvalue;
	}

	public Leaf getLotterynumber() {
		return lotterynumber;
	}

	public Leaf getAppnumbers() {
		return appnumbers;
	}

	public Leaf getIssuesnumbers() {
		return issuesnumbers;
	}

	public Leaf getLotterycode() {
		return lotterycode;
	}

	public Leaf getIssueflag() {
		return issueflag;
	}

	public Leaf getBonusstop() {
		return bonusstop;
	}
	
	
	

}
