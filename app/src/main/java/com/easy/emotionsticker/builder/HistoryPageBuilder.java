package com.easy.emotionsticker.builder;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.easy.emotionsticker.CentralManager;
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

	private final static int MARGIN = 15;

	private Context context;
	private StickerHistory history;
	private ResourcesRepository resourcesRepository;


	public HistoryPageBuilder(CentralManager manager) {
		this.context = manager.getContext();
		this.resourcesRepository = manager.getResourcesRepository();
		this.history = manager.getHistory();
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
			sticker.setPadding(MARGIN, MARGIN, MARGIN, MARGIN);
			sticker.setLayoutParams(getGridLayoutParams(NUM_COL));
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
