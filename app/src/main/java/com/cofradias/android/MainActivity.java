package com.cofradias.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.cofradias.android.model.adapter.CofradiaAdapter;
import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Cofradia;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements CofradiaAdapter.CofradiaClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    private ProgressBar spinner;
    private RecyclerView mRecyclerView;
    private CofradiaAdapter mCofradiaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        spinner = (ProgressBar)findViewById(R.id.main_progress_bar);
        spinner.setVisibility(View.VISIBLE);

        Firebase.setAndroidContext(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_main);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        mCofradiaAdapter = new CofradiaAdapter(this);
        mRecyclerView.setAdapter(mCofradiaAdapter);

        Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_COFRADIAS);
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    mCofradiaAdapter.addCofradia(dataSnapshot.getValue(Cofradia.class));
                    mCofradiaAdapter.notifyDataSetChanged();
                }

                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(FirebaseError error) { }
        });

        int idDrawableNoImage = this.getResources().getIdentifier(Constants.NoImage.NO_IMAGE, "drawable", this.getPackageName());
        Picasso.with(this)
                .load(R.drawable.intro_photo)
                .placeholder(idDrawableNoImage)
                .error(idDrawableNoImage)
                .into((ImageView) findViewById(R.id.home_photo));
    }


    @Override
    public void onClick(int position) {
        Cofradia selectedCofradia = mCofradiaAdapter.getSelectedCofradia(position);
        Intent intent = new Intent(MainActivity.this, DetailCofradiaActivity.class);
        intent.putExtra(Constants.REFERENCE.COFRADIA, selectedCofradia);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_calendario_eventos) {
            Intent intentMenu = new Intent(MainActivity.this, JornadaActivity.class);
            intentMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentMenu);
        } else if (id == R.id.nav_galeria) {
            Intent intentMenu = new Intent(MainActivity.this, GaleriaActivity.class);
            intentMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentMenu);
        } else if (id == R.id.nav_pasos) {
            Intent intentMenu = new Intent(MainActivity.this, PasoActivity.class);
            intentMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentMenu);
        } else if (id == R.id.nav_contacto) {
            Intent intentMenu = new Intent(MainActivity.this, ContactoActivity.class);
            intentMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentMenu);
        } else if (id == R.id.nav_museo_pasos) {
            Intent intentMenu = new Intent(MainActivity.this, MuseoPasosActivity.class);
            intentMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentMenu);
        } else if (id == R.id.nav_procesiones) {
            Intent intentMenu = new Intent(MainActivity.this, ProcesionActivity.class);
            intentMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentMenu);
        } else if (id == R.id.nav_login_usuario) {
            Intent intentMenu = new Intent(MainActivity.this, LoginActivity.class);
            intentMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentMenu);
        } else if (id == R.id.nav_share_app) {

            Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_CONFIGURACION);
            myFirebaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        Intent intentMenu = new Intent(Intent.ACTION_SEND);
                        intentMenu.setType("text/plain");
                        intentMenu.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
                        String texto = getString(R.string.texto_share_app);
                        texto = texto + snapshot.child("urlgoogleplay").getValue();
                        texto = texto + "\n\n";
                        intentMenu.putExtra(Intent.EXTRA_TEXT, texto);
                        startActivity(Intent.createChooser(intentMenu, getString(R.string.texto_compartir_app)));
                    }
                }

                @Override
                public void onCancelled(FirebaseError error) { }
            });


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}