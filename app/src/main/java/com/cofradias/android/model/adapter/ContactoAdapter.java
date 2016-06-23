package com.cofradias.android.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cofradias.android.R;
import com.cofradias.android.model.object.Contacto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaria on 27/04/2016.
 */
public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.Holder>{

    private static final String TAG = ContactoAdapter.class.getSimpleName();
    private final ContactoClickListener mListener;
    private List<Contacto> mContactos;

    public ContactoAdapter(ContactoClickListener listener) {
        mContactos = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_contacto, null, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        int idDrawable;

        Contacto currContacto = mContactos.get(position);

        String imgEscudo = currContacto.getEscudoCofradia();
        int idDrawableEscudo = holder.itemView.getContext().getResources().getIdentifier(imgEscudo, "drawable", holder.itemView.getContext().getPackageName());
        Picasso.with(holder.itemView.getContext())
                .load(idDrawableEscudo)
                .into(holder.mPhotoEscudo);
        holder.mContactoName.setText(currContacto.getNombreCofradia());
        idDrawable = holder.itemView.getContext().getResources().getIdentifier("ic_contacto_direccion", "drawable", holder.itemView.getContext().getPackageName());
        Picasso.with(holder.itemView.getContext()).load(idDrawable).into(holder.mPhotoDireccion);
        holder.mContactoDireccion.setText(currContacto.getDireccion());
        idDrawable = holder.itemView.getContext().getResources().getIdentifier("ic_contacto_telefono", "drawable", holder.itemView.getContext().getPackageName());
        Picasso.with(holder.itemView.getContext()).load(idDrawable).into(holder.mPhotoTelefono);
        holder.mContactoTelefono.setText(currContacto.getTelefono());
        idDrawable = holder.itemView.getContext().getResources().getIdentifier("ic_contacto_email", "drawable", holder.itemView.getContext().getPackageName());
        Picasso.with(holder.itemView.getContext()).load(idDrawable).into(holder.mPhotoEmail);
        holder.mContactoEmail.setText(currContacto.getEmail());
        idDrawable = holder.itemView.getContext().getResources().getIdentifier("ic_contacto_web", "drawable", holder.itemView.getContext().getPackageName());
        Picasso.with(holder.itemView.getContext()).load(idDrawable).into(holder.mPhotoWeb);
        holder.mContactoWeb.setText(currContacto.getWeb());

        //int idDrawable = holder.itemView.getContext().getResources().getIdentifier(imgGaleria, "drawable", holder.itemView.getContext().getPackageName());
        //Picasso.with(holder.itemView.getContext()).load(idDrawable).into(holder.mPhotoGaleria);
    }

    @Override
    public int getItemCount() {
        return mContactos.size();
    }

    public void addContacto(Contacto contacto) {
        mContactos.add(contacto);
        notifyDataSetChanged();
    }

    public Contacto getSelectedContacto(int position) {
        return mContactos.get(position);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mPhotoDireccion, mPhotoEscudo, mPhotoTelefono, mPhotoWeb, mPhotoEmail;
        private TextView mContactoName, mContactoDireccion, mContactoTelefono, mContactoWeb, mContactoEmail;

        public Holder(View itemView) {
            super(itemView);

            mPhotoEscudo = (ImageView) itemView.findViewById(R.id.imagen_escudo);
            mContactoName = (TextView) itemView.findViewById(R.id.contacto_name);
            mPhotoDireccion = (ImageView) itemView.findViewById(R.id.icono_direccion);
            mContactoDireccion = (TextView) itemView.findViewById(R.id.contacto_direccion);
            mPhotoTelefono = (ImageView) itemView.findViewById(R.id.icono_telefono);
            mContactoTelefono = (TextView) itemView.findViewById(R.id.contacto_telefono);
            mPhotoEmail = (ImageView) itemView.findViewById(R.id.icono_email);
            mContactoEmail = (TextView) itemView.findViewById(R.id.contacto_email);
            mPhotoWeb = (ImageView) itemView.findViewById(R.id.icono_web);
            mContactoWeb = (TextView) itemView.findViewById(R.id.contacto_web);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClickContacto(getLayoutPosition());
        }
    }

    public interface ContactoClickListener {
        void onClickContacto(int position);
    }
}
