package com.example.asus.go_paradmin.ui.home.reminder;

import com.example.asus.go_paradmin.data.model.Reminder;

import java.util.List;

public interface ReminderView {
    void onSuccessShowAllReminder(List<Reminder> reminderList);

    void onFailureShowAllReminder(String message);
}
