package com.example.asus.go_paradmin.ui.settings;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
;import com.example.asus.go_paradmin.R;
import com.example.asus.go_paradmin.data.model.User;
import com.example.asus.go_paradmin.presenter.SettingsPresenter;
import com.example.asus.go_paradmin.util.ShowAlert;

import java.util.ArrayList;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity implements SettingsView {

    private TextView tvName;
    private ListView lvSettingsMenu;
    private SettingsPresenter settingsPresenter;
    private Toolbar toolbar;
    private AlertDialog.Builder dialogBuilder;
    private LayoutInflater inflater;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Pengaturan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tvName = findViewById(R.id.tv_name);
        lvSettingsMenu = findViewById(R.id.lv_settings_menu);
        initView();
        initPresenter();
    }

    private void initView(){
        ArrayList<String> menuList = new ArrayList<>();
        menuList.add("Ganti Password");
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuList);
        lvSettingsMenu.setAdapter(itemsAdapter);
        lvSettingsMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    alertDialogChangePassword();
                }
            }
        });
    }

    public void initPresenter(){
        settingsPresenter = new SettingsPresenter(this);
        settingsPresenter.getUserData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSuccessUpdatePassword() {
        ShowAlert.closeProgresDialog();
        alertDialog.dismiss();
        ShowAlert.showToast(SettingsActivity.this, getString(R.string.text_edit_password_confirmation));
    }

    @Override
    public void onFailedShowUserData() {

    }

    @Override
    public void onSuccessShowUserData(User user) {
        tvName.setText(user.getName());
    }

    @Override
    public void onFailedUpdatePassword(String message) {
        ShowAlert.closeProgresDialog();
        ShowAlert.showToast(SettingsActivity.this, message);
    }

    public void alertDialogChangePassword(){
        dialogBuilder = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_change_password, null);
        dialogBuilder.setView(dialogView);

        EditText etPassword = (EditText) dialogView.findViewById(R.id.et_current_password);
        EditText etNewPassword = (EditText) dialogView.findViewById(R.id.et_new_password);
        EditText etNewPassowrdConfirm = dialogView.findViewById(R.id.et_password_new_confirm);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        Button btnSave = dialogView.findViewById(R.id.btn_save);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString().trim();
                String passwordNew = etNewPassword.getText().toString().trim();
                String passwordConfirm = etNewPassowrdConfirm.getText().toString().trim();
                if(password.isEmpty()){
                    etPassword.setError(getString(R.string.text_cannot_empty));
                    etPassword.requestFocus();
                }else if(passwordNew.isEmpty()){
                    etNewPassword.setError(getString(R.string.text_cannot_empty));
                    etNewPassword.requestFocus();
                }else if(passwordConfirm.isEmpty()){
                    etNewPassowrdConfirm.setError(getString(R.string.text_cannot_empty));
                    etNewPassowrdConfirm.requestFocus();
                }else if(!passwordNew.equals(passwordConfirm)){
                    ShowAlert.showToast(SettingsActivity.this, "Konfirmasi password tidak sama");
                }else if(passwordNew.length() <6){
                    ShowAlert.showToast(SettingsActivity.this, "Panjang password minimal 6");
                }else{
                    if(ShowAlert.dialog != null && ShowAlert.dialog.isShowing()){
                        ShowAlert.closeProgresDialog();
                    }
                    ShowAlert.showProgresDialog(SettingsActivity.this);
                    settingsPresenter.updatePassword(password, passwordNew);
                }

            }
        });


        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}
