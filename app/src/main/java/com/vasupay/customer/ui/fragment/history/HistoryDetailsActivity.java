package com.vasupay.customer.ui.fragment.history;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.vasupay.customer.R;

public class HistoryDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgGoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        init();
    }

    private void init() {
        imgGoBack = findViewById(R.id.imgGoBack);
        imgGoBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == imgGoBack){
            onBackPressed();
            finish();
        }
    }

}
