package cn.itcasta.lottery0713.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import cn.itcasta.lottery0713.ConstantValue;

/**
 * 第一界面
 * @author Administrator
 *
 */
public class FirstView extends BaseView{
	public FirstView(Context context,Bundle bundle) {
		super(context,bundle);
	}




	public View getView()
	{
		//简单界面：
		TextView textView = new TextView(context);

		LayoutParams layoutParams = textView.getLayoutParams();
		layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		textView.setLayoutParams(layoutParams);

		textView.setBackgroundColor(Color.BLUE);
		textView.setText("这是第一个界面");
		
		
		return textView;

	}




	@Override
	public int getId() {
		return ConstantValue.VIEW_FIRST;
	}




	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}




	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		
	}
	
	
}
