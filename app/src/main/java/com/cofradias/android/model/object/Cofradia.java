package com.cofradias.android.model.object;

import java.io.Serializable;

/**
 * Created by alaria on 25/04/2016.
 */
public class Cofradia implements Serializable {

    private String abad, id_cofradia, imagenDetalle, imagenEscudo, nombreCofradia, sede, textoDetalle, textoIntroPasos, vestidura;
    private int fundacion, numeroPasos, numeroProcesiones;


    public String getAbad() {
        return abad;
    }

    public void setAbad(String abad) {
        this.abad = abad;
    }

    public int getFundacion() {
        return fundacion;
    }

    public void setFundacion(int fundacion) {
        this.fundacion = fundacion;
    }

    public String getId_cofradia() {
        return id_cofradia;
    }

    public void setId_cofradia(String id_cofradia) {
        this.id_cofradia = id_cofradia;
    }

    public String getImagenDetalle() {
        return imagenDetalle;
    }

    public void setImagenDetalle(String imagenDetalle) {
        this.imagenDetalle = imagenDetalle;
    }

    public String getImagenEscudo() {
        return imagenEscudo;
    }

    public void setImagenEscudo(String imagenEscudo) {
        this.imagenEscudo = imagenEscudo;
    }

    public String getNombreCofradia() {
        return nombreCofradia;
    }

    public void setNombreCofradia(String nombreCofradia) {
        this.nombreCofradia = nombreCofradia;
    }

    public int getNumeroPasos() {
        return numeroPasos;
    }

    public void setNumeroPasos(int numeroPasos) {
        this.numeroPasos = numeroPasos;
    }

    public int getNumeroProcesiones() {
        return numeroProcesiones;
    }

    public void setNumeroProcesiones(int numeroProcesiones) {
        this.numeroProcesiones = numeroProcesiones;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getTextoDetalle() {
        return textoDetalle;
    }

    public void setTextoDetalle(String textoDetalle) {
        this.textoDetalle = textoDetalle;
    }

    public String getTextoIntroPasos() {
        return textoIntroPasos;
    }

    public void setTextoIntroPasos(String textoIntroPasos) {
        this.textoIntroPasos = textoIntroPasos;
    }

    public String getVestidura() {
        return vestidura;
    }

    public void setVestidura(String vestidura) {
        this.vestidura = vestidura;
    }


    public Cofradia() {
    }
}
