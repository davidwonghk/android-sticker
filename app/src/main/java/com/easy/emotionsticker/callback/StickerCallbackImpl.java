package com.easy.emotionsticker.callback;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.easy.emotionsticker.AppPickDialog;
import com.easy.emotionsticker.CentralManager;
import com.easy.emotionsticker.R;
import com.easy.emotionsticker.helper.DeviceStatusChecker;
import com.easy.emotionsticker.helper.MyAlertDialog;

/**
 * Created by david.wong on 19/06/2016.
 */


public class StickerCallbackImpl implements StickerCallback {

	private DeviceStatusChecker checker;
	private CentralManager manager;

	public StickerCallbackImpl(CentralManager manager) {
		this.manager = manager;
		this.checker = new DeviceStatusChecker(manager.getContext());
	}


	public void onStickerSelect(String resName) {
		if (checker.preStickerCheck() == false) {
			return;
		}

		final Context context = manager.getContext();

		try {
			manager.getHistory().add(resName); //save history
		} catch (Throwable t) {
			t.printStackTrace();
			Toast.makeText(context, context.getString(R.string.alert_save_history), Toast.LENGTH_SHORT).show();
		}

		//safeShowAd(50);

		try {
			Uri uri = manager.getResourcesRepository().getSticker(resName);
			new AppPickDialog(context, manager.getAppRepository(), uri).show();
			//sendSticker(resName);
		} catch (Throwable t) {
			// prompt alert dialog
			t.printStackTrace();
			new MyAlertDialog(context, R.string.alert_title, R.string.alert_internet).show();
		}

		//safeShowAd(100);
	}




}

