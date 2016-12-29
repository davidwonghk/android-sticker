package com.easy.emotionsticker;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.easy.emotionsticker.image.SquareImageView;
import com.easy.emotionsticker.pick.AppPick;
import com.facebook.share.widget.LikeView;

import java.util.List;

/**
 * Created by david.wong on 13/08/2016.
 */
public class AppPickDialog extends Dialog {

	public AppPickDialog(Context context, List<AppPick> apps, final String resName) {
		super(context);
		super.setContentView(R.layout.pick_dialog);
		super.setTitle(R.string.app_pick_title);

		setupView(apps, resName);
		setupLikeButton();
	}


	final static int PADDING = 30;
	private void setupView(List<AppPick> apps, final String resName) {
		GridLayout layout = (GridLayout)findViewById(R.id.grid_pickapp);

		for(final AppPick a: apps) {
			ImageView image = new SquareImageView(getContext());
			image.setPadding(PADDING,PADDING,PADDING,PADDING);
			image.setImageResource(a.getIcon());
			image.setOnClickListener(new View.OnClickListener() {
				@Override public void onClick(View v) { a.sendToApplication(resName); }
			});
			layout.addView(image);
		}

	}

	private void setupLikeButton() {
		final String appUrl = getContext().getString(R.string.app_url);

		LikeView likeView = (LikeView) findViewById(R.id.like_view);
		likeView.setObjectIdAndType( appUrl, LikeView.ObjectType.PAGE );
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

