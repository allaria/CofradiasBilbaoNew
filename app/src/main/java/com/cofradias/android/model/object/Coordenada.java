package com.cofradias.android.model.object;

import java.io.Serializable;

/**
 * Created by alaria on 29/04/2016.
 */
public class Coordenada implements Serializable {

    private String id_procesion;
    private Double latitud, longitud;


    public String getId_procesion() {
        return id_procesion;
    }

    public void setId_procesion(String id_procesion) {
        this.id_procesion = id_procesion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }


    public Coordenada() {
    }
}
