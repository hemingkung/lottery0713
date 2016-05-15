package cn.itcasta.lottery0713.service;

import java.io.InputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;
import cn.itcasta.lottery0713.ConstantValue;
import cn.itcasta.lottery0713.bean.User;
import cn.itcasta.lottery0713.net.HttpClientAdapter;
import cn.itcasta.lottery0713.net.protocol.Element;
import cn.itcasta.lottery0713.net.protocol.Message;
import cn.itcasta.lottery0713.util.DES;

public abstract class BaseService {
	/**
	 * 返回Md5验证通过后的结果 
	 * @param user
	 * @param element
	 * @return
	 */
	protected Message getResult(User user,Element element)
	{
		String xml = getXml(user,element);
		InputStream is = sendRequest(xml);
		if(is!=null)
		{
			Message result = firstParser(is);
			boolean checkMd5 = checkMd5(result);
			if(checkMd5)
			{
				return result;
			}
		}
		return null;
	}
	
	
	/**
	 * 2、拿到xml
	 * @param user
	 * @param element
	 * @return
	 */
	protected String getXml(User user,Element element){
		Message message = new Message();
		if(user!=null)
		{
			message.getHeader().getUsername().setTagValue(user.getUsername());
		}
		return message.getXml(element);
		
	}
	
	/**
	 * 3、建立连接，发送xml
	 * @param xml
	 * @return
	 */
	protected InputStream sendRequest(String xml)
	{
		HttpClientAdapter adapter = new HttpClientAdapter();
		return adapter.sendPostRequest(ConstantValue.URL_LOTTERY, xml);

	}
	/**
	 * 第一次解析
	 * @param is
	 * @return
	 */
	protected Message firstParser(InputStream is)
	{
		Message result=new Message();
		// 第一解析
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(is, "utf-8");

			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String name = "";
				switch (eventType) {
					case XmlPullParser.START_TAG:
						name = parser.getName();

						if ("timestamp".equalsIgnoreCase(name)) {
							result.getHeader().getTimestamp().setTagValue(parser.nextText());
						}

						if ("digest".equalsIgnoreCase(name)) {
							result.getHeader().getDigest().setTagValue(parser.nextText());
						}

						if ("body".equalsIgnoreCase(name)) {
							result.getBody().setDesBody(parser.nextText());
						}
						break;
				}

				eventType = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;

	}
	/**
	 * MD5信息验证
	 * @param result
	 * @return
	 */
	protected boolean checkMd5(Message result)
	{
		// 校验MD5
		DES des = new DES();
		String desInfo = "<body>" + des.authcode(result.getBody().getDesBody(), "ENCODE", ConstantValue.DES_PASSWORD) + "</body>";

		String md5Info = result.getHeader().getTimestamp().getTagValue() + ConstantValue.PASSWORD + desInfo;
		String md5Hex = DigestUtils.md5Hex(md5Info);

		if (md5Hex.equals(result.getHeader().getDigest().getTagValue())) {
			result.getBody().setBodyInfo(desInfo);
			return true;
		}
		return false;
	}
	
}
