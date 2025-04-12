package com.example.task1apitransactions.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.task1apitransactions.models.Transaction;

@Entity(tableName = "transactions")
public class TransactionEntity {

    @PrimaryKey
    @NonNull
    public String id;
    public double amount;
    public String date;
    public String description;

    public static TransactionEntity fromApiModel(Transaction transaction) {
        TransactionEntity entity = new TransactionEntity();
        entity.id = transaction.getId();
        entity.amount = transaction.getAmount();
        entity.date = transaction.getDate();
        entity.description = transaction.getDescription();
        return entity;
    }

    public Transaction toApiModel() {
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setAmount(amount);
        transaction.setDate(date);
        transaction.setDescription(description);
        return transaction;
    }
}
