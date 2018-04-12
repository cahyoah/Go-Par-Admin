package com.example.asus.go_paradmin.data.model;

import android.app.Fragment;

/**
 * Created by adhit on 04/01/2018.
 */

public class Fraggment {

    private Fragment fragment;
    private String title;

    public Fraggment(Fragment fragment, String title) {
        this.fragment = fragment;
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public String getTitle() {
        return title;
    }

}
