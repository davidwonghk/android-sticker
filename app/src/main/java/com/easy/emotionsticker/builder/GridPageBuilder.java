package com.easy.emotionsticker.builder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import com.easy.emotionsticker.R;
import com.easy.emotionsticker.helper.ScreenHelper;

/**
 * Created by david.wong on 20/07/2016.
 *
 * provide template methods to hep build page
 */
public abstract class GridPageBuilder<Callback> {
	protected Context context;

	protected final static int MARGIN_H = 0;
	protected final static int MARGIN_V = 0;
	protected final static int NUM_COL = 4;

	public GridPageBuilder(Context context) {
		this.context = context;
	}

	abstract protected void build(GridLayout grid, Callback callback);


	protected ViewGroup.LayoutParams getGridLayoutParams(int numCol, int marginh, int marginv) {
		final int marginSum = marginh + marginv;
		ScreenHelper screen = ScreenHelper.getInstance();
		return new ViewGroup.LayoutParams(screen.getWidth()/numCol-marginSum, screen.getHeight()/numCol-marginSum);
	}

	protected void resizeMenuItem(View view) {
		resizeMenuItem(context, view);
	}

	public static void resizeMenuItem(Context context, View view) {
		int s = context.getResources().getInteger(R.integer.menu_size);

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

