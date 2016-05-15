package cn.itcasta.lottery0713.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import cn.itcasta.lottery0713.ConstantValue;
import cn.itcasta.lottery0713.R;
import cn.itcasta.lottery0713.bean.ShoppingCart;
import cn.itcasta.lottery0713.bean.Ticket;
import cn.itcasta.lottery0713.net.protocol.Message;
import cn.itcasta.lottery0713.net.protocol.Oelement;
import cn.itcasta.lottery0713.net.protocol.element.CurrentIssueElement;
import cn.itcasta.lottery0713.service.CommonService;
import cn.itcasta.lottery0713.util.BeanFactory;
import cn.itcasta.lottery0713.view.adapter.PollAdapter;
import cn.itcasta.lottery0713.view.manager.BottomManager;
import cn.itcasta.lottery0713.view.manager.PlayGame;
import cn.itcasta.lottery0713.view.manager.PromptManager;
import cn.itcasta.lottery0713.view.manager.TopManager;
import cn.itcasta.lottery0713.view.manager.UiManager;
import cn.itcasta.lottery0713.view.sensor.ShakeSensor;

public class PlaySSQ extends BaseView implements PlayGame {

	private Button randomRed;// 机选红球
	private GridView redNumberContainer;// 红球的选号容器
	private Button randomBlue;// 机选蓝球
	private GridView blueNumberContainer;// 蓝球的选号容器

	private PollAdapter redAdapter;
	private PollAdapter blueAdapter;

	private List<Integer> redNum;// 用来存储已经选好了的红球
	private List<Integer> blueNum;// 用来存储已经选好了的红球

	private SensorManager manager;// 加速度传感器
	private ShakeSensor sensor;

	public PlaySSQ(Context context, Bundle bundle) {
		super(context, bundle);
	}

	@Override
	public int getId() {
		return ConstantValue.VIEW_SSQ;
	}

	@Override
	protected void init() {
		container = (ViewGroup) inflater.inflate(R.layout.il_play_ssq, null);
		randomBlue = (Button) findViewById(R.id.ii_ssq_random_blue);
		randomRed = (Button) findViewById(R.id.ii_ssq_random_red);

		redNumberContainer = (GridView) findViewById(R.id.ii_ssq_red_number_container);
		blueNumberContainer = (GridView) findViewById(R.id.ii_ssq_blue_number_container);

		redNum = new ArrayList<Integer>();
		blueNum = new ArrayList<Integer>();

		redAdapter = new PollAdapter(context, 33, redNum, R.drawable.id_redball);
		redNumberContainer.setAdapter(redAdapter);

		blueAdapter = new PollAdapter(context, 16, blueNum, R.drawable.id_blueball);
		blueNumberContainer.setAdapter(blueAdapter);

		manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
	}

