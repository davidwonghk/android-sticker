package com.easy.emotionsticker.fragment;


import android.support.v4.app.Fragment;

import com.easy.emotionsticker.callback.StickerCallback;
import com.easy.emotionsticker.helper.ResourcesRepository;

/**
 * Created by david.wong on 18/06/2016.
 */
public class BaseFragment extends Fragment {
	protected ResourcesRepository resourcesRepository;
	protected String tabName;
	protected StickerCallback callback;


	public void setResourcesRepository(ResourcesRepository resourcesRepository) {
		this.resourcesRepository = resourcesRepository;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public void setCallback(StickerCallback callback) {
		this.callback = callback;
	}

	@Override
	public String toString() {
		return tabName;
	}
}
