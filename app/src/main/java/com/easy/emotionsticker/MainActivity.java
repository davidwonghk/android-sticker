package com.easy.emotionsticker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.easy.emotionsticker.builder.ContentPageBuilder;
import com.easy.emotionsticker.builder.HistoryPageBuilder;
import com.easy.emotionsticker.callback.StickerCallback;
import com.easy.emotionsticker.callback.StickerCallbackImpl;
import com.easy.emotionsticker.fragment.ContentPageFragment;
import com.easy.emotionsticker.fragment.StickerFragmentListBuilder;
import com.easy.emotionsticker.helper.AdHelper;
import com.easy.emotionsticker.helper.MyAlertDialog;
import com.easy.emotionsticker.helper.ResourcesRepository;
import com.easy.emotionsticker.helper.ScreenHelper;
import com.easy.emotionsticker.helper.SettingsHelper;
import com.easy.emotionsticker.helper.StickerHistory;
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

	    //init advertisement
	    final AdView adView = (AdView) findViewById(R.id.adView);
	    this.ad = new AdHelper(this, adView);

	    //create helper components
	    this.settings = SettingsHelper.getSharedPreferences(this);
	    this.history = new StickerHistory(settings);

	    //init sticker callback
	    this.stickerCallback = new StickerCallbackImpl(this, history, ad);

	    //init UI
	    initViewPager(settings);
	    initHistoryPage();

	    //first time alert
	    /*
	    if (settings.getBoolean("com.easy.emotionsticker.firstrun", true) ) {
		    new MyAlertDialog(this, R.string.alert_title, R.string.firstrun).show();
		    settings.edit().putBoolean("com.easy.emotionsticker.firstrun", false).commit();
	    }
	    */

    }

    @Override
    protected  void onStop() {
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
	    StickerFragmentListBuilder listBuilder = new StickerFragmentListBuilder(resourcesRepository);
	    List<? extends Fragment> fragmentList = listBuilder.build(contentPage, stickerCallback);

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
				viewPager.setCurrentItem(i+1);
			}
		});

		builder.buildHomeButton(findViewById(R.id.btn_home), viewPager);

		return fragment;
	}


	private void initHistoryPage() {
		final GridLayout grid = (GridLayout)findViewById(R.id.grid_history);
		final HistoryPageBuilder builder = new HistoryPageBuilder(this, resourcesRepository, history);
		builder.build(grid, stickerCallback);

		//set on history change callback
		history.setOnHistoryChange(new StickerHistory.OnHistoryChangeCallback() {
			@Override
			public void onHistoryChange(StickerHistory history) {
				builder.build(grid, stickerCallback);
			}
		});

		//set up the history clear button
		builder.buildClearButton(findViewById(R.id.btn_clear_history));

	}

}

