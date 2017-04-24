package com.easy.emotionsticker.builder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.easy.emotionsticker.CentralManager;
import com.easy.emotionsticker.R;
import com.easy.emotionsticker.helper.ResourcesRepository;
import com.easy.emotionsticker.image.SquareGifImageView;
import com.easy.emotionsticker.image.SquareImageView;
import com.easy.emotionsticker.image.StickerImage;

import java.util.List;

public class ContentPageBuilder {


	private CentralManager manager;

	public ContentPageBuilder(CentralManager manager) {
		this.manager = manager;
	}

	public View createView(LayoutInflater inflater, ViewGroup container, OnCategorySelectCallback callback) {

		final View view = inflater.inflate(R.layout.sticker_tab, container, false);
		final GridLayout grid = (GridLayout)view.findViewById(R.id.content_grid);
		build(grid, callback);

		return view;
	}

	private void build(GridLayout grid, final OnCategorySelectCallback callback) {
		final Context context = manager.getContext();
		final ResourcesRepository resourcesRepository = manager.getResourcesRepository();


		final List<String> tabs = resourcesRepository.getTabsOrder();
		for (int i=0; i<tabs.size(); ++i) {
			String tab = tabs.get(i);
			Uri iconUri = resourcesRepository.getTabIcon(tab);

			ImageView icon = new SquareImageView(context);
			icon = StickerImage.process(icon);
			icon.setImageURI(iconUri);

			final int index = i;
			icon.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					callback.onCategorySelect(index);			;
				}
			});

			grid.addView(icon);
		}

		//addGifAd(grid);
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
		icon = StickerImage.process(icon);
		icon.setImageResource(R.drawable.gif);

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

}
