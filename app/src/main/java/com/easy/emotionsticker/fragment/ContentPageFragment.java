package com.easy.emotionsticker.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.easy.emotionsticker.R;
import com.easy.emotionsticker.builder.ContentPageBuilder;

/**
 * Created by david.wong on 09/08/2016.
 */
public class ContentPageFragment extends Fragment {
	private ContentPageBuilder builder;
	private ContentPageBuilder.OnCategorySelectCallback callback;

	public void setContentPageBuilder(ContentPageBuilder builder) {
		this.builder = builder;
	}

	public void setCallback(ContentPageBuilder.OnCategorySelectCallback callback) {
		this.callback = callback;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {

		final View view = inflater.inflate(R.layout.sticker_tab, container, false);

		final GridLayout grid = (GridLayout)view.findViewById(R.id.content_grid);
		builder.build(grid, callback);

		return view;
	}

	@Override
	public String toString() {
		return "category";
	}
}
