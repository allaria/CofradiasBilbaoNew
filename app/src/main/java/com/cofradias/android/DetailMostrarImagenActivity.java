package com.cofradias.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Cofradia;
import com.cofradias.android.model.object.ImagenGaleria;
import com.cofradias.android.model.object.Paso;
import com.squareup.picasso.Picasso;

/**
 * Created by alaria on 29/05/2016.
 */
public class DetailMostrarImagenActivity extends AppCompatActivity {

    private final String LOG_TAG = DetailMostrarImagenActivity.class.getSimpleName();

    Toolbar toolbar;
    private ImageView mEscudoDetailPhoto, mImagenDetalle;
    private TextView mNombreCofradia;

    private String imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_mostrar_imagen);

        Intent intent = getIntent();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setLogo(R.drawable.logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String origen = (String) intent.getSerializableExtra(Constants.REFERENCE.ORIGEN);
        switch (origen){

            case "DetailGaleriaFragment":
            case "GaleriaActivity":
            {
                ImagenGaleria imagenGaleria = (ImagenGaleria) intent.getSerializableExtra(Constants.REFERENCE.IMAGENGALERIA);

                imagen = imagenGaleria.getImage();

                break;
            }

            case "DetailPasoFragment":
            case "PasoActivity":
            {
                Paso paso = (Paso) intent.getSerializableExtra(Constants.REFERENCE.PASO);

                imagen = paso.getImagenPaso();

                break;
            }

//            case "GaleriaActivity":{
//                ImagenGaleria imagenGaleria = (ImagenGaleria) intent.getSerializableExtra(Constants.REFERENCE.IMAGENGALERIA);
//
//                imagen = imagenGaleria.getImage();
//
//                break;
//            }

//            case "PasoActivity": {
//                Paso paso = (Paso) intent.getSerializableExtra(Constants.REFERENCE.PASO);
//
//                imagen = paso.getImagenPaso();
//
//                break;
//            }
        }

        Cofradia cofradia = (Cofradia) intent.getSerializableExtra(Constants.REFERENCE.COFRADIA);

        String detailImg = cofradia.getImagenEscudo();
        int idDrawable = getResources().getIdentifier(detailImg, "drawable", getApplicationContext().getPackageName());

        mEscudoDetailPhoto = (ImageView) findViewById(R.id.escudoDetailPhoto);
        Picasso.with(getApplicationContext()).load(idDrawable).into(mEscudoDetailPhoto);

        mNombreCofradia = (TextView) findViewById(R.id.cofradia_name);
        mNombreCofradia.setText(cofradia.getNombreCofradia());

        mImagenDetalle = (ImageView) this.findViewById(R.id.image_preview);
        int idDrawableNoImage = this.getResources().getIdentifier(Constants.NoImage.NO_IMAGE, "drawable", this.getPackageName());
        Picasso.with(this)
                .load(imagen)
                .placeholder(idDrawableNoImage)
                .error(idDrawableNoImage)
                .into(mImagenDetalle);
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_action, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
            return true;
        }else if (id == R.id.menu_action_share) {
            Toast.makeText(this, "Compartir", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
