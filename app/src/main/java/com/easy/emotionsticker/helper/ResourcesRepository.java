package com.easy.emotionsticker.helper;

import android.content.Context;
import android.net.Uri;

import com.easy.emotionsticker.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourcesRepository {

	private Map<String, List<String>> map = new HashMap<>();

	public List<String> getTabsOrder() {
		return new ArrayList(map.keySet());
	}

	//pre initialization the mapping between tab name and drawable resources
	public ResourcesRepository(Context context) {
		initStickers(context);
	}

	public int getTabSize(String tab) {
		return map.get(tab).size();
	}

	public Uri getSticker(String id) {
		try {
			String s[] = id.split("_");
			final String tag = s[0];
			final int index = Integer.parseInt(s[1]);

			String path = map.get(tag).get(index);
			return BitmapHelper.getDrawableUri("sticker/" + tag + "/" + path);
		} catch (Exception e) {
			e.printStackTrace();
			return Uri.EMPTY;
		}
	}

	public Uri getHistoryTabIcon() {
		return Uri.parse("drawable://" + String.valueOf(R.drawable.history));
	}

	public Uri getCategoryTabIcon() {
		return Uri.parse("drawable://" + String.valueOf(R.drawable.category));
	}

	public Uri getTabIcon(String tab) {
		return getSticker(tab+"_0");
	}

	public String getStickerId(String tabName, int selected) {
		return tabName + "_" + String.valueOf(selected);
	}


	private void initStickers(Context context) {
		final String STICKER_PATH = "res/sticker";
		try {
			String[] categories = context.getAssets().list(STICKER_PATH);
			for(String c : categories) {
				String[] stickers = context.getAssets().list(STICKER_PATH + "/" + c);
				map.put(c, Arrays.asList(stickers));
			}

		}catch(IOException e) {
			e.printStackTrace();
		}

	}




}

