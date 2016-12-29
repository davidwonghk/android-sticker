package com.easy.emotionsticker.helper;

import android.net.Uri;

import com.commonsware.cwac.provider.StreamProvider;
import com.easy.emotionsticker.R;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by david on 10/23/15.
 */
public class ResourcesRepository {
    private static final String PACKAGE_NAME = "com.easy.emotionsticker";
	private static final Uri PROVIDER= Uri.parse("content://"+PACKAGE_NAME);

	/*
    static public Uri getDrawableUri(String id) {
        return Uri.parse("android.resource://" + PACKAGE_NAME + "/drawable/" + id);
    }
    */

	static public Uri getDrawableUri(String id) {
		return (PROVIDER
				.buildUpon()
				.appendPath(StreamProvider.getUriPrefix(PACKAGE_NAME))
				.appendPath("assets/"+id+".gif")
				.build());
	}


	private Map<String, StickerRepresent> map = new WeakHashMap<>();

	private class StickerRepresent {
		int merge;
		int tab;
		int size;
	}

	private final static String[] tabs = {
			"face", "finger", "action",
			"moon", "work", "mad", "brown", "bye",
			"cry", "cute", "special", "drunk",
			"cat1", "cat2", "cat3", "dog",
			"oldlady", "milk", "sushi", "wechat",
			"love", "kiss", "hug", "heart", "disheart",
			"red", "purple", "blue", "green", "pink",
			"flat", "threed", "fb",
			"merry", "newyear", "christmas", "halloween",
			"snowman", "food",
			"animal", "tools", "cards", "emoji",
			"ghost", "bat", "owl",
			"arrow", "hand",
			"indian", "indianother",
	};

	public String[] getTabsOrder() {
		return tabs;
	}

	//pre initialization the mapping between tab name and drawable resources
	public ResourcesRepository() {
		add("cute", R.drawable.cute_tab, R.drawable.cute_merge, 23);
		add("drunk", R.drawable.drunk_tab, R.drawable.drunk_merge, 9);
		add("halloween", R.drawable.halloween_tab, R.drawable.halloween_merge, 12);
		add("pink", R.drawable.pink_tab, R.drawable.pink_merge, 11);
		add("threed", R.drawable.threed_tab, R.drawable.threed_merge, 6);
		add("owl", R.drawable.owl_tab, R.drawable.owl_merge, 5);
		add("special", R.drawable.special_tab, R.drawable.special_merge, 6);
		add("emoji", R.drawable.emoji_tab, R.drawable.emoji_merge, 26);
		add("blue", R.drawable.blue_tab, R.drawable.blue_merge, 37);
		add("purple", R.drawable.purple_tab, R.drawable.purple_merge, 22);
		add("tools", R.drawable.tools_tab, R.drawable.tools_merge, 14);
		add("flat", R.drawable.flat_tab, R.drawable.flat_merge, 30);
		add("moon", R.drawable.moon_tab, R.drawable.moon_merge, 26);
		add("finger", R.drawable.finger_tab, R.drawable.finger_merge, 32);
		add("animal", R.drawable.animal_tab, R.drawable.animal_merge, 14);
		add("christmas", R.drawable.christmas_tab, R.drawable.christmas_merge, 36);
		add("merry", R.drawable.merry_tab, R.drawable.merry_merge, 17);
		add("red", R.drawable.red_tab, R.drawable.red_merge, 17);
		add("indian", R.drawable.indian_tab, R.drawable.indian_merge, 23);
		add("brown", R.drawable.brown_tab, R.drawable.brown_merge, 27);
		add("indianother", R.drawable.indianother_tab, R.drawable.indianother_merge, 15);
		add("newyear", R.drawable.newyear_tab, R.drawable.newyear_merge, 15);
		add("hand", R.drawable.hand_tab, R.drawable.hand_merge, 30);
		add("fb", R.drawable.fb_tab, R.drawable.fb_merge, 8);
		add("mad", R.drawable.mad_tab, R.drawable.mad_merge, 10);
		add("ghost", R.drawable.ghost_tab, R.drawable.ghost_merge, 11);
		add("bat", R.drawable.bat_tab, R.drawable.bat_merge, 9);
		add("work", R.drawable.work_tab, R.drawable.work_merge, 9);
		add("face", R.drawable.face_tab, R.drawable.face_merge, 23);
		add("heart", R.drawable.heart_tab, R.drawable.heart_merge, 7);
		add("green", R.drawable.green_tab, R.drawable.green_merge, 28);
		add("arrow", R.drawable.arrow_tab, R.drawable.arrow_merge, 32);
		add("cards", R.drawable.cards_tab, R.drawable.cards_merge, 4);
		add("hug", R.drawable.hug_tab, R.drawable.hug_merge, 11);
		add("oldlady", R.drawable.oldlady_tab, R.drawable.oldlady_merge, 11);
		add("milk", R.drawable.milk_tab, R.drawable.milk_merge, 10);
		add("wechat", R.drawable.wechat_tab, R.drawable.wechat_merge, 23);
		add("sushi", R.drawable.sushi_tab, R.drawable.sushi_merge, 14);
		add("food", R.drawable.food_tab, R.drawable.food_merge, 14);
		add("snowman", R.drawable.snowman_tab, R.drawable.snowman_merge, 15);
		add("action", R.drawable.action_tab, R.drawable.action_merge, 27);
		add("disheart", R.drawable.disheart_tab, R.drawable.disheart_merge, 8);
		add("bye", R.drawable.bye_tab, R.drawable.bye_merge, 15);
		add("kiss", R.drawable.kiss_tab, R.drawable.kiss_merge, 18);
		add("love", R.drawable.love_tab, R.drawable.love_merge, 33);
		add("cat1", R.drawable.cat1_tab, R.drawable.cat1_merge, 25);
		add("cat3", R.drawable.cat3_tab, R.drawable.cat3_merge, 40);
		add("cat2", R.drawable.cat2_tab, R.drawable.cat2_merge, 32);
		add("dog", R.drawable.dog_tab, R.drawable.dog_merge, 44);
		add("cry", R.drawable.cry_tab, R.drawable.cry_merge, 18);


	}

	public int getMergeImageResId(String tab) {
		StickerRepresent rep = map.get(tab);
		if (rep == null) return R.drawable.x;
		return rep.merge;
	}

	public int getIconResId(String tab) {
		StickerRepresent rep = map.get(tab);
		if (rep == null) return R.drawable.x;
		return rep.tab;
	}

	public String getSticker(String tab, int index) {
		try {
			StickerRepresent rep = map.get(tab);
			if (index >= rep.size) return null;
			return tab + '_' + String.valueOf(index);
		}catch (Exception e) {
			return "x";
		}
	}


	private void add(String key, int icon, int merge, int size) {
		StickerRepresent rep = new StickerRepresent();
		rep.tab = icon;
		rep.merge = merge;
		rep.size = size;
		map.put(key, rep);
	}


}
