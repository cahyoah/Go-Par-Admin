package com.example.asus.go_paradmin.ui.settings;

import com.example.asus.go_paradmin.data.model.User;

public interface SettingsView {

    void onSuccessUpdatePassword();

    void onFailedShowUserData();

    void onSuccessShowUserData(User user);

    void onFailedUpdatePassword(String message);
}
