package com.easy.emotionsticker.image;

import android.widget.ImageView;

import static com.easy.emotionsticker.helper.ScreenHelper.getGridLayoutParams;

/**
 * Created by david on 20/04/2017.
 */

public class StickerImage {
	private final static int NUM_COL = 4;
	private final static int MARGIN = 15;

	public static ImageView process(ImageView icon) {
		icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
		icon.setPadding(MARGIN, MARGIN, MARGIN, MARGIN);
		icon.setLayoutParams(getGridLayoutParams(NUM_COL));
		return icon;
	}
}
