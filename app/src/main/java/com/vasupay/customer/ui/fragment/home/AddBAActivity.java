package com.vasupay.customer.ui.fragment.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vasupay.customer.R;
import com.vasupay.customer.ui.auth.OtpActivity;

import dmax.dialog.SpotsDialog;

public class AddBAActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgGoBack;
    private TextInputEditText etBAName, etBANumber, etBAReNumber, etBAIfsc;
    private MaterialButton btnAddBA;
    private FirebaseFirestore db;
    private AlertDialog spotsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_b_a);
        init();
    }

    private void init() {
        imgGoBack = findViewById(R.id.imgGoBack);
        etBAName = findViewById(R.id.etBAName);
        etBANumber = findViewById(R.id.etBANumber);
        etBAReNumber = findViewById(R.id.etBAReNumber);
        etBAIfsc = findViewById(R.id.etBAIfsc);
        btnAddBA = findViewById(R.id.btnAddBA);
        btnAddBA.setOnClickListener(this);
        db = FirebaseFirestore.getInstance();

        loadingDialog();

        imgGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    private void loadingDialog() {
        spotsDialog = new SpotsDialog.Builder().setContext(AddBAActivity.this)
                .setTheme(R.style.Custom)
                .setMessage("Please Wait...")
                .build();
    }

    @Override
    public void onClick(View v) {
        uiValidation();
    }

    private void uiValidation() {
        String ba_name = etBAName.getText().toString();
        String ba_number = etBANumber.getText().toString();
        String ba_re_number = etBAReNumber.getText().toString();
        String ba_ifsc = etBAIfsc.getText().toString();
        if (TextUtils.isEmpty(ba_name)){
            etBAName.requestFocus();
            etBAName.setError("Name On Account Required.");
            return;
        }
        if (TextUtils.isEmpty(ba_number)){
            etBANumber.requestFocus();
            etBANumber.setError("Account Number Required.");
            return;
        }
        if (TextUtils.isEmpty(ba_re_number)){
            etBAReNumber.requestFocus();
            etBAReNumber.setError("Repeat Account Number Required.");
            return;
        }
        if (TextUtils.isEmpty(ba_ifsc)){
            etBAIfsc.requestFocus();
            etBAIfsc.setError("IFSC Code Required.");
            return;
        }
        if (!ba_number.equals(ba_re_number)){
            etBAReNumber.requestFocus();
            etBAReNumber.setError("Account Number Did Not Match.");
            return;
        }
        spotsDialog.show();
        putBA(ba_name, ba_number, ba_ifsc);
    }

    private void putBA(String ba_name, String ba_number, String ba_ifsc) {
        CollectionReference collRef = db.collection("user_bank_accounts");
        DocumentReference docRef = collRef.document();
        String id = docRef.getId();
        BankAccountModel bankAccountModel = new BankAccountModel();
        bankAccountModel.setBa_id(id);
        bankAccountModel.setBa_name(ba_name);
        bankAccountModel.setBa_number(ba_number);
        bankAccountModel.setBa_ifsc(ba_ifsc);
        collRef.document(id)
                .set(bankAccountModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            spotsDialog.dismiss();
                            Toast.makeText(AddBAActivity.this, "Bank Account Added!", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            finish();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        spotsDialog.dismiss();
                        Toast.makeText(AddBAActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
