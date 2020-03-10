package com.vasupay.customer.ui.fragment.history;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vasupay.customer.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private List<HistoryModel> historyModelList;
    private HistoryAdapter historyAdapter;
    private RecyclerView recyclerHistory;
    private View view;

    public HistoryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);
        init();
        return view;
    }

    private void init() {
        recyclerHistory = view.findViewById(R.id.recyclerHistory);
        recyclerHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        historyModelList = new ArrayList<>();
        HistoryModel historyModel = new HistoryModel();
        historyModel.setHistory_acc_holder_name("Sahil Singh");
        historyModel.setHistory_acc_no("5048101000447");
        historyModel.setHistory_amount("- ₹500,000");
        historyModel.setHistory_date("Feb 29, 2020");
        historyModelList.add(historyModel);
        historyAdapter = new HistoryAdapter(historyModelList, getContext());
        recyclerHistory.setAdapter(historyAdapter);
    }
}
