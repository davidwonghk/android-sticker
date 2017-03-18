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
import com.easy.emotionsticker.helper.AdHelper;


/**
 * Created by david on 02/09/2016.
 */
public class HistoryPageFragment extends Fragment {
	private HistoryPageBuilder builder;
	private StickerCallback callback;
	private AdHelper ad;

	public void setHistoryPageBuilder(HistoryPageBuilder builder) {
		this.builder = builder;
	}

	public void setCallback(StickerCallback callback) {
		this.callback = callback;
	}

	public void setAdHelper(AdHelper ad) { this.ad = ad; }

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

		//load facebook ad
		/*
		ViewGroup adViewContainer = (ViewGroup) view.findViewById(R.id.adViewContainer);
		ad.loadFacebookAd(adViewContainer);
		*/

		return view;
	}


	@Override
	public String toString() {
		return "history";
	}


}
