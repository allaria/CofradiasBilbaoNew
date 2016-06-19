package com.cofradias.android.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cofradias.android.R;
import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Cofradia;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaria on 25/04/2016.
 */
public class CofradiaAdapter extends RecyclerView.Adapter<CofradiaAdapter.Holder>{

    private static final String TAG = CofradiaAdapter.class.getSimpleName();
    private final CofradiaClickListener mListener;
    private List<Cofradia> mCofradias;

    public CofradiaAdapter(CofradiaClickListener listener) {
        mCofradias = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cofradia, null, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Cofradia currCofradia = mCofradias.get(position);

        String escudoPhoto = currCofradia.getImagenEscudo();
        String homePhoto = currCofradia.getImagenDetalle();
        int idDrawableEscudo = holder.itemView.getContext().getResources().getIdentifier(escudoPhoto, "drawable", holder.itemView.getContext().getPackageName());
        int idDrawableHome = holder.itemView.getContext().getResources().getIdentifier(homePhoto, "drawable", holder.itemView.getContext().getPackageName());
        int idDrawableNoImage = holder.itemView.getContext().getResources().getIdentifier(Constants.NoImage.NO_IMAGE, "drawable", holder.itemView.getContext().getPackageName());
        Picasso.with(holder.itemView.getContext())
                .load(idDrawableEscudo)
                .into(holder.mEscudoPhoto);
        Picasso.with(holder.itemView.getContext())
                .load(idDrawableHome)
                .placeholder(idDrawableNoImage)
                .error(idDrawableNoImage)
                .into(holder.mHomePhoto);
        holder.mName.setText(currCofradia.getNombreCofradia());

        //Picasso.with(holder.itemView.getContext()).load("https://raw.githubusercontent.com/allaria/CofradiasBilbao/master/app/src/main/res/imagenes/verMapa.png").into(holder.mPhoto);
        //Picasso.with(holder.itemView.getContext()).load(R.drawable.e_santa_vera_cruz).into(holder.mPhoto);
    }

    @Override
    public int getItemCount() {
        return mCofradias.size();
    }

    public void addCofradia(Cofradia cofradia) {
        mCofradias.add(cofradia);
        notifyDataSetChanged();
    }

    public Cofradia getSelectedCofradia(int position) {
        return mCofradias.get(position);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mEscudoPhoto, mHomePhoto;
        private TextView mName;

        public Holder(View itemView) {
            super(itemView);
            mHomePhoto = (ImageView) itemView.findViewById(R.id.cofradia_home_photo);
            mEscudoPhoto = (ImageView) itemView.findViewById(R.id.cofradia_escudo_photo);
            mName = (TextView) itemView.findViewById(R.id.cofradia_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(getLayoutPosition());
        }
    }

    public interface CofradiaClickListener {
        void onClick(int position);
    }
}
