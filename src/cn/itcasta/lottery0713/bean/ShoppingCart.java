package cn.itcasta.lottery0713.bean;

import java.util.ArrayList;
import java.util.List;

import cn.itcasta.lottery0713.ConstantValue;
import cn.itcasta.lottery0713.GloableParams;

/**
 * 购物车
 * 
 * @author Administrator
 * 
 */
public class ShoppingCart {
	private static ShoppingCart instance = new ShoppingCart();

	private ShoppingCart() {
	}

	public static ShoppingCart getInstance() {
		return instance;
	}

	/*
	 * lotteryid string * 玩法编号 issue string * 期号（当前销售期） lotterycode string * 投注号码，注与注之间^分割 lotterynumber string 注数 lotteryvalue string 方案金额，以分为单位
	 * appnumbers string 倍数 issuesnumbers string 追期 issueflag int * 是否多期追号 0否，1多期 bonusstop int * 中奖后是否停止：0不停，1停
	 */

	private List<Ticket> tickets = new ArrayList<Ticket>();// 用于存储用户的投注信息

	private Integer lotteryid;// 玩法编号
	private String issue;// 期号（当前销售期）
	private Integer lotterynumber;// 当前购物车中总注数

	private Integer appnumbers = 1;// 倍数（倍投）
	private Integer issuesnumbers = 1;// 追期

	/**
	 * 操作用户的倍投
	 * @param isAdd  标示：是否是增加倍投
	 * @return
	 */
	public boolean addAppnumbers(boolean isAdd) {
		if (isAdd) {
			appnumbers++;
			if (appnumbers > 99) {
				appnumbers--;
				return false;
			}
			
			// 校验用于的余额
			if (GloableParams.money < getMoney()) {
				appnumbers--;
				return false;
			}
			
		} else {
			appnumbers--;
			if (appnumbers < 1) {
				appnumbers++;
				return false;
			}
		}
		return true;
	}
	/**
	 * 操作用户的追期
	 * @param isAdd  标示：是否是增加追期
	 * @return
	 */
	public boolean addissuesnumbers(boolean isAdd) {
		if (isAdd) {
			issuesnumbers++;
			if (issuesnumbers > 99) {
				issuesnumbers--;
				return false;
			}

			// 校验用于的余额
			if (GloableParams.money < getMoney()) {
				issuesnumbers--;
				return false;
			}

		} else {
			issuesnumbers--;
			if (issuesnumbers < 1) {
				issuesnumbers++;
				return false;
			}
		}
		return true;
	}

	// 获取方案金额
	public Integer getMoney() {
		return getLotterynumber() * 2 * appnumbers * issuesnumbers;
	}

	public Integer getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Integer lotteryid) {
		this.lotteryid = lotteryid;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public Integer getLotterynumber() {
		lotterynumber = 0;
		for (Ticket item : tickets) {
			lotterynumber += item.getNum();
		}

		return lotterynumber;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	/**
	 * 添加机选
	 */
	public void addTicket() {
		// 判断当前购物车里放置的是哪个玩法投注信息
		switch (lotteryid.intValue()) {
			case ConstantValue.SSQ:
				// 随机生成一注双色球的号码 PlaySSQ 摇啊摇 随机生成一注
				break;

			default:
				break;
		}

	}

	public Integer getAppnumbers() {
		return appnumbers;
	}

	public void setAppnumbers(Integer appnumbers) {
		this.appnumbers = appnumbers;
	}

	public Integer getIssuesnumbers() {
		return issuesnumbers;
	}

	public void setIssuesnumbers(Integer issuesnumbers) {
		this.issuesnumbers = issuesnumbers;
	}

}
