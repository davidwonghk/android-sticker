package com.easy.emotionsticker;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.astuetz.PagerSlidingTabStrip;
import com.easy.emotionsticker.fragment.ContentPageFragment;
import com.easy.emotionsticker.fragment.HistoryPageFragment;
import com.easy.emotionsticker.helper.ResourcesRepository;

import java.util.List;


class StickerPagerAdapter extends ListFragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {
	private ResourcesRepository resourcesRepository;

	public StickerPagerAdapter(ResourcesRepository resourcesRepository, FragmentManager fm, List<? extends Fragment> fragmentList) {
		super(fm, fragmentList);
		this.resourcesRepository = resourcesRepository;
	}

	@Override
	public Uri getPageIcon(int pageId) {
		Fragment fragment = getItem(pageId);
		if (fragment instanceof ContentPageFragment) {
			return resourcesRepository.getCategoryTabIcon();
		}
		if (fragment instanceof HistoryPageFragment) {
			return resourcesRepository.getHistoryTabIcon();
		}

		final String tabName = fragment.toString();
		return resourcesRepository.getTabIcon(tabName);
	}


}
