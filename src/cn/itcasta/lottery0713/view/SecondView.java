package cn.itcasta.lottery0713.view;

import cn.itcasta.lottery0713.ConstantValue;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

/**
 * 第二个界面
 * 
 * @author Administrator
 * 
 */
public class SecondView extends BaseView {
	private TextView textView;

	public SecondView(Context context,Bundle bundle) {
		super(context,bundle);
		init();
	}

	protected void init() {
		// 简单界面：
		textView = new TextView(context);

		LayoutParams layoutParams = textView.getLayoutParams();
		layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		textView.setLayoutParams(layoutParams);

		textView.setBackgroundColor(Color.GREEN);
		textView.setText("这是第二个界面");
	}

	public View getView() {
		return textView;
	}

	@Override
	public int getId() {
		return ConstantValue.VIEW_SECOND;
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}
}
