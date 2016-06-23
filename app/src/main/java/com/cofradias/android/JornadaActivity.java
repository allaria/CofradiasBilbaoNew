package com.cofradias.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.cofradias.android.model.adapter.JornadaAdapter;
import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Cofradia;
import com.cofradias.android.model.object.Evento;
import com.cofradias.android.model.object.Jornada;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

/**
 * Created by alaria on 17/05/2016.
 */
public class JornadaActivity extends AppCompatActivity implements JornadaAdapter.JornadaClickListener{

    private static final String TAG = JornadaActivity.class.getSimpleName();
    private ProgressBar spinner;
    private RecyclerView mRecyclerView;
    private JornadaAdapter mJornadaAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evento_activity);

        spinner = (ProgressBar)findViewById(R.id.evento_progress_bar);
        spinner.setVisibility(View.VISIBLE);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_evento);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        mJornadaAdapter = new JornadaAdapter(this);
        mRecyclerView.setAdapter(mJornadaAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_evento);
        toolbar.setLogo(R.drawable.logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_EVENTOS);
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    mJornadaAdapter.addJornada(dataSnapshot.getValue(Jornada.class));
                    mJornadaAdapter.notifyDataSetChanged();
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
        Evento selectedEvento = mJornadaAdapter.getSelectedEvento(position);
        Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_COFRADIAS);
        Query queryRef = myFirebaseRef.orderByChild("id_cofradia").equalTo(selectedEvento.getId_cofradia());
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Cofradia selectedCofradia = dataSnapshot.getValue(Cofradia.class);

                    Intent intent = new Intent(JornadaActivity.this, DetailCofradiaActivity.class);
                    intent.putExtra(Constants.REFERENCE.COFRADIA, selectedCofradia);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }
}
