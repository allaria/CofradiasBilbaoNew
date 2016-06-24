package com.cofradias.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.cofradias.android.model.adapter.ProcesionAdapter;
import com.cofradias.android.model.adapter.ViewPagerCofradiaDetailAdpter;
import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Cofradia;
import com.cofradias.android.model.object.Procesion;
import com.cofradias.android.view.DetailCofradiaFragment;
import com.squareup.picasso.Picasso;

/**
 * Created by alaria on 21/04/2016.
 */
public class DetailCofradiaActivity extends AppCompatActivity implements DetailCofradiaFragment.InterfaceDetailCofradia {

    private static final String LOG_TAG = DetailCofradiaActivity.class.getSimpleName();
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView mEscudoDetailPhoto;
    private TextView mNombreCofradia;
    private Cofradia cofradia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        Intent intent = getIntent();

        cofradia = (Cofradia) intent.getSerializableExtra(Constants.REFERENCE.COFRADIA);
        Cofradia[] cofradiaList = new Cofradia[1];
        cofradiaList[0]=cofradia;

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

        viewPager = (ViewPager) findViewById(R.id.viewpager_detail);
        final ViewPagerCofradiaDetailAdpter viewPagerDetailAdpter = new ViewPagerCofradiaDetailAdpter(this, getSupportFragmentManager(), this.getApplicationContext(), cofradiaList);
        viewPager.setAdapter(viewPagerDetailAdpter);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_detail);
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
    public void onClick(int position, ProcesionAdapter mProcesionAdapter) {

        //Toast.makeText(getApplicationContext(), "Selected Date:", Toast.LENGTH_LONG).show();

        Procesion selectedProcesion = mProcesionAdapter.getSelectedProcesion(position);
        Intent intent = new Intent(DetailCofradiaActivity.this, DetailProcesionActivity.class);
        intent.putExtra(Constants.REFERENCE.PROCESION, selectedProcesion);
        intent.putExtra(Constants.REFERENCE.COFRADIA, cofradia);
        startActivity(intent);
    }
}