package com.easy.emotionsticker.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsHelper {
	private static final String PREFS_NAME = "EmotionStickerPrefs";


	public static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(PREFS_NAME, 0);
	}
}
