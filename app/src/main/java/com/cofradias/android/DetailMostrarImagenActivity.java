package com.cofradias.android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Cofradia;
import com.cofradias.android.model.object.ImagenGaleria;
import com.cofradias.android.model.object.Paso;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by alaria on 29/05/2016.
 */
public class DetailMostrarImagenActivity extends AppCompatActivity {

    private final String LOG_TAG = DetailMostrarImagenActivity.class.getSimpleName();
    private Toolbar toolbar;
    Cofradia cofradia;
    private ImageView mEscudoDetailPhoto, mImagenDetalle;
    private TextView mNombreCofradia;
    private String urlImagen, textoMostrar, fileName;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_mostrar_imagen);

        Intent intent = getIntent();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String origen = (String) intent.getSerializableExtra(Constants.REFERENCE.ORIGEN);
        cofradia = (Cofradia) intent.getSerializableExtra(Constants.REFERENCE.COFRADIA);

        switch (origen){

            case "DetailGaleriaFragment":
            case "GaleriaActivity":
            {
                ImagenGaleria imagenGaleria = (ImagenGaleria) intent.getSerializableExtra(Constants.REFERENCE.IMAGENGALERIA);

                urlImagen = imagenGaleria.getImage();
                textoMostrar = cofradia.getNombreCofradia() + "\r\n\n" + imagenGaleria.getCaption();
                break;
            }

            case "DetailPasoFragment":
            case "DetailProcesionPasosFragment":
            case "PasoActivity":
            {
                Paso paso = (Paso) intent.getSerializableExtra(Constants.REFERENCE.PASO);

                urlImagen = paso.getImagenPaso();
                textoMostrar = cofradia.getNombreCofradia() + "\r\n\n" + paso.getNombrePaso();
                break;
            }
        }

/*        String detailImg = cofradia.getImagenEscudo();
        int idDrawable = getResources().getIdentifier(detailImg, "drawable", getApplicationContext().getPackageName());

        mEscudoDetailPhoto = (ImageView) findViewById(R.id.escudoDetailPhoto);
        Picasso.with(getApplicationContext()).load(idDrawable).into(mEscudoDetailPhoto);

        mNombreCofradia = (TextView) findViewById(R.id.cofradia_name);
        mNombreCofradia.setText(cofradia.getNombreCofradia());*/

        mImagenDetalle = (ImageView) this.findViewById(R.id.image_preview);
        int idDrawableNoImage = this.getResources().getIdentifier(Constants.NoImage.NO_IMAGE, "drawable", this.getPackageName());
        Picasso.with(this)
                .load(urlImagen)
                .placeholder(idDrawableNoImage)
                .error(idDrawableNoImage)
                .into(mImagenDetalle);

        mImagenDetalle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                Toast toast = Toast.makeText(getApplicationContext(), textoMostrar, Toast.LENGTH_SHORT);
                TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
                if( textView != null) textView.setGravity(Gravity.CENTER);
                toast.show();

                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.imagen_toolbar_action, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
            return true;
        }else if (id == R.id.menu_action_share) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_EXTERNAL_STORAGE);

                return true;
            } else {
                sendImage();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendImage() {
        ImageView ivImage = (ImageView) findViewById(R.id.image_preview);
        Uri bmpUri = getLocalBitmapUri(ivImage);
        if (bmpUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            shareIntent.putExtra(Intent.EXTRA_TEXT, textoMostrar);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            startActivity(Intent.createChooser(shareIntent, getString(R.string.texto_compartir_imagen)));
        } else {
            Toast.makeText(getApplicationContext(), R.string.error_share_intent, Toast.LENGTH_SHORT).show();
        }
    }


    public Uri getLocalBitmapUri(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }

        Uri bmpUri = null;
        try {
            fileName = "share_image_" + System.currentTimeMillis() + ".png";
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), fileName);
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), R.string.permiso_storage_ok, Toast.LENGTH_SHORT).show();

                    sendImage();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.permiso_storage_ko, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        //Toast.makeText(getApplicationContext(), "BORRAR", Toast.LENGTH_SHORT).show();
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), fileName);
        file.delete();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
