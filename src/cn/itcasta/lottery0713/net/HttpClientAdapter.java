package cn.itcasta.lottery0713.net;

import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import cn.itcasta.lottery0713.GloableParams;

public class HttpClientAdapter {
	private HttpClient client;
	private HttpPost post;

	public HttpClientAdapter() {
		client = new DefaultHttpClient();

		if (StringUtils.isNotBlank(GloableParams.PROXY_IP)) {
			// 设置代理信息
			HttpHost host = new HttpHost(GloableParams.PROXY_IP, GloableParams.PROXY_PORT);
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, host);
		}
	}

	/**
	 * 发送一个post请求
	 * 
	 * @param uri
	 *            服务器的连接
	 * @param xml
	 *            发送信息
	 */
	public InputStream sendPostRequest(String uri, String xml) {
		// 明确Post、Get，设置URL参数
		post = new HttpPost(uri);// ConstantValue.URL_LOTTERY
		StringEntity entity;
		try {
			// 使用到的是Post，设置发送内容
			entity = new StringEntity(xml);
			post.setEntity(entity);
			HttpResponse response = client.execute(post);

			// 服务器会送状态码判断（200）
			if (response.getStatusLine().getStatusCode() == 200) {
				return response.getEntity().getContent();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
