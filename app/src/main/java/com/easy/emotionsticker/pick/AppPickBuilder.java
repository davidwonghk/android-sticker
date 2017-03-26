package com.easy.emotionsticker.pick;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;

import com.easy.emotionsticker.R;
import com.easy.emotionsticker.helper.MyAlertDialog;
import com.easy.emotionsticker.helper.ResourcesRepository;

/**
 * Created by david on 31/08/2016.
 */
class AppPickBuilder {
	protected Context context;

	private Context getContext() {
		return this.context;
	}

	public AppPickBuilder(Context context) {
		this.context = context;
	}


	public AppPick build(final String appName, final int iconId, final String packageName) {
		return new AppPick() {
			@Override public int getIcon() { return iconId; }
			@Override public String getAppName() { return appName; }

			@Override
			public void sendToApplication(Uri sticker) throws ActivityNotFoundException {
				sendToIntent(appName, sticker, packageName);
			}

		};
	}

	public AppPick buildSms(final int iconId) {
		return new AppPick() {
			@Override public int getIcon() { return iconId; }
			@Override public String getAppName() { return getContext().getString(R.string.name_sms); }

			@Override
			public void sendToApplication(Uri sticker) throws ActivityNotFoundException {
				try {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
						String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(getContext());
						sendToIntent(getAppName(), sticker, defaultSmsPackageName);
						return;
					}

					Intent smsIntent = createSendIntent(sticker);
					smsIntent.setType("vnd.android-dir/mms-sms");
					getContext().startActivity(smsIntent);
				} catch(Exception e) {
					new MyAlertDialog(getContext(), R.string.alert_title, R.string.alert_title).show();
				}
			}

		};

	}

	public AppPick buildOther(final int iconId) {
		return new AppPick() {
			@Override public int getIcon() { return iconId; }
			@Override public String getAppName() { return getContext().getString(R.string.name_other); }

			@Override
			public void sendToApplication(Uri sticker) throws ActivityNotFoundException {
				Intent sendIntent = createSendIntent(sticker);
				try {
					getContext().startActivity(sendIntent);
				} catch (ActivityNotFoundException e) {
					new MyAlertDialog(getContext(), R.string.alert_title, R.string.alert_title).show();
				}
			}
		};
	}

	private void sendToIntent(String appName, Uri resName, String packageName) {
		Intent sendIntent = createSendIntent(resName);
		sendIntent.setPackage(packageName);
		sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);

		try {
			getContext().startActivity(sendIntent);
		} catch (ActivityNotFoundException e) {
			final String alertMessage = appName + " " + context.getString(R.string.alert_install);
			final String title = context.getString(R.string.alert_title);
			new MyAlertDialog(getContext(), title, alertMessage).show();
		}
	}


	private Intent createSendIntent(Uri uri) {
		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
		sendIntent.setType("image/jpeg");
		return sendIntent;
	}

}
