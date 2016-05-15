package cn.itcasta.lottery0713.net.protocol.element;

import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

import cn.itcasta.lottery0713.net.protocol.Element;
import cn.itcasta.lottery0713.net.protocol.Leaf;


/**
 * 用户登陆的请求
 * @author Administrator
 *
 */
public class UserLoginElement extends Element {
	private Leaf actpassword=new Leaf("actpassword");
	/**
	 * 请求的标示
	 */
	@Override
	public String getTransactiontype() {
		
		return "14001";
	}

	@Override
	public void serializer(XmlSerializer serializer) {
		// 序列化相应请求的内容
		try {
			serializer.startTag(null, "element");
			actpassword.serializer(serializer);
			serializer.endTag(null, "element");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Leaf getActpassword() {
		return actpassword;
	}
	
	

}
