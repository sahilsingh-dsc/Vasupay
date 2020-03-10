package com.vasupay.customer.ui.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vasupay.customer.R;
import com.vasupay.customer.ui.auth.OtpActivity;

import dmax.dialog.SpotsDialog;

public class TermsAndConditionActivity extends AppCompatActivity {

    private TextView txtTAndCDetails;
    private FirebaseFirestore db;
    private AlertDialog spotsDialog;
    ImageView imgGoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);
        init();
    }

    private void init() {
        loadingDialog();
        txtTAndCDetails = findViewById(R.id.txtTAndCDetails);
        imgGoBack = findViewById(R.id.imgGoBack);
        imgGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        db = FirebaseFirestore.getInstance();
        spotsDialog.show();
        db.collection("system_constant")
                .document("t_and_c")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            if (task.getResult() != null) {
                                String t_and_c = task.getResult().getString("details");
                                txtTAndCDetails.setText(Html.fromHtml(t_and_c));
                                spotsDialog.dismiss();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        spotsDialog.dismiss();
                        Toast.makeText(TermsAndConditionActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadingDialog() {
        spotsDialog = new SpotsDialog.Builder().setContext(TermsAndConditionActivity.this)
                .setTheme(R.style.Custom)
                .setMessage("Please Wait...")
                .build();
    }

}
