package com.cofradias.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.cofradias.android.model.adapter.GaleriaAdapter;
import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Cofradia;
import com.cofradias.android.model.object.ImagenGaleria;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

/**
 * Created by alaria on 17/05/2016.
 */
public class GaleriaActivity extends AppCompatActivity implements GaleriaAdapter.GaleriaClickListener{

    private static final String LOG_TAG = GaleriaActivity.class.getSimpleName();
    private ProgressBar spinner;
    private RecyclerView mRecyclerView;
    private GaleriaAdapter mGaleriaAdapter;
    private ImagenGaleria selectedImagenGaleria;
    private Cofradia cofradia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.galeria_activity);

        spinner = (ProgressBar)findViewById(R.id.galeria_progress_bar);
        spinner.setVisibility(View.VISIBLE);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_galeria);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        mGaleriaAdapter = new GaleriaAdapter(this);
        mRecyclerView.setAdapter(mGaleriaAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_galeria);
        toolbar.setLogo(R.drawable.logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_GALERIA);
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    mGaleriaAdapter.addImagenGaleria(dataSnapshot.getValue(ImagenGaleria.class));
                    mGaleriaAdapter.notifyDataSetChanged();
                }

                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }


    @Override
    public void onClickGaleria(int position) {

        selectedImagenGaleria = mGaleriaAdapter.getSelectedImagenGaleria(position);

        Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_COFRADIAS);
        Query queryRef = myFirebaseRef.orderByChild("id_cofradia").equalTo(selectedImagenGaleria.getId_cofradia());
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    cofradia = dataSnapshot.getValue(Cofradia.class);
                }

                Intent intent = new Intent(getApplicationContext(), DetailMostrarImagenActivity.class);
                intent.putExtra(Constants.REFERENCE.IMAGENGALERIA, selectedImagenGaleria);
                intent.putExtra(Constants.REFERENCE.ORIGEN, "GaleriaActivity");
                intent.putExtra(Constants.REFERENCE.COFRADIA, cofradia);
                startActivity(intent);
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });


    }
}
