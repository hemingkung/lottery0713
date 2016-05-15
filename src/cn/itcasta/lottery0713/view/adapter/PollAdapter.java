package cn.itcasta.lottery0713.view.adapter;

import java.text.DecimalFormat;
import java.util.List;

import cn.itcasta.lottery0713.R;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 红球和蓝球
 * @author Administrator
 *
 */
public class PollAdapter extends BaseAdapter {
	private Context context;
	private int endNum;//标示球的截止的号码
	private int ballBGResid;//选中球的背景图片
	
	
	private List<Integer> nums;
	

	public PollAdapter(Context context,int endNum,List<Integer> nums,int ballBGResid) {
		super();
		this.context = context;
		this.endNum=endNum;
		this.nums=nums;
		this.ballBGResid=ballBGResid;
	}

	@Override
	public int getCount() {
		return endNum;
	}

	@Override
	public Object getItem(int position) {
		return position+1;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView ball=new TextView(context);
		DecimalFormat format=new DecimalFormat("00");
		ball.setText(format.format(position+1));
		
		ball.setGravity(Gravity.CENTER);
		ball.setTextColor(R.color.black_slight);
		
		ball.setBackgroundResource(R.drawable.id_defalut_ball);
		
		if(nums.size()>0)
		{
			if(nums.contains(position+1))
			{
				ball.setBackgroundResource(ballBGResid);
			}
		}
		
		
		return ball;
	}

}
