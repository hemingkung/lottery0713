package cn.itcasta.lottery0713.service.impl;

import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

import cn.itcasta.lottery0713.net.protocol.Message;
import cn.itcasta.lottery0713.net.protocol.element.CurrentIssueElement;
import cn.itcasta.lottery0713.service.BaseService;
import cn.itcasta.lottery0713.service.CommonService;

public class CommonServiceImpl extends BaseService implements CommonService {

	@Override
	public Message getCurrentIssueInfo(Integer lotteryId) {
		CurrentIssueElement element = new CurrentIssueElement();
		element.getLotteryid().setTagValue(lotteryId.toString());

		Message result = getResult(null, element);
		if (result != null) {
			String bodyInfo = result.getBody().getBodyInfo();
			XmlPullParser parser = Xml.newPullParser();
			try {
				parser.setInput(new StringReader(bodyInfo));

				int eventType = parser.getEventType();
				while (eventType != XmlPullParser.END_DOCUMENT) {
					String name = "";
					switch (eventType) {
						case XmlPullParser.START_TAG:
							name = parser.getName();
							if ("errorcode".equals(name)) {
								result.getBody().getOelement().setErrorcode(parser.nextText());
							}
							if ("errormsg".equals(name)) {
								result.getBody().getOelement().setErrormsg(parser.nextText());
							}
							if ("element".equals(name)) {
//								// 放置的处理回复
//								result.getBody().getElements().remove(0);
								result.getBody().getElements().add(element);
							}

							if ("lotteryname".equals(name)) {
								element.setLotteryname(parser.nextText());
							}
							if ("issue".equals(name)) {
								element.setIssue(parser.nextText());
							}
							if ("lasttime".equals(name)) {
								element.setLasttime(parser.nextText());
							}

							break;

					}

					eventType = parser.next();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return result;
	}

}
