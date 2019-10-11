package com.accubits.trafficticketing;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.accubits.trafficticketing.Model.History;
import java.util.List;

public class NewViolationAndHistoryActivity extends AppCompatActivity {
    LinearLayout history_ul,new_violation_ul;
    RecyclerView rvHistoryList;
    TextView txtHistory,txtNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_violation_and_history);
        new_violation_ul = findViewById(R.id.new_ul);
        history_ul = findViewById(R.id.history_ul);
        rvHistoryList = findViewById(R.id.rv_history_list);
        txtHistory = findViewById(R.id.textView_history);
        txtNew = findViewById(R.id.textView_new);

        txtHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentHistory();
            }
        });

        txtNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewViolationFragment();
            }
        });

        List<History> historyList = getIntent().getParcelableExtra("History_list");

        Toast.makeText(this, ""+historyList, Toast.LENGTH_SHORT).show();

       /* rvHistoryList.setLayoutManager(new LinearLayoutManager(this));
        rvHistoryList.setAdapter(new HistoryListAdapter(historyList, R.layout.history_list, getApplicationContext()));*/
        hideStatusBar();
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

    private void FragmentHistory(){
        new_violation_ul.setVisibility(View.GONE);
        history_ul.setVisibility(View.VISIBLE);
        Fragment historyFragment = new HistoryFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container,historyFragment );
        transaction.commit();
    }

    private void NewViolationFragment(){
        new_violation_ul.setVisibility(View.VISIBLE);
        history_ul.setVisibility(View.GONE);
        Fragment newViolationFragment = new NewViolationFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container,newViolationFragment );
        transaction.commit();
    }

}
