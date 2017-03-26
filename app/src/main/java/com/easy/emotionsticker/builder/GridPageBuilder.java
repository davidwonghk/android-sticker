package com.easy.emotionsticker.builder;

import android.view.ViewGroup;
import android.widget.GridLayout;

import com.easy.emotionsticker.helper.ScreenHelper;

public abstract class GridPageBuilder<Callback> {

	protected final static int NUM_COL = 4;

	abstract protected void build(GridLayout grid, Callback callback);


	protected ViewGroup.LayoutParams getGridLayoutParams(int numCol) {
		ScreenHelper screen = ScreenHelper.getInstance();
		return new ViewGroup.LayoutParams(screen.getWidth()/numCol, screen.getHeight()/numCol);
	}

}

