package com.cofradias.android.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cofradias.android.DetailMostrarImagenActivity;
import com.cofradias.android.R;
import com.cofradias.android.model.adapter.PasoAdapter;
import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Cofradia;
import com.cofradias.android.model.object.Paso;
import com.cofradias.android.model.object.Procesion;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class DetailProcesionPasosFragment extends Fragment implements PasoAdapter.PasoClickListener {

    private ProgressBar spinner;
    private RecyclerView mRecyclerView;
    private PasoAdapter mPasoAdapter;
    private Cofradia cofradia;
    private Procesion procesion;
    private ImageView mEscudoDetailPhoto;
    private TextView mNameCofradia;

    View contentView;

    public void setCofradia(Cofradia cofradia) {
        this.cofradia = cofradia;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        procesion = (Procesion) getArguments().getSerializable("PROCESION_KEY");

        contentView = inflater.inflate(R.layout.detail_procesion_paso_content,container, false);

        spinner = (ProgressBar)contentView.findViewById(R.id.procesion_paso_detail_progress_bar);
        spinner.setVisibility(View.VISIBLE);

        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerViewProcesionPaso);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mPasoAdapter = new PasoAdapter(this);
        mRecyclerView.setAdapter(mPasoAdapter);

//        mNameCofradia = (TextView) contentView.findViewById(R.id.cofradia_name_paso);
//        mNameCofradia.setText(cofradia.getNombreCofradia());

//        String detailImg = cofradia.getImagenEscudo();
//        int idDrawable = getResources().getIdentifier(detailImg, "drawable", getContext().getPackageName());

//        mEscudoDetailPhoto = (ImageView) contentView.findViewById(R.id.escudo_detail_photo_paso);
//        Picasso.with(getContext()).load(idDrawable).into(mEscudoDetailPhoto);

        Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_PASOS);
        Query queryRef = myFirebaseRef.orderByChild("id_cofradia").equalTo(procesion.getId_cofradia());
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    mPasoAdapter.addPaso(dataSnapshot.getValue(Paso.class));
                    mPasoAdapter.notifyDataSetChanged();
                }

                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onClickPaso(int position) {

        Paso selectedPaso = mPasoAdapter.getSelectedPaso(position);

        Intent intent = new Intent(getActivity(), DetailMostrarImagenActivity.class);
        intent.putExtra(Constants.REFERENCE.PASO, selectedPaso);
        intent.putExtra(Constants.REFERENCE.COFRADIA, cofradia);
        intent.putExtra(Constants.REFERENCE.ORIGEN, "DetailProcesionPasosFragment");
        startActivity(intent);
    }
}