	@Override
	protected void setListener() {
		randomBlue.setOnClickListener(this);
		randomRed.setOnClickListener(this);

		redNumberContainer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 每点击一次：拿到当前的用户点击的号码
				// 判断一下，当前的这个存储红球的已选号码的容器里面是否含有当前用户点击，
				// 如果当前容器里含有用户点击的这个号码，改变他的背景图片为默认
				// 如果容器没有，晃一晃

				int num = (Integer) redAdapter.getItem(position);
				if (redNum.contains(num)) {
					// 如果当前容器里含有用户点击的这个号码，改变他的背景图片为默认
					view.setBackgroundResource(R.drawable.id_defalut_ball);

					redNum.remove(num);

				} else {
					redNum.add(num);
					view.setBackgroundResource(R.drawable.id_redball);
					// 如果容器没有，晃一晃
					view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.ia_ball_shake));
				}

				changeNotice();

			}
		});
		blueNumberContainer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 每点击一次：拿到当前的用户点击的号码
				// 判断一下，当前的这个存储红球的已选号码的容器里面是否含有当前用户点击，
				// 如果当前容器里含有用户点击的这个号码，改变他的背景图片为默认
				// 如果容器没有，晃一晃

				int num = (Integer) blueAdapter.getItem(position);
				if (blueNum.contains(num)) {
					// 如果当前容器里含有用户点击的这个号码，改变他的背景图片为默认
					view.setBackgroundResource(R.drawable.id_defalut_ball);
					// 移除这个已经选择号码
					blueNum.remove(num);
				} else {
					blueNum.add(num);
					view.setBackgroundResource(R.drawable.id_blueball);
					// 如果容器没有，晃一晃
					view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.ia_ball_shake));
				}
				changeNotice();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ii_ssq_random_blue:
				// 机选蓝球
				getRandomNum(0, 1);
				break;
			case R.id.ii_ssq_random_red:
				// 机选红球
				// 生成6个红球（随机）
				// 生成（1~33）6个红球，放置一个容器里面，把容器对象传递redAdapter，依据容器里面的号码改变相应球的背景图片
				getRandomNum(6, 0);

				break;

			default:
				break;
		}
		super.onClick(v);
	}

	private void getRandomNum(int red, int blue) {
		// 区分红球还是蓝球
		// 红球数量 蓝球的数量
		// 6 0 随机生成红球
		// 0 1 随机生成蓝球
		// 6 1 随机生成一注------手机摇啊摇

		if (red > 0) {
			redNum.clear();
			// 生成（1~33）6个红球，放置一个容器里面，把容器对象传递redAdapter，依据容器里面的号码改变相应球的背景图片
			Random random = new Random();
			while (redNum.size() < 6) {
				int num = random.nextInt(33) + 1;
				if (redNum.contains(num)) {
					continue;
				}
				redNum.add(num);
			}
			redAdapter.notifyDataSetChanged();

		}
		if (blue > 0) {
			blueNum.clear();
			// 生成（1~16）1个蓝球，放置一个容器里面，把容器对象传递blueAdapter，依据容器里面的号码改变相应球的背景图片
			Random random = new Random();
			int num = random.nextInt(16) + 1;
			blueNum.add(num);
			blueAdapter.notifyDataSetChanged();
		}

		changeNotice();

	}

	@Override
	public void onResume() {
		setTitle();
		register();// 传感器的注册
		clear();
		super.onResume();
	}

	private void register() {
		sensor = new ShakeSensor(context) {

			@Override
			public void createPour() {
				getRandomNum(6, 1);
			}
		};
		manager.registerListener(sensor, manager.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);

	}

	private void unRegister() {
		manager.unregisterListener(sensor);
	}

	@Override
	public void onPause() {
		unRegister();
		super.onPause();
	}

	// 设置标题
	private void setTitle() {
		if (bundle != null) {
			String issue = bundle.getString("lotteryIssue");
			TopManager.getInstance().setTopTitle("双色球第" + issue + "期");
		} else {
			TopManager.getInstance().setTopTitle(R.string.is_ssq_title_default);
		}

	}

	/**
	 * 底部导航的提示
	 */
	private void changeNotice() {
		String notice = "";
		// 判断当前红球的容器当中有多个红球
		// 判断当前蓝球的容器当中有多个蓝球

		if (redNum.size() < 6) {
			notice = "您还需要选择" + (6 - redNum.size()) + "个红球";
		} else {
			// 红球的数量大于等于6
			if (blueNum.size() < 1) {
				notice = "您还需要选择1个蓝球";
			} else {
				// 红球大于等于6 蓝球大于等于1
				// 提示用户当前选择的注数
				//
				int n = redNum.size();
				int r = 6;
				int red = factorial(n) / (factorial(r) * factorial(n - r));

				int blue = blueNum.size();

				int pour = red * blue;// 当前的注数

				notice = "共" + pour + "注" + (pour * 2) + "元";
			}
		}

		BottomManager.getInstrance().changeGameBottomNotice(notice);
	}
	/**
	 * 计算n阶乘
	 * @param n
	 * @return
	 */
	private int factorial(int n) {
		// // n=7 7*6*5*...*1
		// if (n == 0) {
		// return 1;
		// }
		// if (n == 1) {
		// return 1;
		// }
		// if(n>1)
		// {
		//			
		// }
		//		
		//
		// return n * factorial(n - 1);
		if (n > 1) {
			return n * factorial(n - 1);
		} else if (n == 0 || n == 1) {
			return 1;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 清空两个选号容器
	 */
	public void clear() {
		redNum.clear();
		blueNum.clear();
		redAdapter.notifyDataSetChanged();
		blueAdapter.notifyDataSetChanged();
		changeNotice();
	}

	/**
	 * 选好了
	 */
	public void addTicket2ShoppingList() {
		// 判断当前用户是否选择了投注
		if (redNum.size() >= 6 && blueNum.size() >= 1) {
			// 判断是否获取当前的销售期（期次）的信息
			if (bundle == null) {
				// 到服务器获取当前销售期信息
				new HttpTask<Integer, Message>() {

					@Override
					protected void onPostExecute(Message result) {
						handler.sendEmptyMessage(ConstantValue.PROMPT_PROGRESS_CLOSE);

						if (result != null) {
							Oelement oelement = result.getBody().getOelement();
							if ("0".equals(oelement.getErrorcode())) {
								CurrentIssueElement element = (CurrentIssueElement) result.getBody().getElements().get(0);
								bundle = new Bundle();
								bundle.putString("lotteryIssue", element.getIssue());
							}
						}

						super.onPostExecute(result);
					}

					@Override
					protected void onPreExecute() {
						// 在进行耗时工作之间显示滚动条（）
						handler.sendEmptyMessage(ConstantValue.PROMPT_PROGRESS_SHOW);
						super.onPreExecute();
					}

					@Override
					protected Message doInBackground(Integer... params) {
						// 在界面，调用服务层处理获取当前销售期信息的方法
						CommonService service = BeanFactory.getImpl(CommonService.class);
						Message result = service.getCurrentIssueInfo(params[0]);
						return result;
					}
				}.executeProxy(ConstantValue.SSQ);
			} else {
				// 将当前用户的投注封装成一个Ticket对象
				Ticket ticket = new Ticket();
				DecimalFormat format = new DecimalFormat("00");
				StringBuilder builder = new StringBuilder();
				for (Integer red : redNum) {
					builder.append(" ").append(format.format(red));
				}
				ticket.setRed(builder.toString().substring(1));//设置红球

				builder = new StringBuilder();
				for (Integer blue : blueNum) {
					builder.append(" ").append(format.format(blue));
				}

				ticket.setBlue(builder.toString().substring(1));//设置蓝球
				
				
				int n = redNum.size();
				int r = 6;
				int red = factorial(n) / (factorial(r) * factorial(n - r));

				int blue = blueNum.size();

				int pour = red * blue;// 当前的注数
				
				ticket.setNum(pour);//设置注数
				
				

				// 生成一个购物车，里面放置的是Ticket列表，期次关联
				
				ShoppingCart.getInstance().getTickets().add(ticket);
				ShoppingCart.getInstance().setLotteryid(ConstantValue.SSQ);
				ShoppingCart.getInstance().setIssue(bundle.getString("lotteryIssue"));
				
				// 跳转到购物车展示的界面
				UiManager.getInstance().changeView(ShoppingList.class, bundle);
			}

		} else {
			PromptManager.showToast(context, "您需要选择一注投注");
		}

	}

}
