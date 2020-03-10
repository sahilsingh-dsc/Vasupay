package com.vasupay.customer.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.vasupay.customer.R;
import com.vasupay.customer.ui.auth.MobileActivity;
import com.vasupay.customer.ui.fragment.account.AccountFragment;
import com.vasupay.customer.ui.fragment.history.HistoryFragment;
import com.vasupay.customer.ui.fragment.home.HomeFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navFooter;
    private TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        txtTitle = findViewById(R.id.txtTitle);
        navFooter = findViewById(R.id.navFooter);
        navFooter.setOnNavigationItemSelectedListener(MainActivity.this);
        navFooter.setSelectedItemId(R.id.footer_menu_home);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to Vasupay", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()){

            case R.id.footer_menu_home :
                fragment = new HomeFragment();
                txtTitle.setText("Home");
                break;

            case R.id.footer_menu_history :
                fragment = new HistoryFragment();
                txtTitle.setText("Transaction History");
                break;

            case R.id.footer_menu_account :
                fragment = new AccountFragment();
                txtTitle.setText("Your Account");
                break;

        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .commit();
            return true;
        }

        return false;
    }

    public void userLogout(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivity.this, MobileActivity.class));
        finish();
    }

}
