package com.cofradias.android.model.object;

import java.io.Serializable;

/**
 * Created by alaria on 22/06/2016.
 */
public class Contacto implements Serializable {

    private String nombreCofradia, sede, direccion, email, escudoCofradia, id_cofradia, telefono, web;

    public String getNombreCofradia() {
        return nombreCofradia;
    }

    public void setNombreCofradia(String nombreCofradia) {
        this.nombreCofradia = nombreCofradia;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEscudoCofradia() {
        return escudoCofradia;
    }

    public void setEscudoCofradia(String escudoCofradia) {
        this.escudoCofradia = escudoCofradia;
    }

    public String getIdCofradia() {
        return id_cofradia;
    }

    public void setIdCofradia(String id_cofradia) {
        this.id_cofradia = id_cofradia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }



    public Contacto() {
    }

    public Contacto(String nombreCofradia, String sede, String direccion, String email, String id_cofradia, String escudoCofradia, String telefono, String web) {
        this.nombreCofradia = nombreCofradia;
        this.sede = sede;
        this.direccion = direccion;
        this.email = email;
        this.escudoCofradia = escudoCofradia;
        this.id_cofradia = id_cofradia;
        this.telefono = telefono;
        this.web = web;

    }
}
