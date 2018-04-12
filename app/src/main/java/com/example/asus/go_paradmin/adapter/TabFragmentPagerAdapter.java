package com.example.asus.go_paradmin.adapter;



import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import com.example.asus.go_paradmin.data.model.Fraggment;

import java.util.ArrayList;
import java.util.List;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    /**
     * Contains all the fragments.
     */
    private List<Fraggment> fragments = new ArrayList<>();
    private Context context;
    /**
     * Creates a new PagerAdapter instance.
     *
     * @param fragmentManager The FragmentManager.
     */
    public TabFragmentPagerAdapter(Context context, FragmentManager fragmentManager ) {
        super(fragmentManager);
        this.context = context;

    }

    public TabFragmentPagerAdapter(FragmentManager fragmentManager, ArrayList<Fraggment> fraggmentArrayList) {
        super(fragmentManager);
        this.fragments = fraggmentArrayList;
    }

    public void setData(List<Fraggment> fraggments){
        this.fragments = fraggments;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position).getFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return null;
    }


}