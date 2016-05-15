package cn.itcasta.lottery0713.service.impl;

import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;
import cn.itcasta.lottery0713.ConstantValue;
import cn.itcasta.lottery0713.bean.ShoppingCart;
import cn.itcasta.lottery0713.bean.Ticket;
import cn.itcasta.lottery0713.bean.User;
import cn.itcasta.lottery0713.net.HttpClientAdapter;
import cn.itcasta.lottery0713.net.protocol.Message;
import cn.itcasta.lottery0713.net.protocol.element.BalanceElement;
import cn.itcasta.lottery0713.net.protocol.element.BettingElement;
import cn.itcasta.lottery0713.net.protocol.element.UserLoginElement;
import cn.itcasta.lottery0713.service.BaseService;
import cn.itcasta.lottery0713.service.UserService;
import cn.itcasta.lottery0713.util.DES;

public class UserServiceImpl extends BaseService implements UserService {
	public Message login(User user) {
		// 1、生成用户登陆请求（UserLoginElement）
		UserLoginElement element = new UserLoginElement();
		element.getActpassword().setTagValue(user.getPassword());

		Message result = getResult(user, element);

		if (result != null) {
			// MD5验证通过了
			// 解析body
			// 第二次解析（解析内容是body信息）
			XmlPullParser parser = Xml.newPullParser();

			try {
				parser.setInput(new StringReader(result.getBody().getBodyInfo()));

				int eventType = parser.getEventType();
				while (eventType != XmlPullParser.END_DOCUMENT) {
					String name = "";
					switch (eventType) {
						case XmlPullParser.START_TAG:
							name = parser.getName();
							if ("errorcode".equalsIgnoreCase(name)) {
								result.getBody().getOelement().setErrorcode(parser.nextText());
							}
							if ("errormsg".equalsIgnoreCase(name)) {
								result.getBody().getOelement().setErrormsg(parser.nextText());
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

	/**
	 * 用户登陆
	 */
	public Message login1(User user) {
		// 1、生成用户登陆请求（UserLoginElement）
		UserLoginElement element = new UserLoginElement();
		element.getActpassword().setTagValue(user.getPassword());

		Message message = new Message();
		message.getHeader().getUsername().setTagValue(user.getUsername());
		// 2、拿到xml
		String xml = message.getXml(element);
		// 3、建立连接，发送xml
		HttpClientAdapter adapter = new HttpClientAdapter();
		InputStream is = adapter.sendPostRequest(ConstantValue.URL_LOTTERY, xml);

		Message result = new Message();
		if (is != null) {

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

			// 校验MD5
			DES des = new DES();
			String desInfo = "<body>" + des.authcode(result.getBody().getDesBody(), "ENCODE", ConstantValue.DES_PASSWORD) + "</body>";

			String md5Info = result.getHeader().getTimestamp().getTagValue() + ConstantValue.PASSWORD + desInfo;
			String md5Hex = DigestUtils.md5Hex(md5Info);

			if (md5Hex.equals(result.getHeader().getDigest().getTagValue())) {
				// MD5验证通过了
				// 解析body
				// 第二次解析（解析内容是body信息）
				parser = Xml.newPullParser();

				try {
					parser.setInput(new StringReader(desInfo));

					int eventType = parser.getEventType();
					while (eventType != XmlPullParser.END_DOCUMENT) {
						String name = "";
						switch (eventType) {
							case XmlPullParser.START_TAG:
								name = parser.getName();
								if ("errorcode".equalsIgnoreCase(name)) {
									result.getBody().getOelement().setErrorcode(parser.nextText());
								}
								if ("errormsg".equalsIgnoreCase(name)) {
									result.getBody().getOelement().setErrormsg(parser.nextText());
								}

								break;
						}
						eventType = parser.next();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		return result;
	}

	@Override
	public Message balance(User user) {

		/*
		 * accountname string 32 账户名称 accountid string 32 账户ID
		 * 
		 * accounvalues string * 账户金额 investvalues 可投注金额 cashvalues 可提现金额
		 */
		// 创建一个获取用户余额Element
		BalanceElement element = new BalanceElement();

		Message result = getResult(user, element);

		if (result != null) {
			// MD5验证通过了
			// 解析body
			// 第二次解析（解析内容是body信息）
			XmlPullParser parser = Xml.newPullParser();

			try {
				parser.setInput(new StringReader(result.getBody().getBodyInfo()));

				int eventType = parser.getEventType();
				while (eventType != XmlPullParser.END_DOCUMENT) {
					String name = "";
					switch (eventType) {
						case XmlPullParser.START_TAG:
							name = parser.getName();
							if ("errorcode".equalsIgnoreCase(name)) {
								result.getBody().getOelement().setErrorcode(parser.nextText());
							}
							if ("errormsg".equalsIgnoreCase(name)) {
								result.getBody().getOelement().setErrormsg(parser.nextText());
							}

							if ("element".equals(name)) {
								result.getBody().getElements().add(element);
							}

							if ("investvalues".equalsIgnoreCase(name)) {
								element.setInvestvalues(parser.nextText());
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

	@Override
	public Message betting(User user) {
		BettingElement element = new BettingElement();
		// 设置参数

		element.getLotteryid().setTagValue(ShoppingCart.getInstance().getLotteryid().toString());
		element.getIssue().setTagValue(ShoppingCart.getInstance().getIssue());
		element.getLotteryvalue().setTagValue((ShoppingCart.getInstance().getMoney() * 100) + "");

		// 设置的用户的投注（票的信息）
		List<Ticket> tickets = ShoppingCart.getInstance().getTickets();
		StringBuilder builder = new StringBuilder();
		for (Ticket item : tickets) {
			builder.append("^").append(item.getRed().replaceAll(" ", "")).append("|").append(item.getBlue().replaceAll(" ", ""));
		}

		element.getLotterycode().setTagValue(builder.toString().substring(1));

		element.getAppnumbers().setTagValue(ShoppingCart.getInstance().getAppnumbers().toString());
		element.getIssuesnumbers().setTagValue(ShoppingCart.getInstance().getIssuesnumbers().toString());
		element.getLotterynumber().setTagValue(ShoppingCart.getInstance().getLotterynumber().toString());

		element.getIssueflag().setTagValue(ShoppingCart.getInstance().getIssuesnumbers() == 1 ? "0" : "1");

		Message result = getResult(user, element);
		if (result != null) {
			// MD5验证通过了
			// 解析body
			// 第二次解析（解析内容是body信息）
			XmlPullParser parser = Xml.newPullParser();

			try {
				parser.setInput(new StringReader(result.getBody().getBodyInfo()));

				int eventType = parser.getEventType();
				while (eventType != XmlPullParser.END_DOCUMENT) {
					String name = "";
					switch (eventType) {
						case XmlPullParser.START_TAG:
							name = parser.getName();
							if ("errorcode".equalsIgnoreCase(name)) {
								result.getBody().getOelement().setErrorcode(parser.nextText());
							}
							if ("errormsg".equalsIgnoreCase(name)) {
								result.getBody().getOelement().setErrormsg(parser.nextText());
							}

							if ("element".equals(name)) {
								result.getBody().getElements().add(element);
							}

							if ("tradevalue".equals(name)) {
								element.setTradevalue(parser.nextText());
							}
							if ("actvalue".equals(name)) {
								element.setActvalue(parser.nextText());
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
