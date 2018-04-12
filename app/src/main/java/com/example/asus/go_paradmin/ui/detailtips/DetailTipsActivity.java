package com.example.asus.go_paradmin.ui.detailtips;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.asus.go_paradmin.R;
import com.example.asus.go_paradmin.data.model.Tips;
import com.example.asus.go_paradmin.presenter.DetailTipsPresenter;
import com.example.asus.go_paradmin.util.ShowAlert;

public class DetailTipsActivity extends AppCompatActivity implements DetailTipsView, View.OnClickListener {

    private EditText etTipsTitle, etTipsDescription;
    private DetailTipsPresenter detailTipsPresenter;
    private int tipsId;
    private AlertDialog alert;
    private AlertDialog.Builder builder;
    private Toolbar toolbar;
    boolean editTipsState = false;
    private String tipsTitle;
    private String tipsDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tips);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Detail Tips");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        etTipsTitle = findViewById(R.id.et_tips_title);
        etTipsDescription = findViewById(R.id.et_tips_description);
        tipsId = getIntent().getExtras().getInt("tipsId");
        initPresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail_tips, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_delete_tips:
                alerDialogDelete();
                return true;
            case R.id.menu_edit_tips:
                invalidateOptionsMenu();
                etTipsTitle.setEnabled(true);
                etTipsDescription.setEnabled(true);
                tipsTitle = etTipsTitle.getText().toString().trim();
                tipsDescription = etTipsDescription.getText().toString().trim();
                return true;
            case R.id.menu_cancel_edit_tips:
                invalidateOptionsMenu();
                etTipsTitle.setEnabled(false);
                etTipsDescription.setEnabled(false);
                etTipsTitle.setText(tipsTitle);
                etTipsDescription.setText(tipsDescription);
                return true;
            case R.id.menu_accept_edit_tips:
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
                    ShowAlert.showProgresDialog(this);
                    detailTipsPresenter.updateTips(tipsId, tipsTitle, tipsDescription);
                }
                return true;
            case android.R.id.home:
                if(!editTipsState){
                    alertDialogCheck();
                }else{
                    super.onBackPressed();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
       if(editTipsState){
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
            menu.getItem(2).setVisible(true);
            menu.getItem(3).setVisible(true);
            editTipsState = false;
        }
        else{
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(true);
            menu.getItem(2).setVisible(false);
            menu.getItem(3).setVisible(false);
            editTipsState = true;
        }

        return super.onPrepareOptionsMenu(menu);
    }

    private void initPresenter() {
        detailTipsPresenter = new DetailTipsPresenter(this);
        if(ShowAlert.dialog != null && ShowAlert.dialog.isShowing()){
            ShowAlert.closeProgresDialog();
        }
        ShowAlert.showProgresDialog(DetailTipsActivity.this);
        detailTipsPresenter.showDetailTips(tipsId);
    }

    @Override
    public void onSuccessShowDetailTips(Tips tips) {
        ShowAlert.closeProgresDialog();
        etTipsTitle.setText(tips.getTipsTitle());
        etTipsDescription.setText(tips.getTipsDescription());
    }

    @Override
    public void onFailureShowDetailTips(String message) {
        ShowAlert.closeProgresDialog();
        alerDialogFailure();
    }

    @Override
    public void onFailureDeleteTips(String message) {
        ShowAlert.closeProgresDialog();
        ShowAlert.showToast(this, message);
    }

    @Override
    public void onSuccessDeleteTips() {
        ShowAlert.closeProgresDialog();
        ShowAlert.showToast(this, "Hapus tips berhasil");
        super.onBackPressed();
    }

    @Override
    public void onFailureUpdateTips(String message) {
        ShowAlert.closeProgresDialog();
        ShowAlert.showToast(this, message);
    }

    @Override
    public void onSuccessUpdateTips(Tips tips) {
        invalidateOptionsMenu();
        etTipsTitle.setEnabled(false);
        etTipsDescription.setEnabled(false);
        etTipsTitle.setText(tips.getTipsTitle());
        etTipsDescription.setText(tips.getTipsDescription());
        ShowAlert.closeProgresDialog();
        ShowAlert.showToast(this, "Pembaruan tips berhasil");
    }


    @Override
    public void onClick(View v) {

    }

    private void alerDialogFailure() {
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(getString(R.string.text_failed_get_data));
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(ShowAlert.dialog != null && ShowAlert.dialog.isShowing()){
                    ShowAlert.closeProgresDialog();
                }
                ShowAlert.showProgresDialog(DetailTipsActivity.this);
                detailTipsPresenter.showDetailTips(tipsId);
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                DetailTipsActivity.super.onBackPressed();
            }
        });

        alert = builder.create();
        alert.show();

    }

    private void alerDialogDelete() {
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(getString(R.string.text_delete_tips_confirmation));
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(ShowAlert.dialog != null && ShowAlert.dialog.isShowing()){
                    ShowAlert.closeProgresDialog();
                }
                ShowAlert.showProgresDialog(DetailTipsActivity.this);
                detailTipsPresenter.deleteTips(tipsId);
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
    private void alertDialogCheck() {
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(getString(R.string.text_exit_detail_confirmation));
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                DetailTipsActivity.super.onBackPressed();
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
}
