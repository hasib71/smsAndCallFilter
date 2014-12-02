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

import com.sadakhata.smsandcallfilter.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MessageViewer extends Activity
{
    //private static final String TAG = "MessageViewer";

    public static final int REQUEST_CODE_MUTATED = 0;

    public static final int RESULT_CODE_NOT_MUTATED = 0;
    public static final int RESULT_CODE_MUTATED = 1;

    public static final String MESSAGE_ID_EXTRA = C.PACKAGE_NAME + ".message_id";

    private static final long MESSAGE_ID_INITIAL = -1;
    private long mMessageID = MESSAGE_ID_INITIAL;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.message_viewer);

        Intent intent = getIntent();
        mMessageID = intent.getLongExtra(MESSAGE_ID_EXTRA, MESSAGE_ID_INITIAL);
        if (mMessageID < 0)
            throw new AssertionError();

        // User is viewing this message. Hence, remove the notification.
        Notifier.cancel(this, String.valueOf(mMessageID), Notifier.NEW_MESSAGE);

        Message message = new Settings(this).getMessage(mMessageID);

        TextView addressTextView = (TextView) findViewById(R.id.address);
        TextView receivedAtTextView = (TextView) findViewById(R.id.receivedAt);
        TextView messageTextView = (TextView) findViewById(R.id.message);

        addressTextView.setText(
            getString(R.string.from)
            + ": " + message.address);
        receivedAtTextView.setText(
            getString(R.string.received)
            + ": " + TimeFormatter.f(this, message.receivedAt, TimeFormatter.FULL_FORMAT));
        messageTextView.setText(message.message);

        setTitle(message.address);
    }

    public void onConfirmDelete(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(R.string.delete)
            .setMessage(R.string.messageWillBeDeleted)
            .setPositiveButton(
                R.string.delete,
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        delete();
                       
                        Toast.makeText(getBaseContext(), "Message Deleted Successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
            .setNegativeButton(android.R.string.cancel, null);
        builder.show();
    }
    
    public void onRestore(View v)
    {
    	try{
        	ContentValues values = new ContentValues();
        	Message m = new Settings(this).getMessage(mMessageID);
        	values.put("address", m.address);
        	values.put("body", m.message);
        	values.put("date", m.receivedAt);
        	getContentResolver().insert(Uri.parse("content://sms/inbox"),
					values);
        	delete();
        	
            Toast.makeText(getBaseContext(), this.getText(R.string.successRestoreMessage), Toast.LENGTH_SHORT).show(); //not sure about getBaseContext()
    	
    	}catch(Exception e)
    	{
    		Toast.makeText(getBaseContext(), this.getText(R.string.somethingWentWrong), Toast.LENGTH_LONG).show();
    		e.printStackTrace();
    	}
    }

    private void delete()
    {
        new Settings(this).deleteMessage(mMessageID);

        setResult(RESULT_CODE_MUTATED);
        finish();
    }
}
