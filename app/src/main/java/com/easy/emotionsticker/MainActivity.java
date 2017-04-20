package com.easy.emotionsticker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.easy.emotionsticker.builder.ContentPageBuilder;
import com.easy.emotionsticker.builder.HistoryPageBuilder;
import com.easy.emotionsticker.callback.StickerCallback;
import com.easy.emotionsticker.callback.StickerCallbackImpl;
import com.easy.emotionsticker.helper.AdHelper;
import com.easy.emotionsticker.helper.ResourcesRepository;
import com.easy.emotionsticker.helper.ScreenHelper;
import com.easy.emotionsticker.helper.SettingsHelper;
import com.easy.emotionsticker.helper.StickerHistory;
import com.easy.emotionsticker.pick.AppRepository;
import com.facebook.FacebookSdk;
import com.google.android.gms.ads.AdView;


/*TODO:
- marketing
	- video turtail
	- like to unlock

- new UI
	- tutortial page
	- manifier on select
	- loadiing/updating page when first start
*/

public class MainActivity extends FragmentActivity implements CentralManager {

    private ViewPager mViewPager;
    private PagerSlidingTabStrip mPagerSlidingTab;

	private StickerCallback stickerCallback;

	private SharedPreferences settings;
	private AdHelper ad;
	private ResourcesRepository resourcesRepository;
	private AppRepository appRepository;
	private StickerHistory history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout
        setContentView(R.layout.activity_main);

	    //setup screen helper
	    ScreenHelper.setActivity(this);

	    FacebookSdk.sdkInitialize(this);
	    this.settings = SettingsHelper.getSharedPreferences(this);

	    final AdView adView = (AdView) findViewById(R.id.adView);
	    this.ad = new AdHelper(this, adView);

	    this.resourcesRepository = new ResourcesRepository(this);
	    this.appRepository = new AppRepository(this);
	    this.history = new StickerHistory(settings);


	    //init sticker callback
	    this.stickerCallback = new StickerCallbackImpl(this);


	    //init UI
	    initViewPager(settings);
	    initMenuButtons();


	    //first time alert
	    if (settings.getBoolean("com.easy.emotionsticker.v3", true) ) {
		    showSetting();
		    settings.edit().putBoolean("com.easy.emotionsticker.v3", false).commit();
	    }
    }




	@Override
	protected void onStart() {
		super.onStart();

		if (appRepository.getActiveApplications().isEmpty()) {
			appRepository.setActive("WhatsApp Messenger", true);
		}
	}

    @Override
    protected void onStop() {
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("lastTab", mViewPager.getCurrentItem());

        // Commit the edits!
        editor.commit();
    }


	@Override
	protected  void onDestroy() {
		ad.destroy();
		super.onDestroy();
	}


    //-----------------------------------------------------------------------------
    //ViewPager support


    /**
     * Initialise ViewPager
     */
    private void initViewPager(SharedPreferences settings) {
	    this.mViewPager = (ViewPager)super.findViewById(R.id.viewpager);



	    //init the fragment adapter
		StickerPagerAdapter adapter = new StickerPagerAdapter(this);

	    adapter.setStickerCallback(this.stickerCallback);

	    adapter.setOnCategorySelectCallback(
			new ContentPageBuilder.OnCategorySelectCallback() {
				@Override
				public void onCategorySelect(int i) {
					ad.show();
					mViewPager.setCurrentItem(i+2);
				}
	    });

	    mViewPager.setAdapter(adapter);

        // Bind the tabs to the ViewPager
        this.mPagerSlidingTab = (PagerSlidingTabStrip) findViewById(R.id.tabs);
	    mPagerSlidingTab.setViewPager(mViewPager);

        //restore the last read fragment
        final int lastTab = settings.getInt("lastTab", 0);
        mViewPager.setCurrentItem(lastTab); //set the tab as per the saved state


	    //set up the left sliding button
	    findViewById(R.id.btn_tab_left).setOnTouchListener(new View.OnTouchListener() {
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
			    mPagerSlidingTab.fullScroll(View.FOCUS_LEFT);
			    return false;
		    }
	    });


	    //set up the right sliding button
	    findViewById(R.id.btn_tab_right).setOnTouchListener(new View.OnTouchListener() {
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
			    mPagerSlidingTab.fullScroll(View.FOCUS_RIGHT);
			    return false;
		    }
	    });
    }


	//-----------------------------------------------------------------------------

	private void initMenuButtons() {
		ScreenHelper screenHelper = ScreenHelper.getInstance();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenHelper.getWidth()/4, 96);

		findViewById(R.id.btn_clear_history).setLayoutParams(params);

		View btnHome = findViewById(R.id.btn_home);
		btnHome.setLayoutParams(params);
		btnHome.setOnClickListener(new View.OnClickListener() {
			   public void onClick(View v) { mViewPager.setCurrentItem(0); }
	    });

		View btnHistory = findViewById(R.id.btn_history);
		btnHistory.setLayoutParams(params);
		btnHistory.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) { mViewPager.setCurrentItem(1); }
		});

		View btnSetting = findViewById(R.id.btn_setting);
		btnSetting.setLayoutParams(params);
		btnSetting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showSetting();
			}
		});

		//build history buttons
		View btnClear = findViewById(R.id.btn_clear_history);
		new HistoryPageBuilder(this).buildClearButton(btnClear);
	}

	private void showSetting() {
		Intent i = new Intent(MainActivity.this, AppPickPreferenceActivity.class);
		i.putExtra(AppPickPreferenceActivity.BUNDLE_NAME, appRepository.toStringArray());
		startActivity(i);
	}

	//-----------------------------------------------------------------------------
	//implement central manager

	@Override public Context getContext() { return this; }
	@Override public AdHelper getAdHelper() { return this.ad; }
	@Override public StickerHistory getHistory() { return this.history; }
	@Override public ResourcesRepository getResourcesRepository() { return this.resourcesRepository; }
	@Override public AppRepository getAppRepository() { return this.appRepository; }

}

