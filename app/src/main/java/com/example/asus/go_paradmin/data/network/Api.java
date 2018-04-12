package com.example.asus.go_paradmin.data.network;

import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by adhit on 23/01/2018.
 */

public interface Api {
    @POST("user/login")
    @FormUrlEncoded
    Call<JsonObject> login(@Field("username") String username,
                           @Field("password") String password);

    @POST("user/logout")
    @FormUrlEncoded
    Call<JsonObject> logout(@Field("exit") String exit);

    @PUT("user/changepassword/{user}")
    @FormUrlEncoded
    Call<JsonObject> updatePassword(
            @Path("user") int userId,
            @Field("current_password") String currentPassword,
            @Field("new_password") String newPassword) ;

    @GET("tips/showalltips")
    Call<JsonObject> showAllTips();

    @POST("tips/post")
    @FormUrlEncoded
    Call<JsonObject> postTips(
                            @Field("user_id") int userId,
                            @Field("tips_title") String tipsTitle,
                            @Field("tips_description") String tipsDescription);

    @GET("tips/showdetailtips/{tipsId}")
    Call<JsonObject> showDetailTips(@Path("tipsId") int tipsId) ;

    @DELETE("tips/delete/{tipsId}")
    Call<JsonObject> deleteTips(@Path("tipsId") int tipsId) ;

    @PUT("tips/updatetips/{tips}")
    @FormUrlEncoded
    Call<JsonObject> updateTips(
            @Path("tips") int tipsId,
            @Field("user_id") int userId,
            @Field("tips_title") String tipsTitle,
            @Field("tips_description") String tipsDescription) ;

    @GET("reminder/showallreminder")
    Call<JsonObject> showAllReminder();

    @GET("reminder/showdetailreminder/{reminderId}")
    Call<JsonObject> showDetailReminder(@Path("reminderId") int reminderId) ;

    @DELETE("reminder/delete/{reminderId}")
    Call<JsonObject> deleteReminder(@Path("reminderId") int reminderId) ;

    @Multipart
    @POST("reminder/post")
    Call<JsonObject> postReminder(
            @Part("user_id") int userId,
            @Part("reminder_title") String reminderTitle,
            @Part("reminder_description") String reminderDescription,
            @Part("reminder_yes") String reminderYes,
            @Part("reminder_no") String reminderNo,
            @Part("reminder_song") RequestBody name,
            @Part MultipartBody.Part file);




}
