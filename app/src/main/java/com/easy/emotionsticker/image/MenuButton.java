package com.easy.emotionsticker.image;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;


public class MenuButton extends ImageButton {

	public MenuButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAdjustViewBounds(true);
		setScaleType(ScaleType.FIT_CENTER);
		setMaxHeight(200);
		setMinimumHeight(120);

		int color = getResources().getColor(com.facebook.R.color.com_facebook_blue);
		setBackgroundColor(color);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int w = getMeasuredWidth();
		int h = getMeasuredHeight();
		if (h < w/2) h = w/2;
		setMeasuredDimension(w, h); //Snap to width
	}
}
