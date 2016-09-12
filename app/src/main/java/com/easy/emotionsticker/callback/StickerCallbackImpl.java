package com.easy.emotionsticker.callback;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.easy.emotionsticker.AppPickDialog;
import com.easy.emotionsticker.R;
import com.easy.emotionsticker.helper.AdHelper;
import com.easy.emotionsticker.helper.DeviceStatusChecker;
import com.easy.emotionsticker.helper.MyAlertDialog;
import com.easy.emotionsticker.helper.StickerHistory;
import com.easy.emotionsticker.pick.AppRepository;

import java.util.Random;

/**
 * Created by david.wong on 19/06/2016.
 */


public class StickerCallbackImpl implements StickerCallback {

	private Context context;
	private StickerHistory history;
	private DeviceStatusChecker checker;
	private AppRepository appRepository;
	private AdHelper ad;

	public StickerCallbackImpl(Context context, StickerHistory history, AppRepository appRepository, AdHelper ad) {
		this.context = context;
		this.history = history;
		this.checker = new DeviceStatusChecker(context);
		this.appRepository = appRepository;
		this.ad = ad;
	}

	private void safeShowAd(int chance) {
		try {
			if (new Random().nextInt(100) < chance) { ad.show(); }
		} catch(Throwable t) {}
	}

	public void onStickerSelect(String resName) {
		if (checker.preStickerCheck() == false) {
			return;
		}

		try {
			history.add(resName); //save history
		} catch (Throwable t) {
			t.printStackTrace();
			Toast.makeText(context, context.getString(R.string.alert_save_history), Toast.LENGTH_SHORT).show();
		}

		safeShowAd(50);

		try {
			new AppPickDialog(context, appRepository.getActiveApplications(), resName).show();
			//sendSticker(resName);
		} catch (Throwable t) {
			// prompt alert dialog
			t.printStackTrace();
			new MyAlertDialog(context, R.string.alert_title, R.string.alert_internet).show();
		}

		safeShowAd(35);
	}




}

