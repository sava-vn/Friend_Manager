package com.sava.friend_manager.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sava.friend_manager.fragment.BlackFragment;
import com.sava.friend_manager.fragment.FavoriteFragment;
import com.sava.friend_manager.fragment.NormalFragment;

public class TabLayoutAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private String[] titles  = {"FAVORITE","NORMAL","BLACK"};
    public TabLayoutAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0: fragment = new FavoriteFragment();break;
            case 1: fragment = new NormalFragment();break;
            case 2: fragment = new BlackFragment();break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
