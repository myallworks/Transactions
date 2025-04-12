package com.example.task1apitransactions.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TransactionEntity> transactions);

    @Query("SELECT * FROM transactions")
    List<TransactionEntity> getAll();

    @Query("DELETE FROM transactions")
    void deleteAll();
}
