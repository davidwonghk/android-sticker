package com.easy.emotionsticker.preference;

import android.app.Activity;
import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.easy.emotionsticker.CentralManager;
import com.easy.emotionsticker.MainActivity;
import com.facebook.ads.AdView;


public class FacebookAdPreference extends Preference {

	public FacebookAdPreference(Context context, AttributeSet attrs, int defStyle) {super    (context, attrs, defStyle);}
	public FacebookAdPreference(Context context, AttributeSet attrs) {super(context, attrs);}
	public FacebookAdPreference(Context context) {super(context);}


	@Override
	protected View onCreateView(ViewGroup parent) {
		// this will create the linear layout defined in ads_layout.xml
		View view = super.onCreateView(parent);

		// the context is a PreferenceActivity
		Context context = getContext();
		if (context instanceof CentralManager) {
			// Create the adView
			((CentralManager) context).getAdHelper().loadFacebookAd((ViewGroup)view);
		}

		return view;
	}
}

