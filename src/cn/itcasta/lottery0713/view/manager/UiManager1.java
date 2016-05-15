package cn.itcasta.lottery0713.view.manager;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import cn.itcasta.lottery0713.ConstantValue;
import cn.itcasta.lottery0713.view.BaseView;

/**
 * 管理中间容器（切换界面）
 * 
 * @author Administrator
 * 
 */
public class UiManager1 {
	private static final String TAG = "UiManager1";
	// 单例
	private static UiManager1 instance = new UiManager1();

	private UiManager1() {
	}

	public static UiManager1 getInstance() {
		return instance;
	}

	private RelativeLayout middleContainer;// 中间容器

	public void setMiddleContainer(RelativeLayout middleContainer) {
		this.middleContainer = middleContainer;
	}

	private static Map<String, BaseView> VIEWMAP = new HashMap<String, BaseView>();// key为BaseView子类的简单名称

	private BaseView currentView;// 当前显示的界面

	private static LinkedList<String> VIEWBACK = new LinkedList<String>();// 模拟栈，操作栈顶元素

	/**
	 * 界面切换
	 * 
	 * @param targetView
	 *            切换的目标界面
	 * @return
	 */
	public boolean changeView1(BaseView targetView) {
		middleContainer.removeAllViews();

		middleContainer.addView(targetView.getView());

		return true;
	}

	/**
	 * 界面切换（控制目标界面对象的生成）
	 * 
	 * @param targetView
	 *            切换的目标界面
	 * @return
	 */
	public boolean changeView2(Class<? extends BaseView> targetViewClass) {
		// 放置一个容器，存储已经生成过的BaseView子类对象
		BaseView targetView = null;
		// 判断当前是否含有目标Class的实例
		if (VIEWMAP.containsKey(targetViewClass.getSimpleName())) {
			targetView = VIEWMAP.get(targetViewClass.getSimpleName());
		} else {
			// 如果没有目标Class的实例，创建一个目标Class（SecondeView的实例）
			try {
				Constructor<? extends BaseView> constructor = targetViewClass.getConstructor(Context.class);
				targetView = constructor.newInstance(middleContainer.getContext());
				// 将实例添加到我的容器当中
				VIEWMAP.put(targetViewClass.getSimpleName(), targetView);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		middleContainer.removeAllViews();

		Log.i(TAG, targetView.toString());

		View view = targetView.getView();
		Log.i(TAG, view.toString());

		middleContainer.addView(view);

		return true;
	}

	/**
	 * 界面切换（控制界面切换：如果当前显示的界面是我要切换的目标界面，直接返回fasle）
	 * 
	 * @param targetView
	 *            切换的目标界面
	 * @return
	 */
	public boolean changeView3(Class<? extends BaseView> targetViewClass) {
		// 要有个参照：当前的这个界面
		// 将目标界面与这个参照界面进行比较

		// 存在多少个问题
		// if(currentView!=null&&currentView.getClass().getSimpleName().equals(targetViewClass.getSimpleName()))
		if (currentView != null && currentView.getClass() == targetViewClass) {
			// currentView.getClass().getSimpleName().equals(targetViewClass.getSimpleName())
			// 
			return false;
		}

		// 放置一个容器，存储已经生成过的BaseView子类对象
		BaseView targetView = null;
		// 判断当前是否含有目标Class的实例
		if (VIEWMAP.containsKey(targetViewClass.getSimpleName())) {
			targetView = VIEWMAP.get(targetViewClass.getSimpleName());
		} else {
			// 如果没有目标Class的实例，创建一个目标Class（SecondeView的实例）
			try {
				Constructor<? extends BaseView> constructor = targetViewClass.getConstructor(Context.class);
				targetView = constructor.newInstance(middleContainer.getContext());
				// 将实例添加到我的容器当中
				VIEWMAP.put(targetViewClass.getSimpleName(), targetView);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		middleContainer.removeAllViews();

		Log.i(TAG, targetView.toString());

		View view = targetView.getView();
		Log.i(TAG, view.toString());

		middleContainer.addView(view);
		// 将当前显示的界面付给currentView对象
		currentView = targetView;

		return true;
	}

	/**
	 * 界面切换（添加当前界面到栈顶）
	 * 
	 * @param targetView
	 *            切换的目标界面
	 * @return
	 */
	public boolean changeView(Class<? extends BaseView> targetViewClass) {
		// 要有个参照：当前的这个界面
		// 将目标界面与这个参照界面进行比较

		// 存在多少个问题
		// if(currentView!=null&&currentView.getClass().getSimpleName().equals(targetViewClass.getSimpleName()))
		if (currentView != null && currentView.getClass() == targetViewClass) {
			// currentView.getClass().getSimpleName().equals(targetViewClass.getSimpleName())
			// 
			return false;
		}

		// 放置一个容器，存储已经生成过的BaseView子类对象
		BaseView targetView = null;
		// 判断当前是否含有目标Class的实例
		if (VIEWMAP.containsKey(targetViewClass.getSimpleName())) {
			targetView = VIEWMAP.get(targetViewClass.getSimpleName());
		} else {
			// 如果没有目标Class的实例，创建一个目标Class（SecondeView的实例）
			try {
				Constructor<? extends BaseView> constructor = targetViewClass.getConstructor(Context.class);
				targetView = constructor.newInstance(middleContainer.getContext());
				// 将实例添加到我的容器当中
				VIEWMAP.put(targetViewClass.getSimpleName(), targetView);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		middleContainer.removeAllViews();

		Log.i(TAG, targetView.toString());

		View view = targetView.getView();
		Log.i(TAG, view.toString());

		middleContainer.addView(view);
		// 将当前显示的界面付给currentView对象
		currentView = targetView;

		// 添加当前界面到栈顶
		VIEWBACK.addFirst(targetViewClass.getSimpleName());

		changeTitleandBottom(targetView.getId());
		return true;
	}

	/**
	 * 处理返回按钮
	 * 
	 * @return
	 */
	public boolean changeCache() {
		// 获取当前的栈顶元素，如果与正在显示的界面相同，移除栈顶元素
		// 移除之后，获取新的栈顶元素，用户界面的切换
		String key = "";// 接收栈顶元素
		if (VIEWBACK.size() >= 1) {
			key = VIEWBACK.getFirst();
		}

		if (StringUtils.isNotBlank(key)) {
			if (currentView.getClass().getSimpleName().equals(key)) {
				if (VIEWBACK.size() == 1) {
					return false;
				} else
					VIEWBACK.removeFirst();

				// 获取当前栈顶元素
				return changeCache();
			} else {
				BaseView targetView = VIEWMAP.get(key);
				middleContainer.removeAllViews();
				middleContainer.addView(targetView.getView());
				currentView = targetView;
				changeTitleandBottom(targetView.getId());
				return true;
			}
		}

		return false;
	}
	
	/**
	 * 切换底部导航和标题
	 */
	private void changeTitleandBottom(int id)
	{
		//跟随着中间容器的变换，去切换底部和顶部容器
		//根据中间容器里正在显示的界面
		
		//方案一：以中间容器的简单名称
		
//		if(currentView.getClass().getSimpleName().equals("First or second"))
		switch (id) {
			case ConstantValue.VIEW_FIRST:
				TopManager1.getInstance().showUnLoginTitle();
				BottomManager1.getInstrance().showCommonBottom();
				
				break;
			case ConstantValue.VIEW_SECOND:
				TopManager1.getInstance().showCommonTitle();
				BottomManager1.getInstrance().showGameBottom();
				break;
		}
		
	}
}
