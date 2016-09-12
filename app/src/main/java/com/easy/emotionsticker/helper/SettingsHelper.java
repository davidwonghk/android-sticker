package com.easy.emotionsticker.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsHelper {
	private static final String PREFS_NAME = "EmotionStickerPrefs";
	private static final String PICK_PREFS_NAME = "EmotionStickerPickPrefs";


	public static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(PREFS_NAME, 0);
	}

	public static SharedPreferences getPickSharedPreferences(Context context) {
		return context.getSharedPreferences(PICK_PREFS_NAME, 0);
	}
}
