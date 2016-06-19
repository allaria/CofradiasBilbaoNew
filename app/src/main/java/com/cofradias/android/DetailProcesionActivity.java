package com.cofradias.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.cofradias.android.model.adapter.ViewPagerProcesionDetailAdpter;
import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Cofradia;
import com.cofradias.android.model.object.Procesion;
import com.squareup.picasso.Picasso;

/**
 * Created by alaria on 21/04/2016.
 */
public class DetailProcesionActivity extends AppCompatActivity {

    private final String LOG_TAG = DetailProcesionActivity.class.getSimpleName();

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    private ImageView mEscudoDetailPhoto;
    private TextView mNombreCofradia;
    private Procesion procesion;
    private Cofradia cofradia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        procesion = (Procesion) intent.getSerializableExtra(Constants.REFERENCE.PROCESION);
        cofradia = (Cofradia) intent.getSerializableExtra(Constants.REFERENCE.COFRADIA);

        setContentView(R.layout.detail_procesion);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setLogo(R.drawable.logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String detailImg = cofradia.getImagenEscudo();
        int idDrawable = getResources().getIdentifier(detailImg, "drawable", getApplicationContext().getPackageName());

        mEscudoDetailPhoto = (ImageView) findViewById(R.id.escudoDetailPhoto);
        Picasso.with(getApplicationContext()).load(idDrawable).into(mEscudoDetailPhoto);

        mNombreCofradia = (TextView) findViewById(R.id.cofradia_name);
        mNombreCofradia.setText(cofradia.getNombreCofradia());

        viewPager = (ViewPager) findViewById(R.id.viewpager_maps);
        final ViewPagerProcesionDetailAdpter viewPagerProcesionDetailAdpter = new ViewPagerProcesionDetailAdpter(getSupportFragmentManager(), this.getApplicationContext(), procesion);
        viewPager.setAdapter(viewPagerProcesionDetailAdpter);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_maps);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}