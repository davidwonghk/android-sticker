package com.easy.emotionsticker.helper;

import android.content.Context;
import android.preference.Preference;
import android.view.ViewGroup;

import com.easy.emotionsticker.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdSettings;


/**
 * Created by david on 6/5/2016.
 */
public class AdHelper {

	//-----------------------------------------------------------------------------
	//Advertisement

	private Context context;
	private InterstitialAd mInterstitialAd;

	public AdHelper(Context context, AdView adView) {
		this.context = context;
		AdSettings.addTestDevice(context.getString(R.string.test_device_id));
		if (adView != null) {
			createBanner(adView);
			createIntertitalAd(context);
		}
	}



	private void createBanner(AdView adView) {
		// Load an ad into the AdMob banner view.
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
	}


	private void createIntertitalAd(Context context) {
		// Create the InterstitialAd and set the adUnitId (defined in values/strings.xml).
		this.mInterstitialAd = new InterstitialAd(context);
		mInterstitialAd.setAdUnitId(context.getString(R.string.interstitial_ad_unit_id));
		mInterstitialAd.setAdListener(new AdListener() {
			@Override public void onAdLoaded() { }
			@Override public void onAdFailedToLoad(int errorCode) { }

			@Override
			public void onAdClosed() {
				// Proceed to the next level.
				goToNextLevel();
			}
		});

		loadInterstitial();
	}

	public void show() {
		loadInterstitial();
		showInterstitial();
	}

	public void destroy() {
	}

	private void showInterstitial() {
		// Show the ad if it's ready. Otherwise toast and reload the ad.
		if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
			mInterstitialAd.show();
		} else {
			goToNextLevel();
		}
	}

	private void loadInterstitial() {
		// Disable the next level button and load the ad.
		AdRequest adRequest = new AdRequest.Builder().build();
		mInterstitialAd.loadAd(adRequest);
	}

	private void goToNextLevel() {
		// Show the next level and reload the ad to prepare for the level after.
		loadInterstitial();
	}
}
