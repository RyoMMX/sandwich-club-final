package com.udacity.sandwichclub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.udacity.sandwichclub.adapter.SandwichAdapter;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.sandwiches_recycler_view);
        try {
            setUpRecylerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setUpRecylerView() throws JSONException {
        SandwichAdapter sandwichAdapter = new SandwichAdapter(JsonUtils.parseSandwichesJson(this), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(sandwichAdapter);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
