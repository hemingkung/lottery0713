package cn.itcasta.lottery0713.net.protocol;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.xmlpull.v1.XmlSerializer;

/**
 * 所有叶子节点
 * 
 * @author Administrator
 * 
 */
public class Leaf {
	// 人在黑板上画圆 张孝祥老师
	// 画圆的方法在谁身上

	// 叶子节点的标签对应名称
	private String tagName;
	// 叶子节点的标签的值
	private String tagValue;

	// 问题一：为什么没有写无参的构造？
	// 问题二：为什么会有Leaf(String tagName, String tagValue)？

	public Leaf(String tagName, String tagValue) {
		super();
		this.tagName = tagName;
		this.tagValue = tagValue;
	}

	public Leaf(String tagName) {
		super();
		this.tagName = tagName;
	}

	public String getTagValue() {
		return tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public String getTagName() {
		return tagName;
	}

	/**
	 * 序列化叶子
	 */

	public void serializer(XmlSerializer serializer) {
		try {
			serializer.startTag(null, tagName);
			if (StringUtils.isBlank(tagValue)) {
				tagValue = "";
			}
			serializer.text(tagValue);
			serializer.endTag(null, tagName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
