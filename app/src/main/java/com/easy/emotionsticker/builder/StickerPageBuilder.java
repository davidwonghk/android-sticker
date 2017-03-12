package com.easy.emotionsticker.builder;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.easy.emotionsticker.helper.ResourcesRepository;
import com.easy.emotionsticker.image.SquareImageView;

/**
 * Created by david on 12/03/2017.
 */

public class StickerPageBuilder extends GridPageBuilder<StickerPageHandle> {

	private ResourcesRepository resourcesRepository;

	public StickerPageBuilder(Context context, ResourcesRepository repo) {
		super(context);
		this.resourcesRepository = repo;
	}


	@Override
	public void build(GridLayout grid, final StickerPageHandle handle) {
		final int tabSize = resourcesRepository.getTabSize(handle.tabName);
		for (int i=0; i<tabSize; ++i) {
			final String stickerId = resourcesRepository.getStickerId(handle.tabName, i);
			Uri iconUri = resourcesRepository.getSticker(stickerId);

			ImageView icon = new SquareImageView(context);
			icon.setImageURI(iconUri);
			icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			icon.setPadding(MARGIN_H, MARGIN_V, MARGIN_H, MARGIN_V);
			icon.setLayoutParams(getGridLayoutParams(NUM_COL, MARGIN_H, MARGIN_V));

			icon.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					handle.callback.onStickerSelect(stickerId);
				}
			});

			grid.addView(icon);
		}
	}
}
