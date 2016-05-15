package cn.itcasta.lottery0713.service;

import cn.itcasta.lottery0713.bean.User;
import cn.itcasta.lottery0713.net.protocol.Message;

public interface UserService {
	/**
	 * 用户登陆
	 * @param user
	 * @return
	 */
	Message login(User user);
	/**
	 * 获取用户的余额
	 * @return
	 */
	Message balance(User user);
	/**
	 * 用户的投注
	 * @param user
	 * @return
	 */
	Message betting(User user);
}
