package com.accubits.trafficticketing;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnCheckHistory,btnFlagViolation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCheckHistory = findViewById(R.id.button_check_history);
        btnFlagViolation = findViewById(R.id.button_flag_violation);
        hideStatusBar();

        btnCheckHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCheckHistory();
            }
        });

        btnFlagViolation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCheckHistory();
            }
        });

    }

    private void hideStatusBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
    private void openCheckHistory(){
        Intent iCheckHistory = new Intent(MainActivity.this,CheckHistoryActivity.class);
        startActivity(iCheckHistory);
    }
}
