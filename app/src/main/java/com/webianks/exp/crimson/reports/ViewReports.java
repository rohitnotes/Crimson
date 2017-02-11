
package com.webianks.exp.crimson.reports;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.webianks.exp.crimson.R;

import java.util.ArrayList;
import java.util.List;


public class ViewReports extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView  = (RecyclerView) findViewById(R.id.recyclerview);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,4);

        recyclerView.setLayoutManager(gridLayoutManager);

        getReports();

    }

    private void getReports() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reportsReference = database.getReference("reports");

        final List<AllReports> allReportses = new ArrayList<>();


        reportsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                AllReports reports;

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {

                     String value = String.valueOf(postSnapshot.getValue());
                     reports = new AllReports();

                     reports.setUrl(value);
                     allReportses.add(reports);

                }

                showImages(allReportses);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.getMessage());
            }
        });

    }


    private void showImages(List<AllReports> allReportses) {

        ReportsRecyclerViewAdapter reportsRecyclerViewAdapter = new ReportsRecyclerViewAdapter(this,allReportses);
        recyclerView.setAdapter(reportsRecyclerViewAdapter);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
