package com.cofradias.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.cofradias.android.model.adapter.ContactoAdapter;
import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Cofradia;
import com.cofradias.android.model.object.Contacto;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

/**
 * Created by alaria on 17/05/2016.
 */
public class ContactoActivity extends AppCompatActivity implements ContactoAdapter.ContactoClickListener{

    private static final String LOG_TAG = ContactoActivity.class.getSimpleName();
    private ProgressBar spinner;
    private RecyclerView mRecyclerView;
    private ContactoAdapter mContactoAdapter;
    private Contacto selectedContacto;
    private Cofradia cofradia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacto_activity);

        spinner = (ProgressBar)findViewById(R.id.contacto_progress_bar);
        spinner.setVisibility(View.VISIBLE);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_contacto);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        //mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        mContactoAdapter = new ContactoAdapter(this);
        mRecyclerView.setAdapter(mContactoAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_contacto);
        toolbar.setLogo(R.drawable.logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_COFRADIAS);
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    //Por cada Cofradia recuperamos los datos para crear el nuevo objeto Contacto
                    cofradia = dataSnapshot.getValue(Cofradia.class);

                    String nombreCofradia = cofradia.getNombreCofradia();
                    String sede = cofradia.getSede();
                    String direccion = cofradia.getDireccion();
                    String email = cofradia.getEmail();
                    String escudoCofradia = cofradia.getImagenEscudo();
                    String id_cofradia = cofradia.getId_cofradia();
                    String telefono = cofradia.getTelefono();
                    String web = cofradia.getWeb();


                    //Creamos el nuevo objeto Contacto
                    Contacto contacto = new Contacto(nombreCofradia, sede, direccion, email, id_cofradia, escudoCofradia, telefono, web);

                    mContactoAdapter.addContacto(contacto);
                    mContactoAdapter.notifyDataSetChanged();
                }

                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    @Override
    public void onClickContacto(int position) {

        selectedContacto = mContactoAdapter.getSelectedContacto(position);

        Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_COFRADIAS);
        Query queryRef = myFirebaseRef.orderByChild("id_cofradia").equalTo(selectedContacto.getIdCofradia());
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    cofradia = dataSnapshot.getValue(Cofradia.class);
                }

                Intent intent = new Intent(getApplicationContext(), DetailCofradiaActivity.class);
                intent.putExtra(Constants.REFERENCE.COFRADIA, cofradia);
                startActivity(intent);
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });


    }
}
