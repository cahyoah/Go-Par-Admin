package com.example.asus.go_paradmin.ui.detailreminder;

import com.example.asus.go_paradmin.data.model.Reminder;

public interface DetailReminderView {
    void onSuccessShowDetailReminder(Reminder tips);

    void onFailureShowDetailReminder(String message);

    void onFailureDeleteReminder(String message);

    void onSuccessDeleteReminder();
}
