package com.easy.emotionsticker.image;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by david.wong on 01/07/2016.
 */


public class SquareImageView extends ImageView {
	public SquareImageView(Context context) {
		super(context);
		setScaleType(ScaleType.CENTER_CROP);
	}

	public SquareImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setScaleType(ScaleType.CENTER_CROP);
	}

	public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setScaleType(ScaleType.CENTER_CROP);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
	}


}
