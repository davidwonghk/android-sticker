package com.easy.emotionsticker.fragment;


import android.support.v4.app.Fragment;

import com.easy.emotionsticker.callback.StickerCallback;
import com.easy.emotionsticker.helper.ResourcesRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david.wong on 18/06/2016.
 */
public class StickerFragmentListBuilder {
	private ResourcesRepository resourcesRepository;


	public StickerFragmentListBuilder(ResourcesRepository resourcesRepository) {
		this.resourcesRepository = resourcesRepository;
	}

	public List<Fragment> build(Fragment contentPage, StickerCallback callback) {
		List<Fragment> list = new ArrayList<>();
		list.add(contentPage);

		for (String tab : resourcesRepository.getTabsOrder()) {
			StickerFragment fragment = createStickerFragment(tab, callback);
			list.add(fragment);
		}

		return list;
	}


	StickerFragment createStickerFragment(String tabName, StickerCallback callback) {
		StickerFragment fragment = new StickerFragment();
		fragment.setTabName(tabName);
		fragment.setCallback(callback);
		fragment.setResourcesRepository(resourcesRepository);
		return fragment;
	}
}
