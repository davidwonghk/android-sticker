package com.easy.emotionsticker.fragment;


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

	public List<StickerFragment> build(StickerCallback callback) {
		List<StickerFragment> list = new ArrayList<>();

		for (String tab : resourcesRepository.getTabsOrder()) {
			StickerFragment fragment = new StickerFragment();
			fragment.setTabName(tab);
			fragment.setCallback(callback);
			fragment.setResourcesRepository(resourcesRepository);
			list.add(fragment);
		}

		return list;
	}
}
