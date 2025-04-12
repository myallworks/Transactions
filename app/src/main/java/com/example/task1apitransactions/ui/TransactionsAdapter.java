package com.example.task1apitransactions.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task1apitransactions.R;
import com.example.task1apitransactions.models.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder> implements Filterable {

    private final List<Transaction> originalList;
    private final List<Transaction> filteredList;

    public TransactionsAdapter(List<Transaction> transactions) {
        this.originalList = transactions;
        this.filteredList = new ArrayList<>(transactions);
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = filteredList.get(position);
        holder.bind(transaction);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Transaction> filtered = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filtered.addAll(originalList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Transaction item : originalList) {
                        if (item.getDescription().toLowerCase().contains(filterPattern) ||
                                item.getId().toLowerCase().contains(filterPattern)) {
                            filtered.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList.clear();
                filteredList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTransactionId;
        private final TextView tvAmount;
        private final TextView tvDate;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTransactionId = itemView.findViewById(R.id.tvTransactionId);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvDate = itemView.findViewById(R.id.tvDate);
        }

        public void bind(Transaction transaction) {
            tvTransactionId.setText(String.format("Id: %s", transaction.getId()));
            tvAmount.setText(String.format(Locale.getDefault(), "Amount: $%.2f", transaction.getAmount()));
            tvDate.setText(String.format("Date: %s", transaction.getDate()));
        }
    }
}