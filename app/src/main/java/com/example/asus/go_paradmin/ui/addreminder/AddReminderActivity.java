package com.example.asus.go_paradmin.ui.addreminder;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.go_paradmin.R;
import com.example.asus.go_paradmin.presenter.AddReminderPresenter;
import com.example.asus.go_paradmin.presenter.AddTipsPresenter;
import com.example.asus.go_paradmin.ui.tips.AddTipsActivity;
import com.example.asus.go_paradmin.util.ShowAlert;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddReminderActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, AddReminderView {
    private Uri uri;
    private static final String TAG = AddReminderActivity.class.getSimpleName();
    private Button btnSelectSong;
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private Toolbar toolbar;
    private MultipartBody.Part fileToUpload;
    private RequestBody filename;
    private AddReminderPresenter addReminderPresenter;
    private AlertDialog alert;
    private AlertDialog.Builder builder;
    private EditText etReminderTitle, etReminderDescription, etReminderYes, etReminderNo;
    private Spinner spnReminderAge;
    private TextView tvSongPath;
    private String reminderAgeSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Post Reminder");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        etReminderTitle = findViewById(R.id.et_reminder_title);
        etReminderDescription = findViewById(R.id.et_reminder_description);
        etReminderYes = findViewById(R.id.et_reminder_yes);
        etReminderNo = findViewById(R.id.et_reminder_no);
        spnReminderAge = findViewById(R.id.spn_reminder_age);
        tvSongPath = findViewById(R.id.tv_song_path);
        btnSelectSong = findViewById(R.id.btn_select_song);
        btnSelectSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent openAudioIntent = new Intent(Intent.ACTION_PICK);
//                openAudioIntent.setType("audio/*");
//                startActivityForResult(openAudioIntent, REQUEST_GALLERY_CODE);
//                Intent intent_upload = new Intent();
//                intent_upload.setType("audio/*");
//                intent_upload.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent_upload,1);
                Intent videoIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(videoIntent, "Select Audio"),1);
            }
        });
        initPresenter();
        initSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_posting_reminder, menu);
        return true;
    }

    private void initSpinner(){
        final List<String> list = new ArrayList<String>();
        list.add("< 1 Tahun");
        list.add("1 Tahun");
        list.add("2 Tahun");
        list.add("3 Tahun");
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnReminderAge.setAdapter(adp1);
        spnReminderAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                reminderAgeSelected = list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void initPresenter(){
        addReminderPresenter = new AddReminderPresenter(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_posting_reminder:
                String reminderTitle = etReminderTitle.getText().toString().trim();
                String reminderDescription = etReminderDescription.getText().toString().trim();
                String reminderYes = etReminderYes.getText().toString().trim();
                String reminderNo = etReminderNo.getText().toString().trim();

                if(reminderTitle.isEmpty()){
                    etReminderTitle.setError(getString(R.string.text_cannot_empty));
                    etReminderTitle.requestFocus();
                }else if(reminderDescription.isEmpty()){
                    etReminderDescription.setError(getString(R.string.text_cannot_empty));
                    etReminderDescription.requestFocus();
                }else if(reminderYes.isEmpty()){
                    etReminderYes.setError(getString(R.string.text_cannot_empty));
                    etReminderYes.requestFocus();
                }else if(reminderNo.isEmpty()){
                    etReminderNo.setError(getString(R.string.text_cannot_empty));
                    etReminderNo.requestFocus();
                }else if(reminderAgeSelected.isEmpty()){
                    ShowAlert.showToast(getApplicationContext(), "Anda belum memilih umur");
                }else{
                    if(ShowAlert.dialog != null && ShowAlert.dialog.isShowing()){
                        ShowAlert.closeProgresDialog();
                    }
                    ShowAlert.showProgresDialog(AddReminderActivity.this);
                    addReminderPresenter.postReminder(reminderTitle, reminderDescription, reminderYes, reminderNo, reminderAgeSelected, filename, fileToUpload);
                }
                return true;

            case android.R.id.home:
                String reminderTitle1 = etReminderTitle.getText().toString().trim();
                String reminderDescription1 = etReminderDescription.getText().toString().trim();
                String reminderYes1 = etReminderYes.getText().toString().trim();
                String reminderNo1 = etReminderNo.getText().toString().trim();
                if(reminderTitle1.isEmpty() && reminderDescription1.isEmpty() && reminderNo1.isEmpty() && reminderYes1.isEmpty() && reminderAgeSelected.isEmpty()){
                    super.onBackPressed();
                }else{
                    alertDialogCheck();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                uri = data.getData();
                if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    String filePath = getAudioPath(uri);
                    Log.d(TAG, "Filename " + filePath);
                    File file = new File(filePath);
                    RequestBody mFile = RequestBody.create(MediaType.parse("audio/*"), file);
                    fileToUpload = MultipartBody.Part.createFormData("reminder_song", file.getName(), mFile);
                    filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                    tvSongPath.setText(file.getName());
                }else{
                    EasyPermissions.requestPermissions(this, getString(R.string.text_read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
                }
            }
        }
    }

    private String getAudioPath(Uri uri) {
        String[] data = {MediaStore.Audio.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, data, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, AddReminderActivity.this);
    }



    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(uri != null){

            String filePath = getAudioPath(uri);
            File file = new File(filePath);
            RequestBody mFile = RequestBody.create(MediaType.parse("audio/*"), file);
            fileToUpload = MultipartBody.Part.createFormData("reminder_song", file.getName(), mFile);
            filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
            tvSongPath.setText(file.getName());
        }
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");
    }

    private void alertDialogCheck() {
        builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.text_exit_add_confirmation));
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                AddReminderActivity.super.onBackPressed();
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
    public void onSuccessAddReminder() {
        ShowAlert.closeProgresDialog();
        ShowAlert.showToast(getApplicationContext(), "Penambahan Reminder Berhasil");
        super.onBackPressed();
    }

    @Override
    public void onFailureAddReminder(String message) {
        ShowAlert.closeProgresDialog();
        ShowAlert.showToast(getApplicationContext() ,message);
    }
}
