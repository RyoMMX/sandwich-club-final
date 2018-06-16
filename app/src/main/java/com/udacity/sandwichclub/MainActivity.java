package com.udacity.sandwichclub;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.udacity.sandwichclub.adapter.SandwichAdapter;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.NetworkUtils;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import ru.alexbykov.nopaginate.callback.OnLoadMoreListener;
import ru.alexbykov.nopaginate.paginate.NoPaginate;

/*
 * for paginating I used this library
 * https://github.com/NoNews/NoPaginate
 *
 * for API documentation see this page
 * https://developer.edamam.com/edamam-docs-recipe-api
 * */

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SandwichAdapter sandwichAdapter;
    private NoPaginate noPaginate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.sandwiches_recycler_view);
        setUpRecylerView();
    }

    private void setUpRecylerView() {
        sandwichAdapter = new SandwichAdapter(new ArrayList<Sandwich>(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(sandwichAdapter);


        noPaginate = NoPaginate.with(recyclerView)
                .setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        new LoaderAsyncTask(noPaginate, sandwichAdapter).execute();
                    }
                }).build();
    }

    private static void addData(ArrayList<Sandwich> sandwiches, SandwichAdapter sandwichAdapter) {
        if (sandwiches != null && !sandwiches.isEmpty()) {
            sandwichAdapter.addData(sandwiches);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        NetworkUtils.resetPager();
    }

    public static class LoaderAsyncTask extends AsyncTask<Void, Void, ArrayList<Sandwich>> {
        NoPaginate noPaginate;
        private SandwichAdapter sandwichAdapter;

        LoaderAsyncTask(NoPaginate noPaginate, SandwichAdapter sandwichAdapter) {
            this.noPaginate = noPaginate;
            this.sandwichAdapter = sandwichAdapter;
        }

        @Override
        protected ArrayList<Sandwich> doInBackground(Void... voids) {
            ArrayList<Sandwich> sandwiches = null;

            try {
                sandwiches = NetworkUtils.loadSandwiches();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                sandwiches = new ArrayList<>();
            } catch (UnknownHostException e) {
                e.printStackTrace();
                sandwiches = null;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return sandwiches;
        }

        @Override
        protected void onPostExecute(ArrayList<Sandwich> sandwiches) {
            super.onPostExecute(sandwiches);
            if (sandwiches == null) {
                noPaginate.showError(true);
            } else if (sandwiches.isEmpty()) {
                noPaginate.showLoading(false);
                noPaginate.setNoMoreItems(true);
            } else {
                addData(sandwiches, sandwichAdapter);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noPaginate.unbind();
    }
}
