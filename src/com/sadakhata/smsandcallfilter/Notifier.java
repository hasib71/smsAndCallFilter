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

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

public class Notifier
{
    public static final int NEW_MESSAGE = 1;

    public static Notification build(int icon, CharSequence tickerText)
    {
        Notification notification = new Notification(icon, tickerText, System.currentTimeMillis());
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        return notification;
    }

    public static void notify(Context context, int id, Notification notification)
    {
        getManager(context).notify(id, notification);
    }

    public static void notify(Context context, String tag, int id, Notification notification)
    {
        getManager(context).notify(tag, id, notification);
    }

    public static void cancel(Context context, int id)
    {
        getManager(context).cancel(id);
    }

    public static void cancel(Context context, String tag, int id)
    {
        getManager(context).cancel(tag, id);
    }

    private static NotificationManager getManager(Context context)
    {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
