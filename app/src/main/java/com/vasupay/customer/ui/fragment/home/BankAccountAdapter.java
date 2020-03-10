package com.vasupay.customer.ui.fragment.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vasupay.customer.R;

import java.util.List;

public class BankAccountAdapter extends RecyclerView.Adapter<BankAccountAdapter.BAViewHolder> {

    List<BankAccountModel> bankAccountModelList;
    Context context;
    int row_index;
    SharedPreferences preferencesBA;

    public BankAccountAdapter(List<BankAccountModel> bankAccountModelList, Context context) {
        this.bankAccountModelList = bankAccountModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public BankAccountAdapter.BAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_account_list_item, parent, false);
        preferencesBA = context.getSharedPreferences("BA", 0);
        return new BAViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BankAccountAdapter.BAViewHolder holder, final int position) {
        BankAccountModel bankAccountModel = bankAccountModelList.get(position);
        holder.txtAccountName.setText(bankAccountModel.getBa_name());
        holder.txtAccountNumber.setText(bankAccountModel.getBa_number());
        holder.lhBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position;
                notifyDataSetChanged();
            }
        });

        if (row_index == position) {
            holder.imgSelectedCheck.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check_circle_black_24dp));
            Editor editor = preferencesBA.edit();
            editor.putString("ba_id", bankAccountModel.getBa_id());
            editor.putString("ba_name", bankAccountModel.getBa_name());
            editor.putString("ba_number", bankAccountModel.getBa_number());
            editor.putString("ba_ifsc", bankAccountModel.getBa_ifsc());
            editor.apply();
        } else {
            holder.imgSelectedCheck.setImageDrawable(context.getResources().getDrawable(R.drawable.bank));
        }

    }

    @Override
    public int getItemCount() {
        return bankAccountModelList.size();
    }

    public class BAViewHolder extends RecyclerView.ViewHolder {

        ImageView imgSelectedCheck;
        private TextView txtAccountName, txtAccountNumber;
        private LinearLayout lhBA;

        public BAViewHolder(@NonNull View itemView) {
            super(itemView);

            txtAccountName = itemView.findViewById(R.id.txtAccountName);
            txtAccountNumber = itemView.findViewById(R.id.txtAccountNumber);
            lhBA = itemView.findViewById(R.id.lhBA);
            imgSelectedCheck = itemView.findViewById(R.id.imgSelectedCheck);

        }
    }
}
