package com.easy.emotionsticker;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.view.View;
import android.widget.ImageView;

import com.easy.emotionsticker.helper.MyAlertDialog;
import com.easy.emotionsticker.helper.ResourcesRepository;

/**
 * Created by david.wong on 13/08/2016.
 */
public class AppPickDialog extends Dialog {


	public AppPickDialog(Context context, final String resName) {
		super(context);
		super.setContentView(R.layout.pick_dialog);
		super.setTitle(R.string.app_pick_title);



		setupButton(R.id.btn_whatsapp, new View.OnClickListener() {
			@Override public void onClick(View v) {
				sendToAppcliation(resName, "com.whatsapp", R.string.alert_whatsapp);
			}
		});

		setupButton(R.id.btn_wechat, new View.OnClickListener() {
			@Override public void onClick(View v) {
				sendToAppcliation(resName, "com.tencent.mm", R.string.alert_wechat);
			}
		});


		setupButton(R.id.btn_sms, new View.OnClickListener() {
			@Override public void onClick(View v) { sendSms(resName); }
		});

		/*
		setupButton(R.id.btn_line, new View.OnClickListener(){
			@Override public void onClick(View v) {
				sendToAppcliation(resName, "line.me", R.string.alert_line);
			}
		});
		*/

		setupButton(R.id.btn_fb, new View.OnClickListener() {
			@Override public void onClick(View v) {
				sendToAppcliation(resName, "com.facebook.orca", R.string.alert_facebook);
			}
		});

		setupButton(R.id.btn_hangout, new View.OnClickListener(){
			@Override public void onClick(View v) {
				sendToAppcliation(resName, "com.google.android.talk", R.string.alert_hangout);
			}
		});

		setupButton(R.id.btn_other, new View.OnClickListener() {
			@Override public void onClick(View v) { sendOther(resName); }
		});

	}


	private void setupButton(int resId, View.OnClickListener listener) {
		ImageView btn = (ImageView) findViewById(resId);
		btn.setOnClickListener(listener);
	}



	private void sendSms(String resName) {
		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(getContext());
				sendToAppcliation(resName, defaultSmsPackageName, R.string.alert_title);
				return;
			}

			Intent smsIntent = createSendIntent(resName);
			smsIntent.setType("vnd.android-dir/mms-sms");
			getContext().startActivity(smsIntent);
		} catch(Exception e) {
			new MyAlertDialog(getContext(), R.string.alert_title, R.string.alert_title).show();
		}

	}

	private void sendOther(String resName) {
		Intent sendIntent = createSendIntent(resName);
		try {
			getContext().startActivity(sendIntent);
		} catch (ActivityNotFoundException e) {
			new MyAlertDialog(getContext(), R.string.alert_title, R.string.alert_title).show();
		}
	}


	private void sendToAppcliation(String resName, String packageName, int alertMsgId) {
		Intent sendIntent = createSendIntent(resName);
		sendIntent.setPackage(packageName);
		sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);

		try {
			getContext().startActivity(sendIntent);
		} catch (ActivityNotFoundException e) {
			new MyAlertDialog(getContext(), R.string.alert_title, alertMsgId).show();
		}
	}


	private static Intent createSendIntent(String resName) {
		final Uri uri = ResourcesRepository.getDrawableUri(resName);
		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
		sendIntent.setType("image/jpeg");
		return sendIntent;
	}
}

