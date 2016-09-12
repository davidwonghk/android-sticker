package com.easy.emotionsticker.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.easy.emotionsticker.R;
import com.easy.emotionsticker.builder.HistoryPageBuilder;
import com.easy.emotionsticker.callback.StickerCallback;

/**
 * Created by david on 02/09/2016.
 */
public class HistoryPageFragment extends Fragment {
	private HistoryPageBuilder builder;
	private StickerCallback callback;

	public void setHistoryPageBuilder(HistoryPageBuilder builder) {
		this.builder = builder;
	}

	public void setCallback(StickerCallback callback) {
		this.callback = callback;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {

		final View view = inflater.inflate(R.layout.history_page, container, false);

		final GridLayout grid = (GridLayout)view.findViewById(R.id.history_grid);
		builder.build(grid, new StickerCallback() {
			@Override
			public void onStickerSelect(String resName) {
				callback.onStickerSelect(resName);
			}
		});

		return view;
	}

	@Override
	public String toString() {
		return "history";
	}

}
