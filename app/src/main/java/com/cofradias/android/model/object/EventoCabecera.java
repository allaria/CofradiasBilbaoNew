package com.cofradias.android.model.object;

import java.io.Serializable;

/**
 * Created by alaria on 20/05/2016.
 */
public class EventoCabecera implements Serializable {

    public String diaCabecera, fechaCabecera;

    public String getDiaCabecera() {
        return diaCabecera;
    }

    public void setDiaCabecera(String diaCabecera) {
        this.diaCabecera = diaCabecera;
    }

    public String getFechaCabecera() {
        return fechaCabecera;
    }

    public void setFechaCabecera(String fechaCabecera) {
        this.fechaCabecera = fechaCabecera;
    }


    public EventoCabecera() {
    }

    public EventoCabecera(String diaCabecera, String fechaCabecera) {
        this.diaCabecera = diaCabecera;
        this.fechaCabecera = fechaCabecera;
    }
}
