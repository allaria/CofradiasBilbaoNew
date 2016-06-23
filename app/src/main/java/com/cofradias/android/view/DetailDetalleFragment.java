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
import com.cofradias.android.model.object.Cofradia;

public class DetailDetalleFragment extends Fragment {

    private Cofradia cofradia;

    private ImageView mEscudoDetailPhoto, mDetailPhoto;
    private TextView mNameCofradia, mPasos, mTexto, mTextoPaso, mCofradiaDireccion, mCofradiaTelefono, mCofradiaWeb;

    View contentView;

    public void setCofradia(Cofradia cofradia) {
        this.cofradia = cofradia;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.detail_detalle_content,container, false);

//        mNameCofradia = (TextView) contentView.findViewById(R.id.cofradia_name);
//        mNameCofradia.setText(cofradia.getNombreCofradia());

        mPasos = (TextView) contentView.findViewById(R.id.cofradia_pasos);
        mPasos.setText("\nPasos (" + String.valueOf(cofradia.getNumeroPasos()) + "):");

        mTexto = (TextView) contentView.findViewById(R.id.cofradia_texto);
        mTexto.setText(cofradia.getTextoDetalle());

        mTextoPaso = (TextView) contentView.findViewById(R.id.texto_intro_paso);
        mTextoPaso.setText(cofradia.getTextoIntroPasos());

        mCofradiaDireccion = (TextView) contentView.findViewById(R.id.cofradia_direccion);
        mCofradiaDireccion.setText(cofradia.getDireccion());

        mCofradiaTelefono = (TextView) contentView.findViewById(R.id.cofradia_telefono);
        mCofradiaTelefono.setText(cofradia.getTelefono());

        mCofradiaWeb = (TextView) contentView.findViewById(R.id.cofradia_web);
        mCofradiaWeb.setText(cofradia.getWeb());

//        String detailImg = cofradia.getImagenEscudo();
//        int idDrawable = getResources().getIdentifier(detailImg, "drawable", getContext().getPackageName());

//        mEscudoDetailPhoto = (ImageView) contentView.findViewById(R.id.escudo_detail_photo);
//        Picasso.with(getContext()).load(idDrawable).into(mEscudoDetailPhoto);

        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }
}
