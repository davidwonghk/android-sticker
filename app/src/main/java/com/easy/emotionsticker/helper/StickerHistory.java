package com.easy.emotionsticker.helper;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by david.wong on 20/07/2016.
 */
public class StickerHistory {
	private final static String KEY = "historys";
	private final static String SEP = ";";
	private SharedPreferences settings;
	private Map<String, OnHistoryChangeCallback> callbacks = new HashMap<>();


	public StickerHistory(SharedPreferences settings) {
		this.settings = settings;
	}

	synchronized
	public List<String> get() {
		String historyStr = settings.getString(KEY, null);
		if (historyStr == null) return new ArrayList<>();
		List<String> list = Arrays.asList(historyStr.split(SEP));

		Log.d(KEY, list.toString());
		return list;
	}


	synchronized
	public void add(String resName) {
		Log.d(KEY, "save history: " + resName);
		List<String> historyList = new ArrayList<>();
		historyList.add(resName);

		for (String s : get()) {
			if (false == s.equals(resName)) historyList.add(s);
		}

		String toSave = TextUtils.join(SEP, historyList);
		settings.edit().putString(KEY, toSave).commit();

		Log.d(KEY, "toSave=" + toSave);
		for(OnHistoryChangeCallback cb: callbacks.values()) {
			cb.onHistoryChange(this);
		}
	}

	synchronized
	public void clear() {
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(KEY, null);
		editor.commit();

		for(OnHistoryChangeCallback cb: callbacks.values()) {
			cb.onHistoryChange(this);
		}
	}


	public void addOnHistoryChange(String name, OnHistoryChangeCallback callback) {
		if (callbacks.containsKey(name)) return;
		this.callbacks.put(name, callback);
	}

	public interface OnHistoryChangeCallback {
		void onHistoryChange(StickerHistory history);
	}
}
