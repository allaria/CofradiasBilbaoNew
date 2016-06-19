package com.cofradias.android.model.object;

import java.io.Serializable;

/**
 * Created by alaria on 17/05/2016.
 */
public class Evento implements Serializable {

    public String hora, id_cofradia, imagenOrganizador, imagenVista, organizador, textoEvento;


    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getId_cofradia() {
        return id_cofradia;
    }

    public void setId_cofradia(String id_cofradia) {
        this.id_cofradia = id_cofradia;
    }

    public String getImagenOrganizador() {
        return imagenOrganizador;
    }

    public void setImagenOrganizador(String imagenOrganizador) {
        this.imagenOrganizador = imagenOrganizador;
    }

    public String getImagenVista() {
        return imagenVista;
    }

    public void setImagenVista(String imagenVista) {
        this.imagenVista = imagenVista;
    }

    public String getOrganizador() {
        return organizador;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    public String getTextoEvento() {
        return textoEvento;
    }

    public void setTextoEvento(String textoEvento) {
        this.textoEvento = textoEvento;
    }


    public Evento() {
    }
}
