package com.cofradias.android.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cofradias.android.R;
import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.ImagenGaleria;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaria on 27/04/2016.
 */
public class GaleriaAdapter extends RecyclerView.Adapter<GaleriaAdapter.Holder>{

    private static final String TAG = GaleriaAdapter.class.getSimpleName();
    private final GaleriaClickListener mListener;
    private List<ImagenGaleria> mImagenes;

    public GaleriaAdapter(GaleriaClickListener listener) {
        mImagenes = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_imagen_galeria, null, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        ImagenGaleria currImagen = mImagenes.get(position);

        String imgGaleria = currImagen.getThumbnail();
        int idDrawableNoImage = holder.itemView.getContext().getResources().getIdentifier(Constants.NoImage.NO_IMAGE, "drawable", holder.itemView.getContext().getPackageName());
        Picasso.with(holder.itemView.getContext())
                .load(imgGaleria)
                .placeholder(idDrawableNoImage)
                .error(idDrawableNoImage)
                .into(holder.mPhotoGaleria);

        //int idDrawable = holder.itemView.getContext().getResources().getIdentifier(imgGaleria, "drawable", holder.itemView.getContext().getPackageName());
        //Picasso.with(holder.itemView.getContext()).load(idDrawable).into(holder.mPhotoGaleria);
        //Picasso.with(getApplicationContext()).load("http://services.hanselandpetal.com/photos/" + flower.getPhoto()).into(mPhoto);
        //holder.mImagenName.setText(currImagen.getCaption());
    }

    @Override
    public int getItemCount() {
        return mImagenes.size();
    }

    public void addImagenGaleria(ImagenGaleria imagenGaleria) {
        mImagenes.add(imagenGaleria);
        notifyDataSetChanged();
    }

    public ImagenGaleria getSelectedImagenGaleria(int position) {
        return mImagenes.get(position);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mPhotoGaleria;
        private TextView mImagenName;

        public Holder(View itemView) {
            super(itemView);
            //mImagenName = (TextView) itemView.findViewById(R.id.ImagenGaleriaName);
            mPhotoGaleria = (ImageView) itemView.findViewById(R.id.imagen_galeria_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClickGaleria(getLayoutPosition());
        }
    }

    public interface GaleriaClickListener {
        void onClickGaleria(int position);
    }
}
