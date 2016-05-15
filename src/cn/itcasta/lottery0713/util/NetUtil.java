package cn.itcasta.lottery0713.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import cn.itcasta.lottery0713.GloableParams;

/**
 * 判断当前手机联网的渠道
 * @author Administrator
 *
 */
public class NetUtil {
	/**
	 * 检查当前手机网络
	 * @param context
	 * @return
	 */
	public static boolean checkNet(Context context)
	{
		//判断连接方式
		boolean wifiConnected = isWIFIConnected(context);
		boolean mobileConnected = isMOBILEConnected(context);
		if(wifiConnected==false&&mobileConnected==false){
		//如果都没有连接返回false，提示用户当前没有网络
			return false;
		}
		if(mobileConnected==true)
		{
			//判断到当前是mobile连接，设置apn
			setApnParame(context);
		}
		return true;
	}
	
	
	//判断手机使用是wifi还是mobile
	/**
	 * 判断手机是否采用wifi连接
	 */
	public static boolean isWIFIConnected(Context context)
	{
		//Context.CONNECTIVITY_SERVICE). 

		ConnectivityManager manager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(networkInfo!=null&&networkInfo.isConnected())
		{
			return true;
		}
		return false;
	}
	
	public static boolean isMOBILEConnected(Context context)
	{
		ConnectivityManager manager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if(networkInfo!=null&&networkInfo.isConnected())
		{
			return true;
		}
		return false;
	}
	/**
	 * 设置当前apn信息，保存到应用的全局的变量里面
	 * @param context
	 */
	public static void setApnParame(Context context)
	{
		//Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");

		Uri uri=Uri.parse("content://telephony/carriers/preferapn"); 
		
		ContentResolver contentResolver = context.getContentResolver();
		Cursor query = contentResolver.query(uri, null, null, null, null);
		if(query!=null&&query.getCount()==1)
		{
			if(query.moveToFirst())
			{
				GloableParams.PROXY_IP=query.getString(query.getColumnIndex("proxy"));
				GloableParams.PROXY_PORT=query.getInt(query.getColumnIndex("port"));
			}
		}
		query.close();
	}
}
