package com.easy.emotionsticker;

import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.easy.emotionsticker.builder.ContentPageBuilder;
import com.easy.emotionsticker.builder.HistoryPageBuilder;
import com.easy.emotionsticker.builder.StickerPageBuilder;
import com.easy.emotionsticker.callback.StickerCallback;
import com.easy.emotionsticker.helper.ResourcesRepository;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by david on 20/04/2017.
 */

public class StickerPagerAdapter extends PagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

	private final ResourcesRepository resourcesRepository;
	private final List<String> stickerTabs;
	private final LayoutInflater inflater;

	private HistoryPageBuilder historyPageBuilder;
	private ContentPageBuilder contentPageBuilder;
	private StickerPageBuilder stickerPageBuilder;

	private View contentView, historyView;
	private Map<String, View> stickerViews;

	private ContentPageBuilder.OnCategorySelectCallback onCategorySelectCallback;
	private StickerCallback stickerCallback;

	public StickerPagerAdapter(CentralManager manager) {
		this.resourcesRepository  = manager.getResourcesRepository();
		this.stickerTabs = resourcesRepository.getTabsOrder();
		this.inflater = LayoutInflater.from(manager.getContext());

		this.historyPageBuilder = new HistoryPageBuilder(manager);
		this.contentPageBuilder = new ContentPageBuilder(manager);
		this.stickerPageBuilder = new StickerPageBuilder(manager);

		this.stickerViews = new WeakHashMap<>();

	}

	@Override
	public View instantiateItem(ViewGroup container, final int position) {
		View view = getPageView(container, position);

		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}

		container.addView(view);
		return view;
	}

	protected View getPageView(ViewGroup container, final int position) {
		switch (position) {
			case 0: return getContentView(container);
			case 1: return getHistoryView(container);
			default: {
				final String tabName = stickerTabs.get(position-2);
				return getStickerView(container, tabName);
			}
		}
	}

	private View getContentView(ViewGroup container) {
		if (contentView != null) return contentView;
		this.contentView = contentPageBuilder.createView(inflater, container, onCategorySelectCallback);
		return contentView;
	}

	private View getHistoryView(ViewGroup container) {
		if (historyView != null) return historyView;
		this.historyView = historyPageBuilder.createView(inflater, container, stickerCallback);
		return historyView;
	}

	private View getStickerView(ViewGroup container, String tabName) {
		View view = stickerViews.get(tabName);
		if (view == null) {
			view = stickerPageBuilder.createView(inflater, container);
			stickerPageBuilder.updateView(view, tabName, stickerCallback);
			stickerViews.put(tabName, view);
		}

		return view;
	}


	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		Log.d("TAG", "destroyItem: ");
		if (false == object instanceof View) return;
		View view = (View) object;

		if (view.getParent() == container) {
			container.removeView(view);
		}

	}


	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}


	@Override
	public int getCount() {
		return 2 + stickerTabs.size();
	}


	@Override
	public Uri getPageIcon(int pageId) {
		switch(pageId) {
			case 0: return resourcesRepository.getCategoryTabIcon();
			case 1: return resourcesRepository.getHistoryTabIcon();
			default: {
				String tabName = stickerTabs.get(pageId - 2);
				return resourcesRepository.getTabIcon(tabName);
			}
		}
	}

	public void setOnCategorySelectCallback(ContentPageBuilder.OnCategorySelectCallback onCategorySelectCallback) {
		this.onCategorySelectCallback = onCategorySelectCallback;
	}

	public void setStickerCallback(StickerCallback stickerCallback) {
		this.stickerCallback = stickerCallback;
	}
}
