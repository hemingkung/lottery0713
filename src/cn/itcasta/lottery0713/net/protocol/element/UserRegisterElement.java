package cn.itcasta.lottery0713.net.protocol.element;

import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

import cn.itcasta.lottery0713.net.protocol.Element;
import cn.itcasta.lottery0713.net.protocol.Leaf;

public class UserRegisterElement extends Element {
	
	private Leaf actpassword=new Leaf("actpassword");//
	
	

	public Leaf getActpassword() {
		return actpassword;
	}

	@Override
	public String getTransactiontype() {
		
		return "10001";
	}

	@Override
	public void serializer(XmlSerializer serializer) {
		try {
			serializer.startTag(null, "element");
			actpassword.serializer(serializer);
			serializer.endTag(null, "element");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
