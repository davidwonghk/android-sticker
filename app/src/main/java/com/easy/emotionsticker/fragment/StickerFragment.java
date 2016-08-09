package com.easy.emotionsticker.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.easy.emotionsticker.R;

/**
 * Created by david.wong on 18/06/2016.
 */
public class StickerFragment extends BaseFragment {
	private final static int NUM_COL = 4;


	//TODO: use arguemtn bundle istead of setter
	@Override
	public String toString() {
		return tabName;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
		final View view = inflater.inflate(R.layout.sticker_tab, container, false);

		final ImageView imageView = (ImageView)view.findViewById(R.id.mergeImage);

		/***** set up the merge image for stickers *****/
		int mergeImageResId = resourcesRepository.getMergeImageResId(tabName);
		imageView.setImageResource(mergeImageResId);

		/***** handle click event *****/
		imageView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//by pass the touch event to parent scrollview
				return false;
			}
		});


		//use the parent scrollview to detect the mouse click position
		final View hview = view.findViewById(R.id.hview);
		hview.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() != MotionEvent.ACTION_UP) return false;
				long clickTime =  event.getEventTime() - event.getDownTime();
				if (clickTime > 300l) return false;

				final float w = (float)imageView.getMeasuredWidth() / NUM_COL;
				float i = event.getX()/w;
				float j = (event.getY() + hview.getScrollY())/w;

				if (!inRegion(i, 15) || !inRegion(j, 15)) return false;
				int selected = (int)i + (int)j*NUM_COL;
				Log.d("coord ", String.valueOf(selected));

				String resName = resourcesRepository.getSticker(tabName, selected);
				if (resName != null) {
					callback.onStickerSelect(resName);
				}

				return true;
			}
		});

		return view;
	}



	private static boolean inRegion(float i, int bp) {
		int fp = (int)(i * 100) % 100;
		return (fp > bp && fp < 100-bp);
	}

}

