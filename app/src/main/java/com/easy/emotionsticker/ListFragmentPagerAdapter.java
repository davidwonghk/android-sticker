package com.easy.emotionsticker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * The <code>StickerPagerAdapter</code> serves the fragments when paging.
 * @author mwho
 */
class ListFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<? extends Fragment> fragments;
    /**
     * @param fm
     * @param fragments
     */
    public ListFragmentPagerAdapter(FragmentManager fm, List<? extends Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }
    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
     */
    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    /* (non-Javadoc)
     * @see android.support.v4.view.StickerPagerAdapter#getCount()
     */
    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @Override
    public String getPageTitle(int position) {
        return fragments.get(position).toString();
    }

}