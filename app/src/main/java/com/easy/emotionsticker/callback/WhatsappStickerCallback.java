package com.easy.emotionsticker.callback;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.easy.emotionsticker.R;
import com.easy.emotionsticker.helper.AdHelper;
import com.easy.emotionsticker.helper.DeviceStatusChecker;
import com.easy.emotionsticker.helper.MyAlertDialog;
import com.easy.emotionsticker.helper.ResourcesRepository;
import com.easy.emotionsticker.helper.StickerHistory;

import java.util.Random;

/**
 * Created by david.wong on 19/06/2016.
 */


public class WhatsappStickerCallback implements StickerCallback {

	private Context context;
	private StickerHistory history;
	private DeviceStatusChecker checker;
	private AdHelper ad;

	public WhatsappStickerCallback(Context context, StickerHistory history, AdHelper ad) {
		this.context = context;
		this.history = history;
		this.checker = new DeviceStatusChecker(context);
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
			Toast.makeText(context, context.getString(R.string.alert_save_history), Toast.LENGTH_SHORT).show();
		}

		safeShowAd(50);

		try {
			sendSticker(resName);
		} catch (Throwable t) {
			// prompt alert dialog
			new MyAlertDialog(context, R.string.alert_title, R.string.alert_internet).show();
		}

		safeShowAd(35);
	}


	private void sendSticker(String resName) {
		Intent sendIntent = new Intent();

		final Uri uri = ResourcesRepository.getDrawableUri(resName);
		//final String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension("jpg");

		sendIntent.setPackage("com.whatsapp");
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
		sendIntent.setType("image/jpeg");
		sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);

		try {
			context.startActivity(sendIntent);
			//context.startActivity(Intent.createChooser(sendIntent, context.getString(R.string.intent_title)));
		} catch (ActivityNotFoundException e) {
			new MyAlertDialog(context, R.string.alert_title, R.string.alert_whatsapp);
		}
	}


}

