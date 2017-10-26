package com.aircontrol.demo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Silenoff on 2016/10/20.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;//Fragment容器

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
         super(fm);
         this.fragmentList = fragmentList;
    }
     @Override
     public Fragment getItem(int arg0) {
         return fragmentList.get(arg0);
     }
     @Override
     public int getCount() {
         return fragmentList.size();
     }

}
