package cn.itcasta.lottery0713.net.protocol;

import java.io.IOException;
import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

/**
 * 封装的message
 * @author Administrator
 *
 */
public class Message {
	private Header header=new Header();//头
	private Body body=new Body();//体
	public Header getHeader() {
		return header;
	}
	public Body getBody() {
		return body;
	}
	
	public void serializer(XmlSerializer serializer)
	{
		//序列化根节点
		try {
			serializer.startTag(null, "message");
			serializer.attribute(null,"version", "1.0");
			//已经添加了请求
			Element element = body.getElements().get(0);
			//请求的标示（唯一标定一个请求）
			String transactiontype = element.getTransactiontype();
			
			header.getTransactiontype().setTagValue(transactiontype);
			header.serializer(serializer,body.getBody());
			
			serializer.startTag(null, "body");
			serializer.text(body.getDESBody());
			serializer.endTag(null, "body");
			
			
			serializer.endTag(null, "message");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 返回现阶段处理后的xml
	 * 获取指定请求的xml（登陆请求）
	 * @return
	 */
	public String getXml(Element element)
	{
		XmlSerializer serializer=Xml.newSerializer();
		StringWriter writer=new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("utf-8", null);
			
			body.getElements().add(element);
			this.serializer(serializer);
			
			serializer.endDocument();
			
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
