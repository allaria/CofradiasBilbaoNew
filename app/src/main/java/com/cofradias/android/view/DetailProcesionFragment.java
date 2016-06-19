package com.cofradias.android.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cofradias.android.R;
import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Procesion;
import com.squareup.picasso.Picasso;

public class DetailProcesionFragment extends Fragment {

    private Procesion procesion;

    private ImageView mImagenDetalle;
    private TextView mNombre, mDia, mFecha, mHora, mLugarInicio, mPasos;

    View contentView;

    public void setProcesion(Procesion procesion) {
        this.procesion = procesion;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        procesion = (Procesion) getArguments().getSerializable("PROCESION_KEY");

        contentView = inflater.inflate(R.layout.detail_procesion_content,container, false);

        String procesionImg = procesion.getImagenProcesion();
        int idDrawable = getResources().getIdentifier(procesionImg, "drawable", getContext().getPackageName());

        mImagenDetalle = (ImageView) contentView.findViewById(R.id.procesion_imagen);
        int idDrawableNoImage = this.getResources().getIdentifier(Constants.NoImage.NO_IMAGE, "drawable", getContext().getPackageName());
        Picasso.with(getContext())
                .load(idDrawable)
                .placeholder(idDrawableNoImage)
                .error(idDrawableNoImage)
                .into(mImagenDetalle);
//        mEscudoDetailPhoto = (ImageView) contentView.findViewById(R.id.escudo_detail_photo_galeria);
//        Picasso.with(getContext()).load(idDrawable).into(mEscudoDetailPhoto);

        mNombre = (TextView) contentView.findViewById(R.id.procesion_nombre);
        mNombre.setText(procesion.getNombreProcesion());

        mDia = (TextView) contentView.findViewById(R.id.procesion_dia);
        mDia.setText(procesion.getDia());

        mFecha = (TextView) contentView.findViewById(R.id.procesion_fecha);
        mFecha.setText(procesion.getFecha());

        mHora = (TextView) contentView.findViewById(R.id.procesion_hora);
        mHora.setText(procesion.getHorario());

        mLugarInicio = (TextView) contentView.findViewById(R.id.procesion_lugar_inicio);
        mLugarInicio.setText(procesion.getSalida());

        mLugarInicio = (TextView) contentView.findViewById(R.id.procesion_lugar_inicio);
        mLugarInicio.setText(procesion.getSalida());

        mPasos = (TextView) contentView.findViewById(R.id.procesion_pasos);
        mPasos.setText(procesion.getPasos());

        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }
}
