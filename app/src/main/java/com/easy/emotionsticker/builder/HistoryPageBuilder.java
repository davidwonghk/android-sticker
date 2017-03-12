package com.easy.emotionsticker.builder;

import android.app.Activity;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.easy.emotionsticker.R;
import com.easy.emotionsticker.callback.StickerCallback;
import com.easy.emotionsticker.helper.ResourcesRepository;
import com.easy.emotionsticker.helper.StickerHistory;
import com.easy.emotionsticker.image.ZoomSquareImageView;



/**
 * Created by david.wong on 01/07/2016.
 */
public class HistoryPageBuilder extends GridPageBuilder<StickerCallback> {

	private final static String TAG = "HistoryPageBuilder";

	private final static int MARGIN = 0;

	private StickerHistory history;
	private ResourcesRepository resourcesRepository;


	public HistoryPageBuilder(Activity activity, ResourcesRepository resourcesRepository, StickerHistory history) {
		super(activity);
		this.resourcesRepository = resourcesRepository;
		this.history = history;
	}


	synchronized
	public void buildMenubar(final GridLayout grid, final StickerCallback callback) {
		Log.d(TAG, "build history menu bar");
		grid.removeAllViews();

		for (final String h : history.get()) {
			Log.d(TAG, h);

			Uri iconUri = resourcesRepository.getSticker(h);
			ImageView icon = new ZoomSquareImageView(context);
			icon.setImageURI(iconUri);

			icon.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					callback.onStickerSelect(h);
				}
			});
			resizeMenuItem(icon);
			grid.addView(icon);

		}

		history.addOnHistoryChange("menu", new StickerHistory.OnHistoryChangeCallback() {
			@Override
			public void onHistoryChange(StickerHistory history) {
				buildMenubar(grid, callback);
			}
		});
	}

	@Override
	synchronized
	public void build(final GridLayout grid, final StickerCallback callback) {
		Log.d(TAG, "build history page");
		grid.removeAllViews();

		for (final String h : history.get()) {
			Log.d(TAG, h);

			Uri stickerUri = resourcesRepository.getSticker(h);
			ImageView sticker = new ZoomSquareImageView(context);
			sticker.setImageURI(stickerUri);

			sticker.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					callback.onStickerSelect(h);
				}
			});
			sticker.setScaleType(ImageView.ScaleType.FIT_XY);
			sticker.setPadding(10, 10, 10, 10);
			sticker.setLayoutParams(getGridLayoutParams(NUM_COL, 10, 10));
			grid.addView(sticker);

		}

		grid.refreshDrawableState();

		//setup history callback
		history.addOnHistoryChange("page", new StickerHistory.OnHistoryChangeCallback() {
			@Override
			public void onHistoryChange(StickerHistory history) {
				build(grid, callback);
			}
		});


	}

	public void buildClearButton(View btnClearHistory) {
		//set the button size
		resizeMenuItem(btnClearHistory);

		//set the onclick listener
		btnClearHistory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(context)
						.setTitle(R.string.alert_title)
						.setMessage(R.string.alert_clear_history)
						.setNegativeButton(R.string.CANCEL, new DialogInterface.OnClickListener() {
							@Override public void onClick(DialogInterface dialog, int which) { }
						})
						.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
							@Override public void onClick(DialogInterface dialog, int which) {
								history.clear();
							}
						})
						.setIcon(android.R.drawable.ic_dialog_alert)
						.create().show();

			}
		});
	}



}
