package cn.itcasta.lottery0713.view;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import cn.itcasta.lottery0713.ConstantValue;
import cn.itcasta.lottery0713.GloableParams;
import cn.itcasta.lottery0713.R;
import cn.itcasta.lottery0713.bean.ShoppingCart;
import cn.itcasta.lottery0713.bean.Ticket;
import cn.itcasta.lottery0713.bean.User;
import cn.itcasta.lottery0713.net.protocol.Message;
import cn.itcasta.lottery0713.net.protocol.Oelement;
import cn.itcasta.lottery0713.net.protocol.element.BettingElement;
import cn.itcasta.lottery0713.service.UserService;
import cn.itcasta.lottery0713.util.BeanFactory;
import cn.itcasta.lottery0713.view.manager.PromptManager;
import cn.itcasta.lottery0713.view.manager.UiManager;

/**
 * 设置追期和倍投的界面
 * 
 * @author Administrator
 * 
 */
public class PreBeting extends BaseView {

	private ListView shoppingCartList;// 用户选号展示
	private TextView lotteryNumView;// 注数
	private TextView lotteryMoneyView;// 方案金额和用户余额

	// 倍投
	private Button addAppnumbers;// 增加倍投
	private TextView appnumbers;// 倍投信息
	private Button subAppnumbers;// 减少倍投
	// 期号
	private Button addIssueflagNum;// 增加追期
	private TextView issueflagNum;// 追期信息
	private Button subIssueflagNum;// 减少追期

	private ImageButton betButton;// 投注

	private ShoppingListAdapter adapter;

	public PreBeting(Context context, Bundle bundle) {
		super(context, bundle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getId() {
		return ConstantValue.VIEW_PREBETTING;
	}

	@Override
	protected void init() {
		container = (ViewGroup) inflater.inflate(R.layout.il_play_prefectbetting, null);

		shoppingCartList = (ListView) container.findViewById(R.id.ii_lottery_shopping_list);
		lotteryNumView = (TextView) container.findViewById(R.id.ii_shopping_list_betting_num);
		lotteryMoneyView = (TextView) container.findViewById(R.id.ii_shopping_list_betting_money);
		addAppnumbers = (Button) container.findViewById(R.id.ii_add_appnumbers);
		subAppnumbers = (Button) container.findViewById(R.id.ii_sub_appnumbers);
		addIssueflagNum = (Button) container.findViewById(R.id.ii_add_issueflagNum);
		subIssueflagNum = (Button) container.findViewById(R.id.ii_sub_issueflagNum);

		appnumbers = (TextView) container.findViewById(R.id.ii_appnumbers);
		issueflagNum = (TextView) container.findViewById(R.id.ii_issueflagNum);

		betButton = (ImageButton) container.findViewById(R.id.ii_lottery_purchase);

		adapter = new ShoppingListAdapter();
		shoppingCartList.setAdapter(adapter);
	}

	@Override
	protected void setListener() {
		addIssueflagNum.setOnClickListener(this);
		addAppnumbers.setOnClickListener(this);
		subIssueflagNum.setOnClickListener(this);
		subAppnumbers.setOnClickListener(this);
		betButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ii_add_appnumbers:
				// 倍投的增加
				boolean result = ShoppingCart.getInstance().addAppnumbers(true);
				if (result) {
					changeNotice();
				} else {
					PromptManager.showToast(context, "用户余额不足或倍投不能大于99");
				}

				break;
			case R.id.ii_sub_appnumbers:
				// 倍投的减少
				result = ShoppingCart.getInstance().addAppnumbers(false);
				if (result) {
					changeNotice();
				}

				break;
			case R.id.ii_add_issueflagNum:
				// 追期的增加
				result=ShoppingCart.getInstance().addissuesnumbers(true);
				if (result) {
					changeNotice();
				} else {
					PromptManager.showToast(context, "用户余额不足或追期不能大于99");
				}
				break;
			case R.id.ii_sub_issueflagNum:
				// 追期的减少
				result=ShoppingCart.getInstance().addissuesnumbers(false);
				if (result) {
					changeNotice();
				}
				break;
			case R.id.ii_lottery_purchase:
				// 购买
				User user=new User();
				user.setUsername(GloableParams.username);
				new HttpTask<User, Message>() {

					@Override
					protected Message doInBackground(User... params) {
						User user=params[0];
						UserService service=BeanFactory.getImpl(UserService.class);
						Message result=service.betting(user);
						return result;
					}

					@Override
					protected void onPostExecute(Message result) {
						if(result!=null)
						{
							Oelement oelement=result.getBody().getOelement();
							if("0".equals(oelement.getErrorcode()))
							{
								//清空栈（返回键处理）
								UiManager.getInstance().clearCache();
								//TODO 切换界面
								UiManager.getInstance().changeView(Hall.class, null);
								//提示用户
								BettingElement element=(BettingElement) result.getBody().getElements().get(0);
								PromptManager.showErrorDialog(context, "投注成功，扣除："+element.getTradevalue()+"元");
								GloableParams.money=Float.parseFloat(element.getActvalue());
							}
						}
						super.onPostExecute(result);
					}
					
					
				}.executeProxy(user);

				break;
		}
	}

	/**
	 * 用户的提示信息
	 */
	private void changeNotice() {

		// 注数提示信息
		String pourInfo = context.getResources().getString(R.string.is_shopping_list_betting_num);
		Integer num = ShoppingCart.getInstance().getLotterynumber();

		pourInfo = StringUtils.replace(pourInfo, "NUM", num.toString());

		lotteryNumView.setText(Html.fromHtml(pourInfo));

		// 金额的提示信息

		String moneyInfo = context.getResources().getString(R.string.is_shopping_list_betting_money);
		Integer money = ShoppingCart.getInstance().getMoney();
		moneyInfo = StringUtils.replaceEach(moneyInfo, new String[] { "MONEY1", "MONEY2" },
				new String[] { money.toString(), GloableParams.money + "" });
		lotteryMoneyView.setText(Html.fromHtml(moneyInfo));
		
		
		appnumbers.setText(ShoppingCart.getInstance().getAppnumbers().toString());
		issueflagNum.setText(ShoppingCart.getInstance().getIssuesnumbers().toString());

	}

	@Override
	public void onResume() {
		changeNotice();
		super.onResume();
	}

	private class ShoppingListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return ShoppingCart.getInstance().getTickets().size();
		}

		@Override
		public Object getItem(int position) {
			return ShoppingCart.getInstance().getTickets().get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if (convertView == null) {
				holder = new Holder();
				convertView = View.inflate(context, R.layout.il_play_prefectbetting_row, null);
				holder.redsTextView = (TextView) convertView.findViewById(R.id.ii_shopping_item_reds);
				holder.bluesTextView = (TextView) convertView.findViewById(R.id.ii_shopping_item_blues);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			Ticket ticket = ShoppingCart.getInstance().getTickets().get(position);
			holder.redsTextView.setText(ticket.getRed());
			holder.bluesTextView.setText(ticket.getBlue());

			return convertView;
		}

		private class Holder {
			TextView redsTextView;
			TextView bluesTextView;
		}

	}

}
