package cn.itcasta.lottery0713.view;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import cn.itcasta.lottery0713.util.NetUtil;
import cn.itcasta.lottery0713.view.manager.PromptManager;

public abstract class BaseView implements View.OnClickListener {
	protected Context context;
	protected LayoutInflater inflater;
	protected static Handler handler;

	protected ViewGroup container;// 中间的容器
	
	protected Bundle bundle;

	public static void setHandler(Handler handler) {
		BaseView.handler = handler;
	}

	public BaseView(Context context,Bundle bundle) {
		super();
		this.context = context;
		this.bundle=bundle;
		inflater = LayoutInflater.from(context);
		init();
		setListener();
	}

	/**
	 * 初始化
	 */
	protected abstract void init();

	/**
	 * 设置监听
	 */
	protected abstract void setListener();

	/**
	 * 抽取获取当前View的方法
	 * 
	 * @return
	 */
	public View getView() {
		if (container.getLayoutParams() == null)
			container.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return container;
	}

	/**
	 * 拿到当前容器的唯一标示
	 * 
	 * @return
	 */
	public abstract int getId();

	@Override
	public void onClick(View v) {

	}

	protected View findViewById(int resId) {
		return container.findViewById(resId);
	}

	/**
	 * 定义访问网络任务
	 * 
	 * @author Administrator
	 * 
	 * @param <P>
	 *            代表传递的参数
	 * @param <R>
	 *            代表返回值
	 */
	protected abstract class HttpTask<P, R> extends AsyncTask<P, Integer, R> {
		public AsyncTask<P, Integer, R> executeProxy(P... params) {
			if (NetUtil.checkNet(context)) {
				return execute(params);
			} else {
				PromptManager.showNoNetWork(context);
				return null;
			}
		}

		
		
		
	}

	public void onResume() {

	}

	public void onPause() {

	}

	public void setBundle(Bundle bundle) {
		this.bundle=bundle;
		
	}

}
