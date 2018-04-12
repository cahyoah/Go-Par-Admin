package com.example.asus.go_paradmin.ui.tips;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.asus.go_paradmin.R;
import com.example.asus.go_paradmin.data.model.Tips;
import com.example.asus.go_paradmin.presenter.AddTipsPresenter;
import com.example.asus.go_paradmin.presenter.TipsPresenter;
import com.example.asus.go_paradmin.ui.home.HomeActivity;
import com.example.asus.go_paradmin.ui.home.tips.TipsView;
import com.example.asus.go_paradmin.ui.login.LoginActivity;
import com.example.asus.go_paradmin.util.ShowAlert;

import java.util.List;

public class AddTipsActivity extends AppCompatActivity implements AddTipsView {

    private EditText etTipsTitle, etTipsDescription;
    private AddTipsPresenter addTipsPresenter;
    private AlertDialog alert;
    private AlertDialog.Builder builder;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tips);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Post Tips");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        etTipsTitle = findViewById(R.id.et_tips_title);
        etTipsDescription = findViewById(R.id.et_tips_description);
        initPresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_posting_tips, menu);
        return true;
    }

    private void initPresenter(){
        addTipsPresenter = new AddTipsPresenter(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_posting_tips:
                String tipsTitle = etTipsTitle.getText().toString().trim();
                String tipsDescription = etTipsDescription.getText().toString().trim();
                if(tipsTitle.isEmpty()){
                    etTipsTitle.setError(getString(R.string.text_cannot_empty));
                    etTipsTitle.requestFocus();
                }else if(tipsDescription.isEmpty()){
                    etTipsDescription.setError(getString(R.string.text_cannot_empty));
                    etTipsDescription.requestFocus();
                }else{
                    if(ShowAlert.dialog != null && ShowAlert.dialog.isShowing()){
                        ShowAlert.closeProgresDialog();
                    }
                    ShowAlert.showProgresDialog(AddTipsActivity.this);
                    addTipsPresenter.postTips(tipsTitle, tipsDescription);
                }
                return true;

            case android.R.id.home:
                String tipsTitle1 = etTipsTitle.getText().toString().trim();
                String tipsDescription1 = etTipsDescription.getText().toString().trim();
                if(tipsTitle1.isEmpty() && tipsDescription1.isEmpty()){
                    super.onBackPressed();
                }else{
                    alertDialogCheck();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void alertDialogCheck() {
        builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.text_exit_add_confirmation));
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
              AddTipsActivity.super.onBackPressed();
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
    public void onFailureAddTips(String message) {
        ShowAlert.closeProgresDialog();
        ShowAlert.showToast(getApplicationContext() ,message);
    }

    @Override
    public void onSuccessAddTips() {
        ShowAlert.closeProgresDialog();
        ShowAlert.showToast(getApplicationContext(), "Penambahan Tips Berhasil");
        super.onBackPressed();
    }
}
