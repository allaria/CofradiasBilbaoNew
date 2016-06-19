package com.cofradias.android.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cofradias.android.R;
import com.cofradias.android.model.object.Ruta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaria on 27/04/2016.
 */
public class RutaAdapter extends RecyclerView.Adapter<RutaAdapter.Holder>{

    private static final String LOG_TAG = RutaAdapter.class.getSimpleName();
    private List<Ruta> mRuta;

    public RutaAdapter() {
        mRuta = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_indicacion, null, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Ruta currRuta = mRuta.get(position);
        holder.mIndicacionName.setText(currRuta.getCalle());
    }

    @Override
    public int getItemCount() {
        return mRuta.size();
    }

    public void addRuta(Ruta ruta) {
        mRuta.add(ruta);
        notifyDataSetChanged();
    }

    public Ruta getSelectedRuta(int position) {
        return mRuta.get(position);
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView mPhotoIndicacion;
        private TextView mIndicacionName;

        public Holder(View itemView) {
            super(itemView);
            mIndicacionName = (TextView) itemView.findViewById(R.id.indicacion_calle);
            mPhotoIndicacion = (ImageView) itemView.findViewById(R.id.indicacion_photo);
        }
    }
}
