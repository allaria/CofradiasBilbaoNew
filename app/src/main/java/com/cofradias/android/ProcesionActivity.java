package com.cofradias.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.cofradias.android.model.adapter.ProcesionAdapter;
import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Procesion;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by alaria on 26/06/2016.
 */
public class ProcesionActivity extends AppCompatActivity implements ProcesionAdapter.ProcesionClickListener {

    private static final String LOG_TAG = ProcesionActivity.class.getSimpleName();
    private ProgressBar spinner;
    private RecyclerView mRecyclerView;
    private ProcesionAdapter mProcesionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.procesion_activity);

        spinner = (ProgressBar)findViewById(R.id.procesion_progress_bar);
        spinner.setVisibility(View.VISIBLE);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_procesion);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        mProcesionAdapter = new ProcesionAdapter(this);
        mRecyclerView.setAdapter(mProcesionAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_procesion);
        toolbar.setLogo(R.drawable.logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_PROCESIONES);
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    mProcesionAdapter.addProcesion(dataSnapshot.getValue(Procesion.class));
                    mProcesionAdapter.notifyDataSetChanged();
                }

                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    @Override
    public void onClick(int position) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
