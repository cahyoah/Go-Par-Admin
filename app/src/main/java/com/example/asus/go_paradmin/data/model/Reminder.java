package com.example.asus.go_paradmin.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reminder {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("reminder_title")
    @Expose
    private String reminderTitle;
    @SerializedName("reminder_description")
    @Expose
    private String reminderDescription;
    @SerializedName("reminder_yes")
    @Expose
    private String reminderYes;
    @SerializedName("reminder_no")
    @Expose
    private String reminderNo;
    @SerializedName("reminder_song")
    @Expose
    private String reminderSong;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("user")
    @Expose
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getReminderTitle() {
        return reminderTitle;
    }

    public void setReminderTitle(String reminderTitle) {
        this.reminderTitle = reminderTitle;
    }

    public String getReminderDescription() {
        return reminderDescription;
    }

    public void setReminderDescription(String reminderDescription) {
        this.reminderDescription = reminderDescription;
    }

    public String getReminderYes() {
        return reminderYes;
    }

    public void setReminderYes(String reminderYes) {
        this.reminderYes = reminderYes;
    }

    public String getReminderNo() {
        return reminderNo;
    }

    public void setReminderNo(String reminderNo) {
        this.reminderNo = reminderNo;
    }

    public String getReminderSong() {
        return reminderSong;
    }

    public void setReminderSong(String reminderSong) {
        this.reminderSong = reminderSong;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
