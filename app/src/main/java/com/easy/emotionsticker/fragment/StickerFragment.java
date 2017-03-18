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

	private static StickerCallback callback;
	private static StickerPageBuilder builder;


	public static void setCallback(StickerCallback tcallback) { callback = tcallback; }
	public static void setStickerPageBuilder(StickerPageBuilder tbuilder) { builder = tbuilder; }


	@Override
	public String toString() {
		return getArguments().getString("tabName");
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
		final View view = inflater.inflate(R.layout.sticker_tab, container, false);

		final GridLayout grid = (GridLayout)view.findViewById(R.id.content_grid);
		builder.build(grid, new StickerPageHandle(toString(), callback));

		return view;
	}

}

