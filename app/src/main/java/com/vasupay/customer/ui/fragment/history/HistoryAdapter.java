package com.vasupay.customer.ui.fragment.history;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.vasupay.customer.R;
import com.vasupay.customer.ui.main.MainActivity;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    List<HistoryModel> historyModelList;
    Context context;

    public HistoryAdapter(List<HistoryModel> historyModelList, Context context) {
        this.historyModelList = historyModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int position) {
        HistoryModel historyModel = historyModelList.get(position);
        holder.txtAccountName.setText(historyModel.getHistory_acc_holder_name());
        holder.txtAccNo.setText(historyModel.getHistory_acc_no());
        holder.txtHistoryAmount.setText(historyModel.getHistory_amount());
        holder.txtHistoryDate.setText(historyModel.getHistory_date());
        holder.cardHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyIntent = new Intent(context, HistoryDetailsActivity.class);
                context.startActivity(historyIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyModelList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView txtAccountName, txtAccNo, txtHistoryDate, txtHistoryAmount;
        CardView cardHistory;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAccountName = itemView.findViewById(R.id.txtAccountName);
            txtAccNo = itemView.findViewById(R.id.txtAccNo);
            txtHistoryDate = itemView.findViewById(R.id.txtHistoryDate);
            txtHistoryAmount = itemView.findViewById(R.id.txtHistoryAmount);
            cardHistory = itemView.findViewById(R.id.cardHistory);
        }
    }
}
