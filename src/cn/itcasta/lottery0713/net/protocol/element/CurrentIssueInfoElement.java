package cn.itcasta.lottery0713.net.protocol.element;

import org.xmlpull.v1.XmlSerializer;

import cn.itcasta.lottery0713.net.protocol.Element;
import cn.itcasta.lottery0713.net.protocol.Leaf;

public class CurrentIssueInfoElement extends Element {
	private Leaf lotteryid=new Leaf("lotteryid");
	private Leaf issues=new Leaf("issues","1");
	
	/*************服务器回复信息****************/
	private String lotteryname;//玩法名称
	private String issue;//期号
	private String lasttime;//到期结剩余时间（秒）
	
	
	
	@Override
	public String getTransactiontype() {
		return "12002";
	}

	@Override
	public void serializer(XmlSerializer serializer) {
		try {
			serializer.startTag(null, "element");
			lotteryid.serializer(serializer);
			issues.serializer(serializer);
			serializer.endTag(null, "element");
			
		} catch (Exception e) {
		}

	}

	public Leaf getLotteryid() {
		return lotteryid;
	}

	public String getLotteryname() {
		return lotteryname;
	}

	public void setLotteryname(String lotteryname) {
		this.lotteryname = lotteryname;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}
	
	
	
	

}
