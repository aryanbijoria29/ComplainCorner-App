package com.celes.complaincorner;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class stayLoggedIn extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

        if(mUser!=null && mUser.isEmailVerified()){
            Intent intent=new Intent(this, studentMain.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
