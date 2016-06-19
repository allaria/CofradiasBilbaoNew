package com.cofradias.android.model.object;

import java.io.Serializable;

/**
 * Created by alaria on 07/06/2016.
 */
public class Ruta implements Serializable {

    private String calle, id_procesion, imagenRuta;

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getId_procesion() {
        return id_procesion;
    }

    public void setId_procesion(String id_procesion) {
        this.id_procesion = id_procesion;
    }

    public String getImagenRuta() {
        return imagenRuta;
    }

    public void setImagenRuta(String imagenRuta) {
        this.imagenRuta = imagenRuta;
    }

    public Ruta() {
    }
}
