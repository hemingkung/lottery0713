package cn.itcasta.lottery0713.service;

import cn.itcasta.lottery0713.net.protocol.Message;

/**
 * 处理里公共信息
 * @author Administrator
 *
 */
public interface CommonService {
	/**
	 * 获取当前销售期信息
	 * @param lotteryId
	 * @return
	 */
	Message getCurrentIssueInfo(Integer lotteryId);
}
