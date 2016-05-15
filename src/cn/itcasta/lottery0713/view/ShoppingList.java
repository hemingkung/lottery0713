package cn.itcasta.lottery0713.view;

import cn.itcasta.lottery0713.ConstantValue;
import cn.itcasta.lottery0713.GloableParams;
import cn.itcasta.lottery0713.R;
import cn.itcasta.lottery0713.bean.ShoppingCart;
import cn.itcasta.lottery0713.bean.Ticket;
import cn.itcasta.lottery0713.view.manager.PromptManager;
import cn.itcasta.lottery0713.view.manager.UiManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class ShoppingList extends BaseView {
	private Button ii_add_optional;// 添加自选号码
	private Button ii_add_random;// 随机生成一注
	private ImageButton ii_shopping_list_clear;// 清空列表

	private Button ii_lottery_shopping_buy;// 购买

	private TextView ii_shopping_lottery_notice;// 提示信息

	private ListView ii_shopping_list;// 封装所有用户选择的投注
	private ShoppingAdapter adapter;

	public ShoppingList(Context context, Bundle bundle) {
		super(context, bundle);
	}

	@Override
	public int getId() {
		return ConstantValue.VIEW_SHOPPING;
	}

	@Override
	protected void init() {
		container = (ViewGroup) inflater.inflate(R.layout.il_shopping_trolley2, null);
		ii_add_optional = (Button) findViewById(R.id.ii_add_optional);
		ii_add_random = (Button) findViewById(R.id.ii_add_random);
		ii_shopping_list_clear = (ImageButton) findViewById(R.id.ii_shopping_list_clear);
		ii_lottery_shopping_buy = (Button) findViewById(R.id.ii_lottery_shopping_buy);

		ii_shopping_lottery_notice = (TextView) findViewById(R.id.ii_shopping_lottery_notice);
		ii_shopping_list = (ListView) findViewById(R.id.ii_shopping_list);

		adapter = new ShoppingAdapter();
		ii_shopping_list.setAdapter(adapter);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ii_add_optional:
				// 添加自选号码
				UiManager.getInstance().changeCache();
				break;
			case R.id.ii_add_random:
				// 添加机选号码
				ShoppingCart.getInstance().addTicket();
				adapter.notifyDataSetChanged();
				break;
			case R.id.ii_shopping_list_clear:
				// 清空
				ShoppingCart.getInstance().getTickets().clear();
				adapter.notifyDataSetChanged();
				break;
			case R.id.ii_lottery_shopping_buy:
				// 购买
				// 判断当前用户是否登陆                         跳转到登陆界面
				if(!GloableParams.isLogin)
				{
					PromptManager.showToast(context, "您未登陆");
					//跳转到用户登陆的界面
					UiManager.getInstance().changeView(UserLogin.class, null);
				}else
				{
					// 判断当前用户的余额是否满足用户的投注                         跳转到充值界面
					if(GloableParams.money<ShoppingCart.getInstance().getMoney())
					{
						PromptManager.showToast(context, "当前的余额不足");
					}else
					{
						//跳转到设置追期和倍投的界面
						UiManager.getInstance().changeView(PreBeting.class, null);
					}
				}
				

				break;
		}
		super.onClick(v);
	}

	@Override
	protected void setListener() {
		ii_lottery_shopping_buy.setOnClickListener(this);

	}

	private class ShoppingAdapter extends BaseAdapter {

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
		public View getView(final int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.il_shopping_row, null);
				holder = new Holder();
				holder.deleteButton = (ImageButton) convertView.findViewById(R.id.ii_shopping_item_delete);
				holder.redsTextView = (TextView) convertView.findViewById(R.id.ii_shopping_item_reds);
				holder.bluesTextView = (TextView) convertView.findViewById(R.id.ii_shopping_item_blues);
				holder.moneyDetailsTextView = (TextView) convertView.findViewById(R.id.ii_shopping_item_money);

				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			Ticket ticket = ShoppingCart.getInstance().getTickets().get(position);

			holder.redsTextView.setText(ticket.getRed());
			holder.bluesTextView.setText(ticket.getBlue());

			holder.moneyDetailsTextView.setText(ticket.getNum() + "注");

			holder.deleteButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ShoppingCart.getInstance().getTickets().remove(position);

				}
			});

			return convertView;
		}

		private class Holder {
			ImageButton deleteButton;
			TextView redsTextView;
			TextView bluesTextView;
			TextView moneyDetailsTextView;
		}

	}

}
