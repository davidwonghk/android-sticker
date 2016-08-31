package com.easy.emotionsticker.builder;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.easy.emotionsticker.helper.ResourcesRepository;
import com.easy.emotionsticker.image.SquareImageView;

/**
 * Created by david.wong on 01/07/2016.
 */
public class ContentPageBuilder extends GridPageBuilder<ContentPageBuilder.OnCategorySelectCallback> {

	private final static int MARGIN_H = 0;
	private final static int MARGIN_V = 0;
	private final static int NUM_COL = 4;


	private ResourcesRepository resourcesRepository;

	public ContentPageBuilder(Activity activity, ResourcesRepository repo) {
		super(activity);
		this.resourcesRepository = repo;
	}

	@Override
	public void build(GridLayout grid, final OnCategorySelectCallback callback) {

		final String[] tabs = resourcesRepository.getTabsOrder();
		for (int i=0; i<tabs.length; ++i) {
			String tab = tabs[i];
			int iconId = resourcesRepository.getIconResId(tab);
			ImageView icon = new SquareImageView(context);
			icon.setImageResource(iconId);
			icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			icon.setPadding(MARGIN_H, MARGIN_V, MARGIN_H, MARGIN_V);
			icon.setLayoutParams(getGridLayoutParams(NUM_COL, MARGIN_H, MARGIN_V));

			final int index = i;
			icon.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					callback.onCategorySelect(index);			;
				}
			});

			grid.addView(icon);
		}
	}

	public void buildHomeButton(View btnHome, final ViewPager pager) {
		//set the button size
		resizeMenuItem(btnHome);

		//set the onclick listener
		btnHome.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pager.setCurrentItem(0);
			}
		});
	}


	public interface OnCategorySelectCallback {
		void onCategorySelect(int i);
	}
}
