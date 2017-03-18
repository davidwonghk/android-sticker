package com.easy.emotionsticker.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.easy.emotionsticker.builder.StickerPageBuilder;
import com.easy.emotionsticker.callback.StickerCallback;
import com.easy.emotionsticker.helper.ResourcesRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david.wong on 18/06/2016.
 */
public class StickerFragmentListBuilder {
	private ResourcesRepository resourcesRepository;


	public StickerFragmentListBuilder(Context context, ResourcesRepository resourcesRepository, StickerCallback callback) {
		this.resourcesRepository = resourcesRepository;
		StickerFragment.setStickerPageBuilder(new StickerPageBuilder(context, resourcesRepository));
		StickerFragment.setCallback(callback);
	}

	public List<Fragment> build(Fragment... fragments) {
		List<Fragment> list = new ArrayList<>();
		for (Fragment f: fragments) {
			list.add(f);
		}

		for (String tab : resourcesRepository.getTabsOrder()) {
			StickerFragment fragment = createStickerFragment(tab);
			list.add(fragment);
		}

		return list;
	}


	StickerFragment createStickerFragment(String tabName) {
		StickerFragment fragment = new StickerFragment();
		Bundle bundle = new Bundle();
		bundle.putString("tabName", tabName);
		fragment.setArguments(bundle);
		return fragment;
	}
}
