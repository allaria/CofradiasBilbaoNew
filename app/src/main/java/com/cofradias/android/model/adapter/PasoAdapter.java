package com.cofradias.android.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cofradias.android.R;
import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Paso;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaria on 27/04/2016.
 */
public class PasoAdapter extends RecyclerView.Adapter<PasoAdapter.Holder>{

    private static final String TAG = PasoAdapter.class.getSimpleName();
    private final PasoClickListener mListener;
    private List<Paso> mPasos;

    public PasoAdapter(PasoClickListener listener) {
        mPasos = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_paso, null, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Paso currPaso = mPasos.get(position);

        String imgPaso = currPaso.getImagenPaso();
        int idDrawableNoImage = holder.itemView.getContext().getResources().getIdentifier(Constants.NoImage.NO_IMAGE, "drawable", holder.itemView.getContext().getPackageName());
        Picasso.with(holder.itemView.getContext())
                .load(imgPaso)
                .placeholder(idDrawableNoImage)
                .error(idDrawableNoImage)
                .into(holder.mPhotoPaso);
        holder.mPasoName.setText(currPaso.getNombrePaso());

        //int idDrawable = holder.itemView.getContext().getResources().getIdentifier(imgGaleria, "drawable", holder.itemView.getContext().getPackageName());
        //Picasso.with(holder.itemView.getContext()).load(idDrawable).into(holder.mPhotoGaleria);
    }

    @Override
    public int getItemCount() {
        return mPasos.size();
    }

    public void addPaso(Paso paso) {
        mPasos.add(paso);
        notifyDataSetChanged();
    }

    public Paso getSelectedPaso(int position) {
        return mPasos.get(position);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mPhotoPaso;
        private TextView mPasoName;

        public Holder(View itemView) {
            super(itemView);
            mPasoName = (TextView) itemView.findViewById(R.id.paso_name);
            mPhotoPaso = (ImageView) itemView.findViewById(R.id.paso_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClickPaso(getLayoutPosition());
        }
    }

    public interface PasoClickListener {
        void onClickPaso(int position);
    }
}
