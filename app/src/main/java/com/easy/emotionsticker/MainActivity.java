package com.easy.emotionsticker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.easy.emotionsticker.builder.ContentPageBuilder;
import com.easy.emotionsticker.builder.GridPageBuilder;
import com.easy.emotionsticker.builder.HistoryPageBuilder;
import com.easy.emotionsticker.callback.StickerCallback;
import com.easy.emotionsticker.callback.StickerCallbackImpl;
import com.easy.emotionsticker.fragment.ContentPageFragment;
import com.easy.emotionsticker.fragment.HistoryPageFragment;
import com.easy.emotionsticker.fragment.StickerFragmentListBuilder;
import com.easy.emotionsticker.helper.AdHelper;
import com.easy.emotionsticker.helper.ResourcesRepository;
import com.easy.emotionsticker.helper.ScreenHelper;
import com.easy.emotionsticker.helper.SettingsHelper;
import com.easy.emotionsticker.helper.StickerHistory;
import com.easy.emotionsticker.pick.AppRepository;
import com.google.android.gms.ads.AdView;

import java.util.List;


/*TODO:
- increase stickers image quality
- marketing
	- video turtail

- new UI
	- turtial page
	- manifier on select
	- loadiing/updating page when first start
*/

public class MainActivity extends FragmentActivity {

    private AdHelper ad;
    private ViewPager mViewPager;
    private PagerSlidingTabStrip mPagerSlidingTab;

	private ResourcesRepository resourcesRepository;
	private StickerCallback stickerCallback;
	private SharedPreferences settings;
	private StickerHistory history;
	private AppRepository appRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout
        setContentView(R.layout.activity_main);

	    //setup screen helper
	    ScreenHelper.setActivity(this);

	    //check if network is on and whatsapp installed
	    //DeviceStatusChecker checker = new DeviceStatusChecker(this);
	    //checker.preStickerCheck();

	    //init all the component classes
	    this.resourcesRepository = new ResourcesRepository();

	    //create application repository
	    this.appRepository = new AppRepository(this);

	    //init advertisement
	    final AdView adView = (AdView) findViewById(R.id.adView);
	    this.ad = new AdHelper(this, adView);

	    //create helper components
	    this.settings = SettingsHelper.getSharedPreferences(this);
	    this.history = new StickerHistory(settings);

	    //init sticker callback
	    this.stickerCallback = new StickerCallbackImpl(this, history, appRepository, ad);

	    //init UI
	    initViewPager(settings);
	    initSettingButton();

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




    //-----------------------------------------------------------------------------
    //ViewPager support


    /**
     * Initialise ViewPager
     */
    private void initViewPager(SharedPreferences settings) {
	    this.mViewPager = (ViewPager)super.findViewById(R.id.viewpager);

	    Fragment contentPage = createContentPage(mViewPager);
	    Fragment historyPage = createHistoryPage(mViewPager);

	    StickerFragmentListBuilder listBuilder = new StickerFragmentListBuilder(resourcesRepository);
	    List<? extends Fragment> fragmentList = listBuilder.build(stickerCallback, contentPage, historyPage);

	    mViewPager.setAdapter(new StickerPagerAdapter(resourcesRepository, getSupportFragmentManager(), fragmentList));

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
	//init content and history page

	private ContentPageFragment createContentPage(final ViewPager viewPager) {
		assert(viewPager != null);
		ContentPageBuilder builder = new ContentPageBuilder(this, resourcesRepository);

		ContentPageFragment fragment = new ContentPageFragment();
		fragment.setContentPageBuilder(builder);
		fragment.setCallback(new ContentPageBuilder.OnCategorySelectCallback() {
			@Override
			public void onCategorySelect(int i) {
				viewPager.setCurrentItem(i+2);
			}
		});

		builder.buildHomeButton(findViewById(R.id.btn_home), viewPager);

		return fragment;
	}


	private HistoryPageFragment createHistoryPage(final ViewPager viewPager) {
		assert(viewPager != null);
		final HistoryPageBuilder builder = new HistoryPageBuilder(this, resourcesRepository, history);

		//setup the history page
		HistoryPageFragment fragment = new HistoryPageFragment();
		fragment.setHistoryPageBuilder(builder);
		fragment.setCallback(stickerCallback);

		//setup the history bar
		final GridLayout grid = (GridLayout)findViewById(R.id.history_bar);
		builder.buildMenubar(grid, stickerCallback);

		//set up the history clear button
		builder.buildClearButton(findViewById(R.id.btn_clear_history));

		return fragment;
	}


	private void initSettingButton() {
		final ImageView btnSetting = (ImageView) findViewById(R.id.btn_setting);
		GridPageBuilder.resizeMenuItem(this, btnSetting);
		btnSetting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showSetting();
			}
		});
	}

	private void showSetting() {
		Intent i = new Intent(MainActivity.this, AppPickPreferenceActivity.class);
		i.putExtra(AppPickPreferenceActivity.BUNDLE_NAME, appRepository.toStringArray());
		startActivity(i);
	}

	//-----------------------------------------------
	//option menu

	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.action_end:
				finish();
				System.exit(0);
				return true;

			case R.id.action_home:
				mViewPager.setCurrentItem(0);
				return true;

			case R.id.action_customize:
				Intent i = new Intent(this, AppPickPreferenceActivity.class);
				i.putExtra(AppPickPreferenceActivity.BUNDLE_NAME, appRepository.toStringArray());
				startActivity(i);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	*/

}

