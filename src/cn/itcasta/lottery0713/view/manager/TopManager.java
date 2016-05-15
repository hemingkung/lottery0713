package cn.itcasta.lottery0713.view.manager;

import java.util.Observable;
import java.util.Observer;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.itcasta.lottery0713.ConstantValue;
import cn.itcasta.lottery0713.GloableParams;
import cn.itcasta.lottery0713.R;
import cn.itcasta.lottery0713.view.SecondView;

/**
 * 管理顶部标题（容器）
 * 
 * @author Administrator
 * 
 */
public class TopManager implements Observer{

	protected static final String TAG = "TopManager1";

	// 完成了TopManager的单例
	private TopManager() {
	}

	private static TopManager instance = new TopManager();

	public static TopManager getInstance() {
		return instance;
	}

	/******************************* 容器 ******************************************/
	private RelativeLayout commonTopContaner;// 通用的标题
	private RelativeLayout loginTopContaner;// 登陆标题
	private RelativeLayout unLoginTopContaner;// 未登陆的标题
	/*************************************************************************/
	private ImageButton topReturn;// 返回按钮
	private TextView topTitle;// 标题内容
	private ImageButton topHelp;// 帮助按钮

	private Button registButton;
	private Button loginButton;
	
	/**************** 登录标题 ******************/
	private TextView userInfoTextView;

	public void init(Activity activity) {
		commonTopContaner = (RelativeLayout) activity.findViewById(R.id.ii_top_common_continer);
		unLoginTopContaner = (RelativeLayout) activity.findViewById(R.id.ii_top_unlogin_continer);
		loginTopContaner = (RelativeLayout) activity.findViewById(R.id.ii_top_login_continer);

		topReturn = (ImageButton) activity.findViewById(R.id.ii_top_return);
		topTitle = (TextView) activity.findViewById(R.id.ii_top_title);
		topHelp = (ImageButton) activity.findViewById(R.id.ii_top_help);

		registButton = (Button) activity.findViewById(R.id.ii_top_regist);
		loginButton = (Button) activity.findViewById(R.id.ii_top_login);
		
		userInfoTextView = (TextView) activity.findViewById(R.id.ii_top_user_info);

		setListener();
	}

	/**
	 * 设置按钮的监听
	 */
	private void setListener() {
		topReturn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(TAG, "点击了返回按钮");

			}
		});
		topHelp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(TAG, "点击了帮助按钮");
				boolean changeView = UiManager.getInstance().changeView(SecondView.class,null);
				Log.i(TAG, "result:"+changeView);
			}
		});
		registButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(TAG, "点击了注册按钮");
				boolean changeView = UiManager.getInstance().changeView(SecondView.class,null);
				Log.i(TAG, "result:"+changeView);
			}
		});
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(TAG, "点击了登陆按钮");

			}
		});

	}

	/**
	 * 隐藏所有的标题
	 */
	private void initTitle() {
		commonTopContaner.setVisibility(View.GONE);
		unLoginTopContaner.setVisibility(View.GONE);
		loginTopContaner.setVisibility(View.GONE);
	}

	/**
	 * 显示通用的标题
	 */
	public void showCommonTitle() {
		initTitle();
		commonTopContaner.setVisibility(View.VISIBLE);
	}

	/**
	 * 显示未登录的标题
	 */
	public void showUnLoginTitle() {
		initTitle();
		unLoginTopContaner.setVisibility(View.VISIBLE);
	}

	/**
	 * 显示已经登陆的标题
	 */
	public void showLoginTitle(String userInfo) {
		initTitle();
		loginTopContaner.setVisibility(View.VISIBLE);
		
		// 设置标题内容
		userInfoTextView.setText(userInfo);
	}

	/**
	 * 设置标题
	 * @param resId
	 */
	public void setTopTitle(int resId) {
		topTitle.setText(resId);
	}

	public void setTopTitle(String title) {
		topTitle.setText(title);
	}
	
	/**
	 * 依据中间容器传递过来的信息进行顶部容器的切换
	 */
	@Override
	public void update(Observable observable, Object data) {
		if(data!=null&&StringUtils.isNumeric(data.toString()))
		{
			int id=Integer.parseInt(data.toString());
			switch (id) {
				case ConstantValue.VIEW_HALL:
					if(GloableParams.isLogin)
					{
						//显示用户名称和余额
//						showLoginTitle(userInfo);
					}else
					{
						showUnLoginTitle();
					}
					break;
				case ConstantValue.VIEW_FIRST:
					showUnLoginTitle();
					break;
				case ConstantValue.VIEW_SECOND:
				case ConstantValue.VIEW_SSQ:
				case ConstantValue.VIEW_SHOPPING:
					showCommonTitle();
					break;
				default:
					break;
			}
		}
		
	}

}
