package com.easy.emotionsticker.helper;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.easy.emotionsticker.R;

/**
 * Created by david.wong on 22/06/2016.
 */
public class DeviceStatusChecker {

	private final static String tag = "DeviceStatusChecker";
	private Context context;

	public DeviceStatusChecker(Context context) {
		this.context = context;
	}

	public boolean preStickerCheck() {
		if (false == netCheckin()) {
			new MyAlertDialog(context, R.string.alert_title, R.string.alert_internet).show();
			return false;
		}

		if (false == isAppInstalled("com.whatsapp")) {
			new MyAlertDialog(context, R.string.alert_title, R.string.alert_whatsapp).show();
			return false;
		}

		return true;
	}

	public boolean netCheckin() {
		try {
			ConnectivityManager nInfo = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE);
			nInfo.getActiveNetworkInfo().isConnectedOrConnecting();
			Log.d(tag, "Net avail:" + nInfo.getActiveNetworkInfo().isConnectedOrConnecting());

			ConnectivityManager cm = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isConnectedOrConnecting()) {
				Log.d(tag, "Network available:true");
				return true;
			} else {
				Log.d(tag, "Network available:false");
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}


	public boolean isAppInstalled(String packageName) {
		try {
			context.getPackageManager().getApplicationInfo(packageName, 0);
			return true;
		}
		catch (PackageManager.NameNotFoundException e) {
			return false;
		}
	}

}
