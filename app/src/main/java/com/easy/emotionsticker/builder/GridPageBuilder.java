package com.easy.emotionsticker.builder;

import android.app.Activity;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import com.easy.emotionsticker.R;

/**
 * Created by david.wong on 20/07/2016.
 *
 * provide template methods to hep build page
 */
abstract class GridPageBuilder<Callback> {
	protected Activity activity;
	private Point screenSize;

	public GridPageBuilder(Activity activity) {
		this.activity = activity;
		this.screenSize = getScreenSize();
	}

	abstract protected void build(GridLayout grid, Callback callback);


	protected ViewGroup.LayoutParams getGridLayoutParams(int numCol, int marginh, int marginv) {
		final int marginSum = marginh + marginv;
		return new ViewGroup.LayoutParams(screenSize.x/numCol-marginSum, screenSize.y/numCol-marginSum);
	}



	private Point getScreenSize() {
		Point size = new Point();
		activity.getWindowManager().getDefaultDisplay().getSize(size);
		return size;
	}

	protected void resizeMenuItem(View view) {
		int s = activity.getResources().getInteger(R.integer.menu_size);

		ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
		if (layoutParams == null) {
			layoutParams = new RelativeLayout.LayoutParams(s, s);
		} else {
			layoutParams.width = s;
			layoutParams.height = s;
		}
		view.setLayoutParams(layoutParams);
		view.requestLayout();
	}
}

