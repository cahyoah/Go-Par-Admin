package com.example.asus.go_paradmin.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.asus.go_paradmin.R;
import com.example.asus.go_paradmin.adapter.TabFragmentPagerAdapter;
import com.example.asus.go_paradmin.data.model.Fraggment;
import com.example.asus.go_paradmin.presenter.HomePresenter;
import com.example.asus.go_paradmin.ui.login.LoginActivity;
import com.example.asus.go_paradmin.ui.settings.SettingsActivity;
import com.example.asus.go_paradmin.util.ShowAlert;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements HomeView {
    private ViewPager pager;
    private TabLayout tabs;
    private TabFragmentPagerAdapter adapter;
    private HomePresenter homePresenter;
    private AlertDialog alert;
    private AlertDialog.Builder builder;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));
        pager = findViewById(R.id.pager);
        tabs = findViewById(R.id.tabs);
        initView();

    }
    public void initView(){
        adapter = new TabFragmentPagerAdapter(this, getFragmentManager());
        pager.setAdapter(adapter);
        tabs.setTabTextColors(getResources().getColor(R.color.colorGrey300),
                getResources().getColor(R.color.colorWhite));
        tabs.setupWithViewPager(pager);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        homePresenter = new HomePresenter(this);
        homePresenter.showFragmentList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_exit:
                alertDialogExit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void alertDialogExit() {
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(getString(R.string.text_exit_confirmation));
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(ShowAlert.dialog != null && ShowAlert.dialog.isShowing()){
                    ShowAlert.closeProgresDialog();
                }
                ShowAlert.showProgresDialog(HomeActivity.this);
                homePresenter.logout();
            }
        });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert = builder.create();
        alert.show();

    }

    @Override
    public void showFragment(ArrayList<Fraggment> fraggmentArrayList) {
        adapter.setData(fraggmentArrayList);
        tabs.getTabAt(0).setText(fraggmentArrayList.get(0).getTitle());
        tabs.getTabAt(1).setText(fraggmentArrayList.get(1).getTitle());
    }

    @Override
    public void onSuccessLogout() {
        ShowAlert.closeProgresDialog();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailureLogout(String message) {
        ShowAlert.closeProgresDialog();
        ShowAlert.showToast(this, message);
    }
}
