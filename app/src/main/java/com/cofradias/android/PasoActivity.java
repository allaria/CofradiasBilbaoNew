package com.cofradias.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.cofradias.android.model.adapter.PasoAdapter;
import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Cofradia;
import com.cofradias.android.model.object.Paso;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

/**
 * Created by alaria on 17/05/2016.
 */
public class PasoActivity extends AppCompatActivity implements PasoAdapter.PasoClickListener{

    private static final String LOG_TAG = PasoActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private PasoAdapter mPasoAdapter;

    private Paso selectedPaso;
    private Cofradia cofradia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paso_activity);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_galeria);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        //mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));


        mPasoAdapter = new PasoAdapter(this);
        mRecyclerView.setAdapter(mPasoAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_paso);
        toolbar.setLogo(R.drawable.logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_PASOS);
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    mPasoAdapter.addPaso(dataSnapshot.getValue(Paso.class));
                    mPasoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }


    @Override
    public void onClickPaso(int position) {

        selectedPaso = mPasoAdapter.getSelectedPaso(position);

        Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_COFRADIAS);
        Query queryRef = myFirebaseRef.orderByChild("id_cofradia").equalTo(selectedPaso.getId_cofradia());
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    cofradia = dataSnapshot.getValue(Cofradia.class);
                }

                Intent intent = new Intent(getApplicationContext(), DetailMostrarImagenActivity.class);
                intent.putExtra(Constants.REFERENCE.PASO, selectedPaso);
                intent.putExtra(Constants.REFERENCE.ORIGEN, "PasoActivity");
                intent.putExtra(Constants.REFERENCE.COFRADIA, cofradia);
                startActivity(intent);
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });


    }
}
