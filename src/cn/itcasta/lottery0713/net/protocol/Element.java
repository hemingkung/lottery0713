package cn.itcasta.lottery0713.net.protocol;

import org.xmlpull.v1.XmlSerializer;

/**
 * 封装Element标签内信息（1、发送时使用到信息，2、回复时用到信息）
 * @author Administrator
 *
 */
public abstract class Element {
	//70个内容：35个请求、35个回复
	
	//工作肯定是要分出去：
	
	/**
	 * 序列化
	 */
	public abstract void serializer(XmlSerializer serializer);
	/**
	 * 
	 *获取请求标示
	 */
	public abstract String getTransactiontype();
	
}
