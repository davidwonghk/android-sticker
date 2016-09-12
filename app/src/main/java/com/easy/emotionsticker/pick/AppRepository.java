package com.easy.emotionsticker.pick;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.SharedPreferences;

import com.easy.emotionsticker.R;
import com.easy.emotionsticker.helper.SettingsHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 31/08/2016.
 */
public class AppRepository {
	private Context context;
	private List<AppPick> applications;
	private AppPickBuilder builder;

	public AppRepository(Context context) {
		this.context = context;
		this.applications = new ArrayList<>();
		this.builder = new AppPickBuilder(context);

		setup();
	}



	public List<AppPick> getActiveApplications() {
		List<AppPick> activeApplications = new ArrayList<>();

		for(AppPick a : applications) {
			SharedPreferences sp = SettingsHelper.getPickSharedPreferences(context);
			boolean active = sp.getBoolean(a.getAppName(), false);
			if (active) {
				activeApplications.add(a);
			}
		}
		return activeApplications;
	}

	public void setActive(String appName, boolean value) {
		SharedPreferences sp = SettingsHelper.getPickSharedPreferences(context);
		sp.edit().putBoolean(appName, value).commit();
	}

	private void setup() {
		add("WhatsApp Messenger", R.drawable.btn_whatsapp, "com.whatsapp");
		add("WeChat", R.drawable.btn_wechat, "com.tencent.mm");
		add("Facebook Messenger", R.drawable.btn_fb, "com.facebook.orca");
		add("Google Hangout", R.drawable.btn_hangout, "com.google.android.talk");
		applications.add(builder.buildSms(R.drawable.btn_sms));
		applications.add(builder.buildOther(R.drawable.btn_other));
	}

	private void add(String appName, int iconId, String packageName) {
		applications.add(
			builder.build(appName, iconId, packageName)
		);
	}

	//--------------------------------------------------

	public String[] toStringArray() {
		String[] ary = new String[applications.size()];

		for(int i=0; i<applications.size(); ++i) {
			final AppPick a = applications.get(i);
			StringBuilder sb = new StringBuilder();
			sb.append(a.getAppName());
			sb.append(":");
			sb.append(a.getIcon());
			ary[i] = sb.toString();
		}

		return ary;
	}

	public static List<AppPick> fromStringArray(String[] input) {
		List<AppPick> list = new ArrayList<>();
		for (String s: input) {
			final String c[] = s.split(":");
			list.add(new AppPick() {
				@Override public int getIcon() { return Integer.parseInt(c[1]); }
				@Override public String getAppName() { return c[0]; }
				@Override public void sendToApplication(String sticker) throws ActivityNotFoundException { }
			});
		}
		return list;
	}


}
