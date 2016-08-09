package com.easy.emotionsticker.builder;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.easy.emotionsticker.R;
import com.easy.emotionsticker.helper.ResourcesRepository;
import com.easy.emotionsticker.image.SquareImageView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

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
			ImageView icon = new SquareImageView(activity);
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

	public void buildHomeButton(View btnHome, final SlidingUpPanelLayout slidingLayout) {
		//set the button size
		resizeMenuItem(btnHome);

		//set the onclick listener
		btnHome.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				switch(slidingLayout.getPanelState()) {
					case EXPANDED:
						slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
						break;
					case COLLAPSED:
						slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
						break;
				}
			}
		});
	}


	public interface OnCategorySelectCallback {
		void onCategorySelect(int i);
	}
}
