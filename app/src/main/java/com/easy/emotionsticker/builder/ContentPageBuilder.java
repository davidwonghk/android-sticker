package com.easy.emotionsticker.builder;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.easy.emotionsticker.CentralManager;
import com.easy.emotionsticker.R;
import com.easy.emotionsticker.helper.ResourcesRepository;
import com.easy.emotionsticker.image.SquareGifImageView;
import com.easy.emotionsticker.image.SquareImageView;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class ContentPageBuilder extends GridPageBuilder<ContentPageBuilder.OnCategorySelectCallback> {

	private final static int MARGIN = 30;

	private CentralManager manager;

	public ContentPageBuilder(CentralManager manager) {
		this.manager = manager;
	}

	@Override
	public void build(GridLayout grid, final OnCategorySelectCallback callback) {
		final ResourcesRepository resourcesRepository = manager.getResourcesRepository();


		final List<String> tabs = resourcesRepository.getTabsOrder();
		for (int i=0; i<tabs.size(); ++i) {
			String tab = tabs.get(i);
			Uri iconUri = resourcesRepository.getTabIcon(tab);

			ImageView icon = new SquareImageView(manager.getContext());
			icon.setImageURI(iconUri);
			imageSettings(icon);

			final int index = i;
			icon.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					callback.onCategorySelect(index);			;
				}
			});

			grid.addView(icon);
		}

		addGifAd(grid);
	}


	public interface OnCategorySelectCallback {
		void onCategorySelect(int i);
	}

	/**
	 *
	 * add an advertisement of Gif Animatoin Sticker to the content page
	 */
	private void addGifAd(GridLayout grid) {
		final Context context = manager.getContext();
		ImageView icon = new SquareGifImageView(context);
		icon.setImageResource(R.drawable.gif);
		imageSettings(icon);

		icon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
						"market://details?id=com.easy.gifsticker"
				));
				context.startActivity(browserIntent);
			}
		});

		grid.addView(icon);
	}

	private void imageSettings(ImageView icon) {
		icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
		icon.setPadding(MARGIN, MARGIN, MARGIN, MARGIN);
		icon.setLayoutParams(getGridLayoutParams(NUM_COL));

	}
}
