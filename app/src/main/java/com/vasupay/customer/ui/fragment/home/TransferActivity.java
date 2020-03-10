package com.vasupay.customer.ui.fragment.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vasupay.customer.R;

public class TransferActivity extends AppCompatActivity {


    private ImageView imgGoBack;
    private Bundle bundle;
    private TextView txtAccountName, txtAccountNumberAndIfsc, txtTransferAmount, txtTransferFee, txtTotalPayable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        init();
    }

    private void init() {
        bundle = getIntent().getExtras();
        imgGoBack = findViewById(R.id.imgGoBack);
        txtAccountName = findViewById(R.id.txtAccountName);
        txtAccountNumberAndIfsc = findViewById(R.id.txtAccountNumberAndIfsc);
        txtTransferAmount = findViewById(R.id.txtTransferAmount);
        txtTransferFee = findViewById(R.id.txtTransferFee);
        txtTotalPayable = findViewById(R.id.txtTotalPayable);
        imgGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        setData();
    }

    private void setData() {
        String transfer_amount = bundle.getString("transfer_amount");
        String ba_name = bundle.getString("ba_name");
        String ba_number = bundle.getString("ba_number");
        String ba_ifsc = bundle.getString("ba_ifsc");
        txtAccountName.setText(ba_name);
        txtAccountNumberAndIfsc.setText(ba_number+"@"+ba_ifsc);
        doCalculate(transfer_amount);
    }

    private void doCalculate(String transfer_amount) {
        double transfer_amt = Double.parseDouble(transfer_amount);
        double fee_percent = 4.0;
        double fee_amount =  (transfer_amt * fee_percent)/100;
        double total_payable = transfer_amt+fee_amount;
        txtTransferAmount.setText("₹ "+transfer_amount);
        txtTransferFee.setText("₹ "+fee_amount);
        txtTotalPayable.setText("₹ "+total_payable);
    }

}
