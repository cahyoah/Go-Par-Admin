package com.example.asus.go_paradmin.data.local;

import com.example.asus.go_paradmin.util.Constant;
import com.example.asus.go_paradmin.util.SharedPrefUtil;

/**
 * Created by adhit on 24/01/2018.
 */

public class SessionLogin {
    private static SessionLogin ourInstance;

    private SessionLogin() {
    }

    public static SessionLogin getInstance() {
        if (ourInstance == null) ourInstance = new SessionLogin();
        return ourInstance;
    }

   public void setLoginStatus(boolean loginStatus){
        SharedPrefUtil.saveBoolean(Constant.LOGIN_STATUS, loginStatus);
   }

   public boolean getLoginStatus(){
       return SharedPrefUtil.getBoolean(Constant.LOGIN_STATUS);
   }
}
