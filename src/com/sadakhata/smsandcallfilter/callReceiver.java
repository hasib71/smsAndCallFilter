/*
 * Author: Hasib Al Muhaimin.
 * 
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 * 
 */

package com.sadakhata.smsandcallfilter;

import android.content.BroadcastReceiver;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;


import com.android.internal.telephony.*;


public class callReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		try{
		 	String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
		 	
			if(incomingNumber.length() < 5 || isSpamCall(incomingNumber))
		 	{
				try{
					TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
					Class<?> clazz = Class.forName(telephonyManager.getClass().getName());
					Method method = clazz.getDeclaredMethod("getITelephony");
					method.setAccessible(true);
					ITelephony telephonyService = (ITelephony) method.invoke(telephonyManager);
					telephonyService.endCall();
					Toast.makeText(context, "Blocked Call " + incomingNumber, Toast.LENGTH_SHORT).show();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
		 	}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private boolean isSpamCall(String number)
	{
		List<String> spamCaller = Arrays.asList("+8807181");
		
		return spamCaller.indexOf(number) != -1;
	}
}