package com.cofradias.android.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cofradias.android.R;
import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Coordenada;
import com.cofradias.android.model.object.Procesion;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class COPY_DetailMapsFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private final String LOG_TAG = COPY_DetailMapsFragment.class.getSimpleName();

    private Procesion procesion;
    private View view;
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;
    String origen;
    private Location mLastLocation;

    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        procesion = (Procesion) getArguments().getSerializable("PROCESION_KEY");

        view = inflater.inflate(R.layout.detail_procesion_maps_content, container, false);
        mMapView = (MapView) view.findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floation_button_posicion);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                getMyLocation("onCreateView");
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        List<Coordenada> coordenadaList = procesion.getCoordenadas();
        List<LatLng> coordenadasMapa = new ArrayList<LatLng>();
        for (int i = 0; i < coordenadaList.size(); i++) {
            Coordenada coordenada = coordenadaList.get(i);

            //Log.v("Longitud - Latitud", coordenada.getLongitud() + " - " + coordenada.getLatitud());
            coordenadasMapa.add(new LatLng(
                            coordenada.getLongitud(),
                            coordenada.getLatitud())
                    //Double.parseDouble(coordenada.getLongitud()),
                    //Double.parseDouble(coordenada.getLatitud()))
            );
        }

        LatLng inicioProcesion = coordenadasMapa.get(0);
        mGoogleMap.setMapType(4);
        Marker myMarkerStart = mGoogleMap.addMarker(new MarkerOptions()
                .position(inicioProcesion)
                .title(procesion.getNombreProcesion())
        );
        myMarkerStart.showInfoWindow();

        Firebase myFirebaseRef1 = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_PROCESIONES + "/001-PRO");
        Log.v(LOG_TAG,Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_PROCESIONES + "/001-PRO");
        //Query queryRef = myFirebaseRef1.orderByChild("id_cofradia").equalTo(selectedPaso.getId_cofradia());
        //Firebase myProcesion1 = myFirebaseRef1.child("001-PRO");
        myFirebaseRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String mLongitud = (String) snapshot.child("longitudActual").getValue();
                String mLatitud = (String) snapshot.child("latitudActual").getValue();

                Log.v(LOG_TAG,mLongitud + " - " + mLatitud);

                LatLng ubicacioProcesion = new LatLng(Double.parseDouble(mLongitud),Double.parseDouble(mLatitud));
                Marker myMarkerProcesion = mGoogleMap.addMarker(new MarkerOptions()
                        .position(ubicacioProcesion)
                );
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }

        });

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            return;
        } else {

            int off = 0;
            try {
                off = Settings.Secure.getInt(getContext().getContentResolver(), Settings.Secure.LOCATION_MODE);
                if (off == 0) {
                    Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(onGPS);
                }
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
        }
        mGoogleMap.setMyLocationEnabled(true);

        float mxZoom = mGoogleMap.getMaxZoomLevel();
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(inicioProcesion, 16.0f));

        Polyline polyline = mGoogleMap.addPolyline(new PolylineOptions()
                .addAll(coordenadasMapa)
                .color(Color.RED)
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), R.string.permiso_gps_ok, Toast.LENGTH_LONG).show();

                    int off = 0;
                    try {
                        off = Settings.Secure.getInt(getContext().getContentResolver(), Settings.Secure.LOCATION_MODE);
                        if (off == 0) {
                            Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(onGPS);
                        }
                    } catch (Settings.SettingNotFoundException e) {
                        e.printStackTrace();
                    }

                    Log.v(LOG_TAG,"Llamada de nuevo a la generación del mapa.");
                    onMapReady(mGoogleMap);
                } else {
                    Toast.makeText(getContext(), R.string.permiso_gps_ko, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void getMyLocation(String origen) {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            return;
        } else {

            if(origen=="onCreateView"){
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (mLastLocation != null) {

                    Log.v(LOG_TAG,"mLastLocation distinto a null");
                    Toast.makeText(getContext(), String.valueOf(mLastLocation.getLatitude()) + "\n"
                            + String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_LONG).show();

                    Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_PROCESIONES);
                    Firebase myProcesion = myFirebaseRef.child("001-PRO");
                    myProcesion.child("latitudActual").setValue(String.valueOf(mLastLocation.getLatitude()));
                    myProcesion.child("longitudActual").setValue(String.valueOf(mLastLocation.getLongitude()));



                } else {

                    Log.v(LOG_TAG,"mLastLocation igual a null");
                    Toast.makeText(getContext(), "mLastLocation == null", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        Log.v(LOG_TAG,"connect");
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        Log.v(LOG_TAG,"disconnect");
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getMyLocation("onConnected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getContext(), "Conexión suspendida: " + String.valueOf(i), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getContext(), "Error de conexión: \n" + connectionResult.toString(), Toast.LENGTH_LONG).show();
    }
}
