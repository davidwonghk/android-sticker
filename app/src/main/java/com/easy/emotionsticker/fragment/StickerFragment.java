package com.easy.emotionsticker.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.easy.emotionsticker.R;
import com.easy.emotionsticker.builder.StickerPageBuilder;
import com.easy.emotionsticker.builder.StickerPageHandle;
import com.easy.emotionsticker.callback.StickerCallback;

/**
 * Created by david.wong on 18/06/2016.
 */
public class StickerFragment extends Fragment {

	protected String tabName;
	protected StickerCallback callback;
	private StickerPageBuilder builder;

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public void setCallback(StickerCallback callback) {
		this.callback = callback;
	}

	@Override
	public String toString() {
		return tabName;
	}

	public void setStickerPageBuilder(StickerPageBuilder builder) {
		this.builder = builder;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {

		final View view = inflater.inflate(R.layout.sticker_tab, container, false);

		final GridLayout grid = (GridLayout)view.findViewById(R.id.content_grid);
		builder.build(grid, new StickerPageHandle(tabName, callback));

		return view;
	}

}

