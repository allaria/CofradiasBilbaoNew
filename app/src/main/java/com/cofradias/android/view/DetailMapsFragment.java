package com.cofradias.android.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cofradias.android.R;
import com.cofradias.android.model.object.Coordenada;
import com.cofradias.android.model.object.Procesion;
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

public class DetailMapsFragment extends Fragment implements OnMapReadyCallback {

    private final String LOG_TAG = DetailMapsFragment.class.getSimpleName();

    public Procesion procesion;
    MapView mMapView;
    GoogleMap mGoogleMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        procesion = (Procesion) getArguments().getSerializable("PROCESION_KEY");

        View view = inflater.inflate(R.layout.detail_procesion_maps_content, container, false);
        mMapView = (MapView) view.findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        List<Coordenada> coordenadaList = procesion.getCoordenadas();
        List<LatLng> coordenadasMapa = new ArrayList<LatLng>();
        for (int i=0; i< coordenadaList.size(); i++){
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
        Marker miMarker = mGoogleMap.addMarker(new MarkerOptions()
                        .position(inicioProcesion)
                        .title(procesion.getNombreProcesion())
        );
        miMarker.showInfoWindow();

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
}
