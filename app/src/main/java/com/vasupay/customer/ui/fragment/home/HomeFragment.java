package com.vasupay.customer.ui.fragment.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.vasupay.customer.R;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private View view;
    private RecyclerView recyclerBA;
    private List<BankAccountModel> bankAccountModelList;
    private BankAccountAdapter bankAccountAdapter;
    private FirebaseFirestore db;
    private LinearLayout lhBA;
    private FrameLayout frameHome;
    private AlertDialog spotsDialog;
    private MaterialButton btnProceed;
    private TextInputEditText etTransferAmount;
    private SharedPreferences preferencesBA;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        return view;
    }

    private void init() {
        recyclerBA = view.findViewById(R.id.recyclerBA);
        lhBA = view.findViewById(R.id.lhBA);
        frameHome = view.findViewById(R.id.frameHome);
        btnProceed = view.findViewById(R.id.btnProceed);
        etTransferAmount = view.findViewById(R.id.etTransferAmount);
        btnProceed.setOnClickListener(this);
        lhBA.setOnClickListener(this);
        db = FirebaseFirestore.getInstance();
        Context context;
        preferencesBA = getActivity().getSharedPreferences("BA",0 );
        loadingDialog();
    }

    private void loadingDialog() {
        spotsDialog = new SpotsDialog.Builder().setContext(getContext())
                .setTheme(R.style.Custom)
                .setMessage("Please Wait...")
                .build();
    }

    private void fetchBA(){
        recyclerBA.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        bankAccountModelList = new ArrayList<>();
        Query query = db.collection("user_bank_accounts");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult() != null){
                        bankAccountModelList.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            BankAccountModel bankAccountModel = new BankAccountModel();
                            bankAccountModel.setBa_id(snapshot.getString("ba_id"));
                            bankAccountModel.setBa_name(snapshot.getString("ba_name"));
                            bankAccountModel.setBa_number(snapshot.getString("ba_number"));
                            bankAccountModel.setBa_ifsc(snapshot.getString("ba_ifsc"));
                            bankAccountModelList.add(bankAccountModel);
                        }
                        bankAccountAdapter = new BankAccountAdapter(bankAccountModelList, getContext());
                        recyclerBA.setAdapter(bankAccountAdapter);
                        bankAccountAdapter.notifyDataSetChanged();
                        spotsDialog.dismiss();
                    }
                }else {
                    spotsDialog.dismiss();
                    Snackbar snackbar = Snackbar
                            .make(frameHome, "Bank Account(s) Not Found", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        spotsDialog.dismiss();
                        Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v == lhBA){
            startActivity(new Intent(getContext(), AddBAActivity.class));
        }
        if (v == btnProceed){
            uiValidation();
        }
    }

    private void uiValidation() {

        String transfer_amount = etTransferAmount.getText().toString();
        if (TextUtils.isEmpty(transfer_amount)){
            etTransferAmount.requestFocus();
            etTransferAmount.setError("Transfer Amount Required!");
            return;
        }
        if (!TextUtils.isDigitsOnly(transfer_amount)){
            etTransferAmount.requestFocus();
            etTransferAmount.setError("Transfer Amount Must Be Valid!");
            return;
        }

        String ba_name = preferencesBA.getString("ba_name", "name_null");
        String ba_number = preferencesBA.getString("ba_number", "number_null");
        String ba_ifsc = preferencesBA.getString("ba_ifsc", "ifsc_null");

        sendData(transfer_amount, ba_name, ba_number, ba_ifsc);
    }

    private void sendData(String transfer_amount, String ba_name, String ba_number, String ba_ifsc) {
        Intent transferIntent = new Intent(getContext(), TransferActivity.class);
        Bundle transferBundle = new Bundle();
        transferBundle.putString("transfer_amount", transfer_amount);
        transferBundle.putString("ba_name", ba_name);
        transferBundle.putString("ba_number", ba_number);
        transferBundle.putString("ba_ifsc", ba_ifsc);
        transferIntent.putExtras(transferBundle);
        startActivity(transferIntent);
    }

    @Override
    public void onStart() {
        super.onStart();
        spotsDialog.show();
        fetchBA();
    }

    @Override
    public void onResume() {
        super.onResume();
        spotsDialog.show();
        fetchBA();
    }
}
