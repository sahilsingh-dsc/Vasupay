package com.vasupay.customer.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.vasupay.customer.R;
import com.vasupay.customer.ui.auth.MobileActivity;
import com.vasupay.customer.ui.main.MainActivity;

import static com.vasupay.customer.util.Constant.SPLASH_DELAY;

public class SplashActivity extends AppCompatActivity {

    private Intent mobileIntent, mainIntent;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        mobileIntent = new Intent(SplashActivity.this, MobileActivity.class);
        mainIntent = new Intent(SplashActivity.this, MainActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (firebaseAuth.getCurrentUser() != null){
                    startActivity(mainIntent);
                    finish();
                }else {
                    startActivity(mobileIntent);
                    finish();
                }
            }
        }, SPLASH_DELAY);
    }
}
