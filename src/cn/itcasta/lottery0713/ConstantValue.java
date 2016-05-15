package cn.itcasta.lottery0713;

/**
 * 放置常量的接口
 * 
 * @author Administrator
 * 
 */
public interface ConstantValue {
	/**
	 * 第一个界面
	 */
	int VIEW_FIRST = 0;
	/**
	 * 第二个界面
	 */
	int VIEW_SECOND = 1;
	/**
	 * 购彩大厅的界面
	 */
	int VIEW_HALL = 10;
	/**
	 * 双色球选号界面
	 */
	int VIEW_SSQ = 20;
	/**
	 * 购物车界面
	 */
	int VIEW_SHOPPING = 25;
	/**
	 * 用户登陆界面的标示
	 */
	int VIEW_USER_LOGIN = 30;
	/**
	 * 倍投和追期
	 */
	int VIEW_PREBETTING = 35;
	
	

	/**
	 * 子代理商的标示（代理商）
	 */
	String AGENTERID = "1000002";
	/**
	 * xml文件的来源
	 */
	String SOURCE = "ivr";
	// source=""
	// int ssq=118
	// 密码
	/**
	 * 制定body部分加密的算法
	 */
	String COMPRESS = "DES";
	/**
	 * 子代理商的密码
	 */
	String PASSWORD = "9ab62a694d8bf6ced1fab6acd48d02f8";
	/**
	 * DES加密和解密用到的密钥
	 */
	String DES_PASSWORD = "9b2648fcdfbad80f";
	/**
	 * 彩票服务器连接
	 */
	String URL_LOTTERY = "http://10.0.2.2:8080/ZCWService/Entrance";
	/**
	 * 双色球的标示
	 */
	int SSQ = 118;
	/**
	 * 显示进度框
	 */
	int PROMPT_PROGRESS_SHOW = 0;
	/**
	 * 关闭进度框
	 */
	int PROMPT_PROGRESS_CLOSE = 1;
	
	
	
	
}
