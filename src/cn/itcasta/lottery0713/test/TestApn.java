package cn.itcasta.lottery0713.test;

import cn.itcasta.lottery0713.util.NetUtil;
import android.test.AndroidTestCase;

public class TestApn extends AndroidTestCase {
	public void getCurrentApn()
	{
		NetUtil.setApnParame(getContext());
	}
}
