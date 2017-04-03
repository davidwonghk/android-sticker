package com.easy.emotionsticker.image;

import android.content.Context;

import pl.droidsonroids.gif.GifImageView;


public class SquareGifImageView extends GifImageView {
	public SquareGifImageView(Context context) {
		super(context);
		setScaleType(ScaleType.CENTER_CROP);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
	}
}
