package com.easy.emotionsticker.helper;

import android.net.Uri;

import com.easy.emotionsticker.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by david on 10/23/15.
 */
public class ResourcesRepository {

	private Map<String, Integer> map = new HashMap<>();
	private List<String> list = new ArrayList<>();

	public List<String> getTabsOrder() {
		return list;
	}

	//pre initialization the mapping between tab name and drawable resources
	public ResourcesRepository() {
		initStickers();
	}

	public int getTabSize(String tab) {
		return map.get(tab);
	}

	public Uri getTabIcon(String tab) {
		return BitmapHelper.getDrawableUri("tab/" + tab + "_tab");
	}

	public Uri getSticker(String id) {
		String s[] = id.split("_");
		if (s.length < 2) return Uri.EMPTY;

		return BitmapHelper.getDrawableUri(id);
	}

	public Uri getHistoryTabIcon() {
		return Uri.parse("drawable://" + String.valueOf(R.drawable.history));
	}

	public Uri getCategoryTabIcon() {
		return Uri.parse("drawable://" + String.valueOf(R.drawable.category));
	}


	public String getStickerId(String tabName, int selected) {
		return "sticker/" + tabName + "_" + String.valueOf(selected);
	}


	private void initStickers() {
		add("face", 23);
		add("finger", 32);
		add("action", 27);
		add("moon", 26);
		add("work", 9);
		add("mad", 10);
		add("brown", 27);
		add("bye", 15);
		add("cry", 18);
		add("cute", 23);
		add("special", 6);
		add("drunk", 9);
		add("cat1", 25);
		add("cat2", 32);
		add("cat3", 40);
		add("dog", 44);
		add("rabbit1", 28);
		add("rabbit2", 40);
		add("rabbit4", 30);
		add("bunny", 16);
		add("oldlady", 11);
		add("milk", 10);
		add("sushi", 14);
		add("wechat", 23);
		add("love", 33);
		add("kiss", 18);
		add("hug", 11);
		add("heart", 7);
		add("disheart", 8);
		add("hungry", 8);
		add("red", 17);
		add("purple", 22);
		add("blue", 37);
		add("green", 28);
		add("pink", 11);
		add("flat", 30);
		add("threed", 6);
		add("fb", 8);
		add("merry", 17);
		add("newyear", 15);
		add("christmas", 36);
		add("halloween", 12);
		add("snowman", 15);
		add("food", 14);
		add("animal", 14);
		add("tools", 14);
		add("cards", 4);
		add("emoji", 26);
		add("ghost", 11);
		add("bat", 9);
		add("owl", 5);
		add("arrow", 32);
		add("hand", 30);
		add("indian", 23);
		add("indianother", 15);
	}


	private void add(String key, int size) {
		map.put(key, size);
		list.add(key);
	}


}
