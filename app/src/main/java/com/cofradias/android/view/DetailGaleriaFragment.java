package com.cofradias.android.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cofradias.android.DetailMostrarImagenActivity;
import com.cofradias.android.R;
import com.cofradias.android.model.adapter.GaleriaAdapter;
import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Cofradia;
import com.cofradias.android.model.object.ImagenGaleria;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class DetailGaleriaFragment extends Fragment implements GaleriaAdapter.GaleriaClickListener {

    private ProgressBar spinner;
    private RecyclerView mRecyclerView;
    private GaleriaAdapter mImagenGaleriaAdapter;
    private Cofradia cofradia;
    private ImageView mEscudoDetailPhoto;
    private TextView mNombreCofradia;

    View contentView;

    public void setCofradia(Cofradia cofradia) {
        this.cofradia = cofradia;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.detail_galeria_content,container, false);

        spinner = (ProgressBar)contentView.findViewById(R.id.galeria_detail_progress_bar);
        spinner.setVisibility(View.VISIBLE);

        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerViewGaleria);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        mImagenGaleriaAdapter = new GaleriaAdapter(this);
        mRecyclerView.setAdapter(mImagenGaleriaAdapter);

//        mNombreCofradia = (TextView) contentView.findViewById(R.id.cofradia_name_galeria);
//        mNombreCofradia.setText(cofradia.getNombreCofradia());

//        String detailImg = cofradia.getImagenEscudo();
//        int idDrawable = getResources().getIdentifier(detailImg, "drawable", getContext().getPackageName());

//        mEscudoDetailPhoto = (ImageView) contentView.findViewById(R.id.escudo_detail_photo_galeria);
//        Picasso.with(getContext()).load(idDrawable).into(mEscudoDetailPhoto);

        Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_GALERIA);
        Query queryRef = myFirebaseRef.orderByChild("id_cofradia").equalTo(cofradia.getId_cofradia());
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    mImagenGaleriaAdapter.addImagenGaleria(dataSnapshot.getValue(ImagenGaleria.class));
                    mImagenGaleriaAdapter.notifyDataSetChanged();
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

    public void onClickGaleria(int position) {

        ImagenGaleria selectedImagenGaleria = mImagenGaleriaAdapter.getSelectedImagenGaleria(position);

        Intent intent = new Intent(getActivity(), DetailMostrarImagenActivity.class);
        intent.putExtra(Constants.REFERENCE.IMAGENGALERIA, selectedImagenGaleria);
        intent.putExtra(Constants.REFERENCE.COFRADIA, cofradia);
        intent.putExtra(Constants.REFERENCE.ORIGEN, "DetailGaleriaFragment");
        startActivity(intent);
    }
}
