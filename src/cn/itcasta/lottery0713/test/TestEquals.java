package cn.itcasta.lottery0713.test;

import android.test.AndroidTestCase;

public class TestEquals extends AndroidTestCase{
	public void testEquals()
	{
		Integer num1=new Integer(1);
		Integer num2=new Integer(1);
		
		System.out.println(num2==num1);
		System.out.println(num2.equals(num1));
		
	}
}
