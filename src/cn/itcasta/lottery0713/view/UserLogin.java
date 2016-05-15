package cn.itcasta.lottery0713.view;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import cn.itcasta.lottery0713.ConstantValue;
import cn.itcasta.lottery0713.GloableParams;
import cn.itcasta.lottery0713.R;
import cn.itcasta.lottery0713.bean.User;
import cn.itcasta.lottery0713.net.protocol.Element;
import cn.itcasta.lottery0713.net.protocol.Message;
import cn.itcasta.lottery0713.net.protocol.Oelement;
import cn.itcasta.lottery0713.net.protocol.element.BalanceElement;
import cn.itcasta.lottery0713.service.UserService;
import cn.itcasta.lottery0713.util.BeanFactory;
import cn.itcasta.lottery0713.view.manager.PromptManager;
import cn.itcasta.lottery0713.view.manager.UiManager;

/**
 * 用户登陆的界面
 * 
 * @author Administrator
 * 
 */
public class UserLogin extends BaseView {

	private EditText username;// 用户名
	private EditText password;// 密码

	private ImageView clearUsername;// 清空用户名输入
	private CheckBox autoLogin;// 自动登陆
	private Button login;// 登陆

	public UserLogin(Context context, Bundle bundle) {
		super(context, bundle);
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return ConstantValue.VIEW_USER_LOGIN;
	}

	@Override
	protected void init() {
		container = (ViewGroup) inflater.inflate(R.layout.il_user_login, null);
		username = (EditText) findViewById(R.id.ii_user_login_username);
		password = (EditText) findViewById(R.id.ii_user_login_password);

		clearUsername = (ImageView) findViewById(R.id.clear);
		autoLogin = (CheckBox) findViewById(R.id.ii_user_login_auto_login);
		login = (Button) findViewById(R.id.ii_user_login);
	}

	@Override
	protected void setListener() {
		clearUsername.setOnClickListener(this);
		autoLogin.setOnClickListener(this);
		login.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.clear:
				break;
			case R.id.ii_user_login:
				// 获取到用输入信息
				String name = username.getText().toString();
				String pwd = password.getText().toString();

				// 信息的校验
				boolean isPast = checkUserInput(name, pwd);
				if(isPast)
				{
					// new HttpTask
					
					User user=new User();
					user.setUsername(name);
					user.setPassword(pwd);
					
					new HttpTask<User, Message>() {
						
						

						@Override
						protected void onPostExecute(Message result) {
							handler.sendEmptyMessage(ConstantValue.PROMPT_PROGRESS_CLOSE);
							if(result!=null)
							{
								UiManager.getInstance().changeCache();
							}else
							{
								PromptManager.showToast(context, "登陆失败");
							}
							
							
							super.onPostExecute(result);
						}

						@Override
						protected void onPreExecute() {
							handler.sendEmptyMessage(ConstantValue.PROMPT_PROGRESS_SHOW);
							super.onPreExecute();
						}

						@Override
						protected Message doInBackground(User... params) {
							User user=params[0];
							
							UserService service=BeanFactory.getImpl(UserService.class);
							//登陆的服务器返回的信息
							Message result = service.login(user);
							
							//判断是否登陆成功
							if(result!=null)
							{
								Oelement oelement=result.getBody().getOelement();
								if("0".equals(oelement.getErrorcode()))
								{
									//登陆成功：获取用户的余额信息
									GloableParams.isLogin=true;//标定用户登陆成功
									GloableParams.username=user.getUsername();//用户名保存
									
									Message balanceResult=service.balance(user);
									
									if(balanceResult!=null)
									{
										oelement=balanceResult.getBody().getOelement();
										if("0".equals(oelement.getErrorcode()))
										{
											BalanceElement element = (BalanceElement) balanceResult.getBody().getElements().get(0);
											GloableParams.money=Float.parseFloat(element.getInvestvalues());
											return balanceResult;
										}
									}
								}
							}
							
							return null;
						}
					}.executeProxy(user);
					
				}else
				{
					//提示用户：输入的信息有误
				}


				break;
		}
	}

	private boolean checkUserInput(String name, String pwd) {
		if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(pwd)) {
			if (name.length() == 11 && pwd.length() >= 8) {
				return true;
			}
		}
		return false;
	}

}
