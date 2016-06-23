package com.cofradias.android.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cofradias.android.R;
import com.cofradias.android.model.DividerItemDecoration;
import com.cofradias.android.model.adapter.RutaAdapter;
import com.cofradias.android.model.object.Procesion;
import com.cofradias.android.model.object.Ruta;

import java.util.List;

public class DetailIndicacionesFragment extends Fragment {

    private final String LOG_TAG = DetailIndicacionesFragment.class.getSimpleName();

    public Procesion procesion;

    private RecyclerView mRecyclerView;
    private RutaAdapter mRutaAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        procesion = (Procesion) getArguments().getSerializable("PROCESION_KEY");

        View contentView = inflater.inflate(R.layout.detail_procesion_indicaciones_content, container, false);

        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerViewIndicacion);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        mRutaAdapter = new RutaAdapter();

        mRecyclerView.setAdapter(mRutaAdapter);

        List<Ruta> rutaList = procesion.getRuta();
        for (int i=0; i< rutaList.size(); i++){

            mRutaAdapter.addRuta(rutaList.get(i));
            mRutaAdapter.notifyDataSetChanged();
        }

        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }
}
