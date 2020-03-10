package com.vasupay.customer.ui.auth;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vasupay.customer.R;
import com.vasupay.customer.ui.common.TermsAndConditionActivity;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class MobileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText etMobileNo;
    private MaterialButton btnGenerateOTP;
    private TextView txtTAndC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);
        init();
    }

    private void init() {
        etMobileNo = findViewById(R.id.etMobileNo);
        btnGenerateOTP = findViewById(R.id.btnGenerateOTP);
        txtTAndC = findViewById(R.id.txtTAndC);
        btnGenerateOTP.setOnClickListener(this);
        txtTAndC.setOnClickListener(this);
    }

    private void uiValidation() {
        String mobile_number = Objects.requireNonNull(etMobileNo.getText()).toString();
        if (TextUtils.isEmpty(mobile_number)) {
            etMobileNo.requestFocus();
            etMobileNo.setError("Mobile number required");
            return;
        }
        if (!TextUtils.isDigitsOnly(mobile_number)) {
            etMobileNo.requestFocus();
            etMobileNo.setError("Mobile number must be valid");
            return;
        }
        if (mobile_number.length() < 10) {
            etMobileNo.requestFocus();
            etMobileNo.setError("Mobile number must be of 10 digits");
            return;
        }

        sendOtp();

    }

    @Override
    public void onClick(View v) {
        if (v == btnGenerateOTP) {
           uiValidation();
        }
        if (v == txtTAndC){
            startActivity(new Intent(MobileActivity.this, TermsAndConditionActivity.class));
        }
    }

    private void sendOtp() {
        String mobile_number = Objects.requireNonNull(etMobileNo.getText()).toString();
        Intent otpItent = new Intent(MobileActivity.this, OtpActivity.class);
        Bundle otpBundle = new Bundle();
        otpBundle.putString("mobile_number", mobile_number);
        otpItent.putExtras(otpBundle);
        startActivity(otpItent);
    }
}
