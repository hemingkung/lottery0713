package cn.itcasta.lottery0713;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import cn.itcasta.lottery0713.view.BaseView;
import cn.itcasta.lottery0713.view.Hall;
import cn.itcasta.lottery0713.view.manager.BottomManager;
import cn.itcasta.lottery0713.view.manager.PromptManager;
import cn.itcasta.lottery0713.view.manager.TopManager;
import cn.itcasta.lottery0713.view.manager.UiManager;

public class Lottery0713Activity extends Activity {
	private static final String TAG = "Lottery0713Activity";

	private RelativeLayout middleContainer;// 中间容器
	
	private ProgressDialog dialog;//通用的进度框

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case ConstantValue.PROMPT_PROGRESS_SHOW:
					PromptManager.showSimpleProgressDialog(dialog, "数据加载中……");
					break;
				case ConstantValue.PROMPT_PROGRESS_CLOSE:
					PromptManager.closeProgressDialog(dialog);
					break;

				default:
					break;
			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.il_main);

		// 方式一：直接new （）
		// 方式二：单例
		init();
	}

	private void init() {

		middleContainer = (RelativeLayout) findViewById(R.id.ii_main_middle);

		UiManager.getInstance().setMiddleContainer(middleContainer);
		TopManager.getInstance().init(this);
		BottomManager.getInstrance().init(this);
		
		UiManager.getInstance().addObserver(TopManager.getInstance());
		UiManager.getInstance().addObserver(BottomManager.getInstrance());
		


		TopManager.getInstance().showCommonTitle();

		// FirstView child=new FirstView(this);
		// middleContainer.addView(child.getView());

		UiManager.getInstance().changeView(Hall.class,null);

//		handler.sendEmptyMessageDelayed(0, 2000);
		
		
//		if(!NetUtil.checkNet(this))
//		{
//			PromptManager.showNoNetWork(this);
//		}
		
		dialog=new ProgressDialog(this);
		
		BaseView.setHandler(handler);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 调用一些切换的方法
			boolean changeCache = UiManager.getInstance().changeCache();
			if (changeCache == false) {
				// 退出应用
				Log.i(TAG, "退出应用");
				// 提示用户是否退出（AlertDialog）
				PromptManager.showExitDialog(this);
			}

			return changeCache;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 切换到第二个界面
	 */
	// protected void changeView() {
	// SecondView secondView=new SecondView(this);
	// middleContainer.removeAllViews();
	// View view = secondView.getView();
	// middleContainer.addView(view);
	// view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.ia_view_change));
	//		
	// }

}