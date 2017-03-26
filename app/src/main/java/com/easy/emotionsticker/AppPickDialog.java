package com.easy.emotionsticker;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.easy.emotionsticker.helper.AdHelper;
import com.easy.emotionsticker.image.SquareImageView;
import com.easy.emotionsticker.pick.AppPick;
import com.easy.emotionsticker.pick.AppRepository;

import java.util.List;

/**
 * Created by david.wong on 13/08/2016.
 */
public class AppPickDialog extends Dialog {

	private AppRepository appRepository;

	public AppPickDialog(Context context, AppRepository appRepository, final Uri resName) {
		super(context);
		super.setContentView(R.layout.pick_dialog);
		super.setTitle(R.string.app_pick_title);

		this.appRepository = appRepository;
		setupView(resName);
	}


	private void setupView(final Uri resName) {
		List<AppPick> activeApps = appRepository.getActiveApplications();
		setupAppView(resName, activeApps);

		ImageView imageDemo = (ImageView)findViewById(R.id.image_demo);
		imageDemo.setImageURI(resName);

		View btnMore = findViewById(R.id.btn_more);

		final List<AppPick> allApps = appRepository.getAllApplications();
		if (allApps.size() == activeApps.size()) {
			btnMore.setVisibility(View.INVISIBLE);
		}

		btnMore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setupAppView(resName, allApps);
				v.setVisibility(View.INVISIBLE);
			}
		});
	}

	private void setupAppView(final Uri resName, List<AppPick> apps) {
		final int PADDING = 30;
		GridLayout layout = (GridLayout)findViewById(R.id.grid_pickapp);
		layout.removeAllViews();

		for(final AppPick a: apps) {
			ImageView image = new SquareImageView(getContext());
			image.setPadding(PADDING,PADDING,PADDING,PADDING);
			image.setImageResource(a.getIcon());
			image.setOnClickListener(new View.OnClickListener() {
				@Override public void onClick(View v) {
					a.sendToApplication(resName);
				}
			});
			layout.addView(image);
		}
	}


	@Override
	public void show() {
		GridLayout layout = (GridLayout)findViewById(R.id.grid_pickapp);
		if (layout.getChildCount() == 1) {
			layout.getChildAt(0).callOnClick();
		} else {
			super.show();
		}
	}




}

