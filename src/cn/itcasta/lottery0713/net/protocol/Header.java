package cn.itcasta.lottery0713.net.protocol;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.xmlpull.v1.XmlSerializer;

import cn.itcasta.lottery0713.ConstantValue;

/**
 * 封装的Header（包含的是一系列的叶子）
 * 
 * @author Administrator
 * 
 */
public class Header {
	// 可以确定值
	private Leaf agenterid = new Leaf("agenterid", ConstantValue.AGENTERID);// 代理商的标示
	private Leaf source = new Leaf("source", ConstantValue.SOURCE);// 代理商的标示
	private Leaf compress = new Leaf("compress", ConstantValue.COMPRESS);// 代理商的标示
	// 值，我们在现阶段是无法确认
	private Leaf messengerid = new Leaf("messengerid");// 标示xmlid
	private Leaf timestamp = new Leaf("timestamp");// 时间戳
	private Leaf digest = new Leaf("digest");// MD5加密

	// 值，是需要外界来确定（传递）
	private Leaf transactiontype = new Leaf("transactiontype");// 请求的唯一标示
	private Leaf username = new Leaf("username");// 用户名

	public Leaf getTransactiontype() {
		return transactiontype;
	}

	public Leaf getUsername() {
		return username;
	}

	/**
	 * 序列化头
	 */
	public void serializer(XmlSerializer serializer, String body) {
		// 格式化时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = dateFormat.format(new Date());

		// 处理六位的随机数
		Random random = new Random();
		int num = random.nextInt(999999) + 1;// 0~999998 1~999999 000001
		DecimalFormat decimalFormat = new DecimalFormat("000000");

		String numFormat = decimalFormat.format(num);

		// xml id 设置
		messengerid.setTagValue(time + numFormat);
		// 时间戳设置
		timestamp.setTagValue(time);

		// 设置MD5
		// timestamp+子代理商的密码+(<body>......</body>)
		String md5Info = time + ConstantValue.PASSWORD + body;
		String md5Hex = DigestUtils.md5Hex(md5Info);
		digest.setTagValue(md5Hex);

		// header序列化
		try {
			serializer.startTag(null, "header");

			agenterid.serializer(serializer);
			source.serializer(serializer);
			compress.serializer(serializer);

			messengerid.serializer(serializer);
			timestamp.serializer(serializer);
			digest.serializer(serializer);

			transactiontype.serializer(serializer);
			username.serializer(serializer);

			serializer.endTag(null, "header");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Leaf getTimestamp() {
		return timestamp;
	}

	public Leaf getDigest() {
		return digest;
	}
	
	
	

}
