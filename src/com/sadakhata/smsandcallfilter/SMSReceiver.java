/*
 * Author: Hasib Al Muhaimin.
 * 
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 * 
 * ***courtesy to Jelle Geerts***
 * Author: Jelle Geerts

 * Usage of the works is permitted provided that this instrument is
 * retained with the works, so that any entity that uses the works is
 * notified of this instrument.
 *
 * DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
*/

package com.sadakhata.smsandcallfilter;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.sadakhata.spamsmsblocker.*;

public class SMSReceiver extends BroadcastReceiver
{
    private static final String TAG = "SMSReceiver";
    
    private SKSpamBlocker skspamblocker = new SKSpamBlocker();
    
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle extras = intent.getExtras();
        if (extras == null)
            return;

        // NOTE:
        // One can receive multiple 'SmsMessage' objects, but they should have
        // the same originating address. In other words, it should be safe to
        // rely on the originating address of the first 'SmsMessage' to detect
        // whether this SMS should be filtered.
        Object[] pdus = (Object[]) extras.get("pdus");
        if (pdus.length == 0)
            return;

        SmsMessage first_message = SmsMessage.createFromPdu((byte[]) pdus[0]);
        String address = first_message.getDisplayOriginatingAddress();
        StringBuilder stringBuilder = new StringBuilder().append(first_message.getMessageBody());
        for (int i = 1; i < pdus.length; i++)
        {
            SmsMessage message = SmsMessage.createFromPdu((byte[]) pdus[i]);
            stringBuilder.append(message.getMessageBody());
        }
        String fullMessageBody = stringBuilder.toString();
        
        this.skspamblocker.init(context.getAssets());
        
        
        if (shouldBlockMessage(context, address, fullMessageBody) || this.skspamblocker.isSpam(address, fullMessageBody))
        {
            Settings settings = new Settings(context);
            settings.saveMessage(address, first_message.getTimestampMillis(), fullMessageBody);
            abortBroadcast();
        }
    }

    private boolean shouldBlockMessage(Context context, String address, String fullMessageBody)
    {
        Settings settings = new Settings(context);
        List<Filter> filters = settings.findFiltersForAddress(address);
        for (Filter filter : filters)
        {
            if (address.equals(filter.address)
                || filter.address.equals(Settings.ANY_ADDRESS))
            {
                if (!contentFiltersMatch(filter.contentFilters, fullMessageBody))
                {
                    // There may be more filters for the same originating
                    // address. We have to check them all before knowing
                    // whether we should block the message.
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    private boolean contentFiltersMatch(List<String> contentFilters, String message)
    {
        for (String contentFilter : contentFilters)
        {
            if (message.toLowerCase().indexOf(contentFilter.toLowerCase()) < 0)
            {
                Log.d(TAG, "Content filter [" + contentFilter + "] not found, skipping it.");
                return false;
            }
        }
        return true;
    }
}
