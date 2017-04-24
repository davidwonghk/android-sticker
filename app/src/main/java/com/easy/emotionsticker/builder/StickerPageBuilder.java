package com.easy.emotionsticker.builder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.easy.emotionsticker.CentralManager;
import com.easy.emotionsticker.R;
import com.easy.emotionsticker.callback.StickerCallback;
import com.easy.emotionsticker.helper.ResourcesRepository;
import com.easy.emotionsticker.image.SquareImageView;
import com.easy.emotionsticker.image.StickerImage;

/**
 * Created by david.wong on 18/06/2016.
 */
public class StickerPageBuilder {

	private static CentralManager manager;


	public StickerPageBuilder(CentralManager manager) {
		this.manager = manager;
	}

	public View createView(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.sticker_tab, container, false);
	}

	public void clearView(View view) {
		GridLayout grid = (GridLayout) view.findViewById(R.id.content_grid);
		int oldSize = grid.getChildCount();
		Log.d("TAG", "oldSize: " + String.valueOf(oldSize));
		for (int i=0; i<oldSize; ++i) {
			View v = grid.getChildAt(i);
			if (v instanceof ImageView) {
				((ImageView) v).setImageResource(0);
			}
		}
		grid.removeAllViews();
	}

	public View updateView(View view, final String tabName, final StickerCallback callback) {
		final ResourcesRepository resourcesRepository = manager.getResourcesRepository();
		final Context context = manager.getContext();

		GridLayout grid = (GridLayout) view.findViewById(R.id.content_grid);

		final int size = resourcesRepository.getTabSize(tabName);
		for(int i=0; i<size; ++i) {
			final String stickerId = resourcesRepository.getStickerId(tabName, i);

			ImageView icon = new SquareImageView(context);
			icon.setImageURI(resourcesRepository.getSticker(stickerId));
			icon = StickerImage.process(icon);
			icon.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					callback.onStickerSelect(stickerId);
				}
			});

			grid.addView(icon);
		}

		return view;
	}



}

