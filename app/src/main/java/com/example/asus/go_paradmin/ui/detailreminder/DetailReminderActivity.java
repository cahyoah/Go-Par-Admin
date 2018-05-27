package com.example.asus.go_paradmin.ui.detailreminder;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.asus.go_paradmin.R;
import com.example.asus.go_paradmin.data.model.Reminder;
import com.example.asus.go_paradmin.presenter.DetailReminderPresenter;
import com.example.asus.go_paradmin.ui.detailtips.DetailTipsActivity;
import com.example.asus.go_paradmin.util.ShowAlert;

public class DetailReminderActivity extends AppCompatActivity implements DetailReminderView {

    private EditText etReminderTitle, etReminderDescription, etReminderYes, etReminderNo, etReminderAge;
    private DetailReminderPresenter detailReminderPresenter;
    private int reminderId;
    private AlertDialog alert;
    private AlertDialog.Builder builder;
    private Toolbar toolbar;
    private MediaPlayer mediaPlayer;
    boolean onPlayState = false;
    private int playbackPosition=0;
    private Reminder reminder1;
//    boolean editTipsState = false;
//    private String tipsTitle;
//    private String tipsDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_reminder);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Detail Reminder");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        etReminderTitle = findViewById(R.id.et_reminder_title);
        etReminderDescription = findViewById(R.id.et_reminder_description);
        etReminderYes = findViewById(R.id.et_reminder_yes);
        etReminderNo = findViewById(R.id.et_reminder_no);
        etReminderAge = findViewById(R.id.et_reminder_age);
        reminderId = getIntent().getExtras().getInt("reminderId");
        initPresenter();

    }

    private void initPresenter() {
        detailReminderPresenter = new DetailReminderPresenter(this);
        if(ShowAlert.dialog != null && ShowAlert.dialog.isShowing()){
            ShowAlert.closeProgresDialog();
        }
        ShowAlert.showProgresDialog(this);
        detailReminderPresenter.showDetailReminder(reminderId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail_reminder, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.menu_pause_song:
                if(mediaPlayer != null && mediaPlayer.isPlaying()) {
                    playbackPosition = mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();
                }
                invalidateOptionsMenu();
                return true;
            case R.id.menu_play_song:
                if(mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(playbackPosition);
                    mediaPlayer.start();
                }
                invalidateOptionsMenu();
                return true;
            case R.id.menu_delete_reminder:

                alerDialogDelete();
                return  true;
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(reminder1 != null){
            if(reminder1.getReminderSong() != null){
                if(onPlayState){
                    menu.getItem(0).setVisible(false);
                    menu.getItem(1).setVisible(true);
                    onPlayState = false;
                }
                else{
                    menu.getItem(0).setVisible(true);
                    menu.getItem(1).setVisible(false);
                    onPlayState = true;
                }
            }else{
                menu.getItem(0).setVisible(false);
                menu.getItem(1).setVisible(false);
            }
        }


        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onSuccessShowDetailReminder(Reminder reminder) {
        ShowAlert.closeProgresDialog();
        etReminderTitle.setText(reminder.getReminderTitle());
        etReminderDescription.setText(reminder.getReminderDescription());
        etReminderYes.setText(reminder.getReminderYes());
        etReminderNo.setText(reminder.getReminderNo());
        etReminderAge.setText(reminder.getReminderAge());
        reminder1 = reminder;
        invalidateOptionsMenu();
        if(reminder.getReminderSong() != null){
            try {
                playAudio("http://aiondevelop.xyz/"+reminder.getReminderSong());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailureShowDetailReminder(String message) {
        ShowAlert.closeProgresDialog();
        alerDialogFailure();
    }

    @Override
    public void onFailureDeleteReminder(String message) {
        ShowAlert.closeProgresDialog();
        ShowAlert.showToast(this, message);
    }

    @Override
    public void onSuccessDeleteReminder() {
        ShowAlert.closeProgresDialog();
        ShowAlert.showToast(this, "Hapus reminder berhasil");
        super.onBackPressed();
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
                ShowAlert.showProgresDialog(DetailReminderActivity.this);
                detailReminderPresenter.showDetailReminder(reminderId);
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                DetailReminderActivity.super.onBackPressed();
            }
        });

        alert = builder.create();
        alert.show();

    }


    private void alerDialogDelete() {
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(getString(R.string.text_delete_reminder_confirmation));
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(ShowAlert.dialog != null && ShowAlert.dialog.isShowing()){
                    ShowAlert.closeProgresDialog();
                }
                ShowAlert.showProgresDialog(DetailReminderActivity.this);
                detailReminderPresenter.deleteReminder(reminderId);
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
    protected void onDestroy() {
        super.onDestroy();
        killMediaPlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer != null) {
            try {
                mediaPlayer.pause();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mediaPlayer != null) {
            try {
                mediaPlayer.start();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void killMediaPlayer() {
        if(mediaPlayer!=null) {
            try {
                mediaPlayer.release();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void playAudio(String url) throws Exception
    {
        killMediaPlayer();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(url);
        mediaPlayer.prepare();
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                killMediaPlayer();
                try {
                    playAudio(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }



}
