package com.example.task1apitransactions.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task1apitransactions.R;
import com.example.task1apitransactions.api.ApiClient;
import com.example.task1apitransactions.api.ApiInterface;
import com.example.task1apitransactions.api.ApiResponse;
import com.example.task1apitransactions.auth.BiometricAuthHelper;
import com.example.task1apitransactions.auth.TokenManager;
import com.example.task1apitransactions.db.AppDatabase;
import com.example.task1apitransactions.db.TransactionEntity;
import com.example.task1apitransactions.models.Transaction;
import com.example.task1apitransactions.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BiometricAuthHelper.BiometricAuthCallback {
    private RecyclerView rvTransactions;
    private TokenManager tokenManager;
    private boolean shouldAuthenticate = true;
    private TransactionsAdapter transactionsAdapter;
    private List<Transaction> allTransactions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tokenManager = new TokenManager(this);
        rvTransactions = findViewById(R.id.rvTransactions);
        rvTransactions.setLayoutManager(new LinearLayoutManager(this));

        if (getIntent().getBooleanExtra("from_auth", false)) {
            shouldAuthenticate = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (shouldAuthenticate && tokenManager.isBiometricEnabled()) {
            authenticateWithBiometrics();
        } else {
            fetchTransactions();
        }
    }

    private boolean isDarkModeEnabled() {
        return (getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
    }

    private void authenticateWithBiometrics() {
        BiometricAuthHelper biometricAuthHelper = new BiometricAuthHelper(this, this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            biometricAuthHelper.showBiometricPrompt();
        }
    }

    private void fetchTransactions() {
        if (NetworkUtils.isNetworkAvailable(this)) {
            fetchFromApi();
        } else {
            fetchFromDatabase();
        }
    }

    private void fetchFromApi() {
        String token = tokenManager.getToken();
        if (token == null || token.isEmpty()) {
            returnToLogin();
            return;
        }

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Transaction>> call = apiInterface.getTransactions(token);

        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allTransactions = response.body();
                    transactionsAdapter = new TransactionsAdapter(allTransactions);
                    rvTransactions.setAdapter(transactionsAdapter);
                    saveToDatabase(allTransactions);
                } else {
                    Toast.makeText(MainActivity.this,
                            "Failed to fetch: " + ApiResponse.getErrorMessage(response),
                            Toast.LENGTH_SHORT).show();
                    fetchFromDatabase();
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                fetchFromDatabase();
            }
        });
    }

    private void fetchFromDatabase() {
        new Thread(() -> {
            List<TransactionEntity> entities = AppDatabase.getInstance(this)
                    .transactionDao().getAll();

            List<Transaction> transactions = new ArrayList<>();
            for (TransactionEntity entity : entities) {
                transactions.add(entity.toApiModel());
            }

            runOnUiThread(() -> {
                if (!transactions.isEmpty()) {
                    allTransactions = transactions;
                    transactionsAdapter = new TransactionsAdapter(allTransactions);
                    rvTransactions.setAdapter(transactionsAdapter);
                    Toast.makeText(this, "Showing offline data", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No offline data available", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    private void saveToDatabase(List<Transaction> transactions) {
        new Thread(() -> {
            AppDatabase.getInstance(this).transactionDao().deleteAll();

            List<TransactionEntity> entities = new ArrayList<>();
            for (Transaction transaction : transactions) {
                entities.add(TransactionEntity.fromApiModel(transaction));
            }

            AppDatabase.getInstance(this).transactionDao().insertAll(entities);
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);

        MenuItem darkModeItem = menu.findItem(R.id.menu_dark_mode);
        darkModeItem.setChecked(isDarkModeEnabled());

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (transactionsAdapter != null) {
                    transactionsAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            tokenManager.clearToken();
            returnToLogin();
            return true;
        }

        if (item.getItemId() == R.id.menu_dark_mode) {

            boolean isChecked = !item.isChecked();
            item.setChecked(isChecked);
            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES
                            : AppCompatDelegate.MODE_NIGHT_NO);
            recreate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void returnToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAuthenticationSuccess() {
        shouldAuthenticate = false;
        fetchTransactions();
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        Toast.makeText(this, "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
        returnToLogin();
    }
}
