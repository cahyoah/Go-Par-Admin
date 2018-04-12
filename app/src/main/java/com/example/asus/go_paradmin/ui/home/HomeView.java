package com.example.asus.go_paradmin.ui.home;

import com.example.asus.go_paradmin.data.model.Fraggment;

import java.util.ArrayList;

public interface HomeView {

    void showFragment(ArrayList<Fraggment> fraggmentArrayList);

    void onSuccessLogout();

    void onFailureLogout(String message);
}
