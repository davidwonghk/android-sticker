package com.easy.emotionsticker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.astuetz.PagerSlidingTabStrip;
import com.easy.emotionsticker.fragment.ContentPageFragment;
import com.easy.emotionsticker.fragment.StickerFragment;
import com.easy.emotionsticker.helper.ResourcesRepository;

import java.util.List;


class StickerPagerAdapter extends ListFragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {
	private ResourcesRepository resourcesRepository;

	public StickerPagerAdapter(ResourcesRepository resourcesRepository, FragmentManager fm, List<? extends Fragment> fragmentList) {
		super(fm, fragmentList);
		this.resourcesRepository = resourcesRepository;
	}

	@Override
	public int getPageIconResId(int pageId) {
		Fragment fragment = getItem(pageId);
		if (fragment instanceof ContentPageFragment) {
			return R.drawable.category;
		}
		final String tabName = fragment.toString();
		return resourcesRepository.getIconResId(tabName);
	}


}