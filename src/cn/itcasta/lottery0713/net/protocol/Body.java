package cn.itcasta.lottery0713.net.protocol;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.xmlpull.v1.XmlSerializer;

import cn.itcasta.lottery0713.ConstantValue;
import cn.itcasta.lottery0713.util.DES;

import android.util.Xml;

/**
 * body部分封装
 * 
 * @author Administrator
 * 
 */
public class Body {
	// elements:结合（请求内容）
	private List<Element> elements = new ArrayList<Element>();
	private Oelement oelement = new Oelement();
	
	
	private String desBody;//存储服务器返回的des加密后的信息
	
	private String bodyInfo;//body部分解密之后的信息
	
	
	

	public String getBodyInfo() {
		return bodyInfo;
	}

	public void setBodyInfo(String bodyInfo) {
		this.bodyInfo = bodyInfo;
	}

	public String getDesBody() {
		return desBody;
	}

	public void setDesBody(String desBody) {
		this.desBody = desBody;
	}

	public List<Element> getElements() {
		return elements;
	}

	public Oelement getOelement() {
		return oelement;
	}

	public void setOelement(Oelement oelement) {
		this.oelement = oelement;
	}

	/**
	 * 序列化body
	 * 
	 * @param serializer
	 */
	public void serializer(XmlSerializer serializer) {
		try {
			serializer.startTag(null, "body");
			serializer.startTag(null, "elements");
			// 处理请求（调用了请求的基类里的方法）
			for (Element element : elements) {
				element.serializer(serializer);
			}
			serializer.endTag(null, "elements");
			serializer.endTag(null, "body");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取body部分的内容（包含body标签）
	 * @return
	 */
	public String getBody() {
		XmlSerializer temp = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			temp.setOutput(writer);
			// 没有用到文档的开始的方法
			this.serializer(temp);
			// 文档结束
			temp.flush();
			return writer.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}
	/**
	 * 获取<elements>.....</elements>的DES加密的结果
	 * @return
	 */
	public String getDESBody()
	{
		String bodyInfo=getBody();
		
		String elemengsInfo = StringUtils.substringBetween(bodyInfo, "<body>", "</body>");
		DES des=new DES();
		
		String authcode = des.authcode(elemengsInfo,"DECODE",ConstantValue.DES_PASSWORD);
		
		return authcode;
	}

}
