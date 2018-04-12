package com.example.asus.go_paradmin.ui.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.asus.go_paradmin.R;
import com.example.asus.go_paradmin.data.model.User;
import com.example.asus.go_paradmin.presenter.LoginPresenter;
import com.example.asus.go_paradmin.ui.home.HomeActivity;
import com.example.asus.go_paradmin.util.ShowAlert;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginView {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        initPresenter();
    }

    private void initPresenter() {
        loginPresenter = new LoginPresenter(this);
        loginPresenter.checkLogin();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_login){
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if(username.isEmpty()){
                etUsername.setError(getString(R.string.text_cannot_empty));
                etUsername.requestFocus();
            }else if(password.isEmpty()){
                etPassword.setError(getString(R.string.text_cannot_empty));
                etPassword.requestFocus();
            }else {
                if(ShowAlert.dialog != null && ShowAlert.dialog.isShowing()){
                    ShowAlert.closeProgresDialog();
                }
                ShowAlert.showProgresDialog(LoginActivity.this);
                loginPresenter.login(username, password);
            }
        }
    }

    @Override
    public void onFailureLogin(String message) {
        ShowAlert.closeProgresDialog();
        ShowAlert.showToast(getApplicationContext(), message);
    }

    @Override
    public void onSuccessLogin(User user) {
        ShowAlert.closeProgresDialog();
        ShowAlert.showToast(getApplicationContext(), "Selamat datang "+ user.getName());
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onCheckLogin(boolean loginStatus) {
        if(loginStatus){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
