package com.accubits.trafficticketing.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.accubits.trafficticketing.Model.History;
import com.accubits.trafficticketing.R;

import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryViewHolder> {
    private List<History> historyList;
    private int historyListLayout;
    private Context context;

    public HistoryListAdapter(List<History> historyList, int historyListLayout, Context context) {
        this.historyList = historyList;
        this.historyListLayout = historyListLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(historyListLayout,viewGroup,false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, int i) {
        historyViewHolder.txtViolation.setText(historyList.get(i).getViolation());
        historyViewHolder.txtAmount.setText(historyList.get(i).getAmount());
        historyViewHolder.txttimestamp.setText(historyList.get(i).getTimestamp());
    }

    @Override
    public int getItemCount() {
         return historyList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView txtViolation,txtAmount,txttimestamp;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViolation = itemView.findViewById(R.id.textView_violation);
            txtAmount = itemView.findViewById(R.id.textView_amount);
            txttimestamp = itemView.findViewById(R.id.textView_timestamp);

        }
    }
}
