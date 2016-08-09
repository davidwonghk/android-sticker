package com.easy.emotionsticker.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.easy.emotionsticker.R;

/**
 * Created by david.wong on 18/06/2016.
 */
public class MyAlertDialog {
	private AlertDialog dialog;

	public MyAlertDialog(Context context, String title, String message) {

		this.dialog = new AlertDialog.Builder(context)
				.setTitle(title)
				.setMessage(message)
				.setPositiveButton(R.string.OK, dummyOnClickListener)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.create();
	}

	public MyAlertDialog(Context context, int titleId, int messageId) {
		this(context, context.getString(titleId), context.getString(messageId));
	}

	public void show() {
		this.dialog.show();
	}


	private DialogInterface.OnClickListener dummyOnClickListener = new DialogInterface.OnClickListener(){
		public void onClick(DialogInterface dialog,int which){
			// continue with delete
		}
	};

}
