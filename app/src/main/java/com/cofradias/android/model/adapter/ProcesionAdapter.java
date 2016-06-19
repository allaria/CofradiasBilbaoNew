package com.cofradias.android.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cofradias.android.R;
import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Procesion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaria on 27/04/2016.
 */
public class ProcesionAdapter extends RecyclerView.Adapter<ProcesionAdapter.Holder>{

    private static final String TAG = ProcesionAdapter.class.getSimpleName();
    private final ProcesionClickListener mListener;
    private List<Procesion> mProcesiones;

    public ProcesionAdapter(ProcesionClickListener listener) {
        mProcesiones = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_procesion, null, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Procesion currProcesion = mProcesiones.get(position);

        String imgProcesion = currProcesion.getImagenProcesion();
        int idDrawable = holder.itemView.getContext().getResources().getIdentifier(imgProcesion, "drawable", holder.itemView.getContext().getPackageName());
        int idDrawableNoImage = holder.itemView.getContext().getResources().getIdentifier(Constants.NoImage.NO_IMAGE, "drawable", holder.itemView.getContext().getPackageName());
        Picasso.with(holder.itemView.getContext())
                .load(idDrawable)
                .placeholder(idDrawableNoImage)
                .error(idDrawableNoImage)
                .into(holder.mPhotoProcesion);
        holder.mProcesion.setText(currProcesion.getNombreProcesion());
    }

    @Override
    public int getItemCount() {
        return mProcesiones.size();
    }

    public void addProcesion(Procesion procesion) {
        mProcesiones.add(procesion);
        notifyDataSetChanged();
    }

    public Procesion getSelectedProcesion(int position) {
        return mProcesiones.get(position);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mPhotoProcesion;
        private TextView mProcesion;

        public Holder(View itemView) {
            super(itemView);
            mProcesion = (TextView) itemView.findViewById(R.id.procesion_name);
            mPhotoProcesion = (ImageView) itemView.findViewById(R.id.procesion_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(getLayoutPosition());
        }
    }

    public interface ProcesionClickListener {
        void onClick(int position);
    }
}
