package com.vasupay.customer.ui.auth;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.vasupay.customer.R;
import com.vasupay.customer.ui.common.TermsAndConditionActivity;
import com.vasupay.customer.ui.main.MainActivity;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;

public class OtpActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth firebaseAuth;
    private AlertDialog spotsDialog;
    private TextInputEditText etOtpCode;
    private MaterialButton btnVerifyOTP;
    private ImageView imgGoBack;
    private String mobile_number;
    private Bundle bundle;
    private TextView txtSentCodeNumber, txtTAndC;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private LinearLayout linearOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        init();
    }

    private void init() {
        etOtpCode = findViewById(R.id.etOtpCode);
        btnVerifyOTP = findViewById(R.id.btnVerifyOTP);
        imgGoBack = findViewById(R.id.imgGoBack);
        txtSentCodeNumber = findViewById(R.id.txtSentCodeNumber);
        linearOtp = findViewById(R.id.linearOtp);
        txtTAndC = findViewById(R.id.txtTAndC);
        txtTAndC.setOnClickListener(this);

        bundle = getIntent().getExtras();
        mobile_number = bundle.getString("mobile_number");
        txtSentCodeNumber.setText("Enter the OTP sent to +91" + mobile_number);

        firebaseAuth = FirebaseAuth.getInstance();

        loadingDialog();

        btnVerifyOTP.setOnClickListener(this);
        imgGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                spotsDialog.show();
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                //Toast.makeText(OtpActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Snackbar snackbar = Snackbar
                            .make(linearOtp, "Enter valid mobile number", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar snackbar = Snackbar
                            .make(linearOtp, "Quota Exceeded, Try after some time", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
        startPhoneNumberVerification(mobile_number);
    }

    private void loadingDialog() {
        spotsDialog = new SpotsDialog.Builder().setContext(OtpActivity.this)
                .setTheme(R.style.Custom)
                .setMessage("Verifying OTP...")
                .build();
    }


    @Override
    public void onClick(View v) {
        if (v == btnVerifyOTP) {
            uiValidation();
        }
        if (v == txtTAndC){
            startActivity(new Intent (OtpActivity.this, TermsAndConditionActivity.class));
        }
    }

    private void uiValidation() {
        String otp_code = Objects.requireNonNull(etOtpCode.getText()).toString();
        if (TextUtils.isEmpty(otp_code)) {
            etOtpCode.requestFocus();
            etOtpCode.setError("OTP Code required");
            return;
        }
        if (!TextUtils.isDigitsOnly(otp_code)) {
            etOtpCode.requestFocus();
            etOtpCode.setError("OTP Code must be valid");
            return;
        }
        if (otp_code.length() < 6) {
            etOtpCode.requestFocus();
            etOtpCode.setError("OTP Code must be of 6 digits");
            return;
        }
        verifyCode(otp_code);
    }

    private void verifyCode(String otp_code) {
        spotsDialog.show();
        verifyPhoneNumberWithCode(mVerificationId, otp_code);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            spotsDialog.dismiss();
                            Snackbar snackbar = Snackbar
                                    .make(linearOtp, "Success", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            startActivity(new Intent (OtpActivity.this, MainActivity.class));
                        } else {
                            spotsDialog.dismiss();
                            //Toast.makeText(OtpActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Snackbar snackbar = Snackbar
                                        .make(linearOtp, "Invalid Code", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }

                        }
                    }
                });
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        Snackbar snackbar = Snackbar
                .make(linearOtp, "OTP Sent", Snackbar.LENGTH_LONG);
        snackbar.show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
        Snackbar snackbar = Snackbar
                .make(linearOtp, "OTP Resent", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}
