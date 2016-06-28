package com.cofradias.android.model.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaria on 27/04/2016.
 */
public class Procesion implements Serializable {

    private List<Coordenada> coordenadas;
    private List<Ruta> ruta;
    private String dia, fecha, horario, id_cofradia, id_procesion, imagenProcesion, nombreProcesion, pasos, salida, textoIntroProcesion;
    private Double latitudActual, longitudActual;

    public List<Coordenada> getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(List<Coordenada> coordenadas) {
        this.coordenadas = coordenadas;
    }

    public List<Ruta> getRuta() {
        return ruta;
    }

    public void setRuta(List<Ruta> ruta) {
        this.ruta = ruta;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getId_cofradia() {
        return id_cofradia;
    }

    public void setId_cofradia(String id_cofradia) {
        this.id_cofradia = id_cofradia;
    }

    public String getId_procesion() {
        return id_procesion;
    }

    public void setId_procesion(String id_procesion) {
        this.id_procesion = id_procesion;
    }

    public String getImagenProcesion() {
        return imagenProcesion;
    }

    public void setImagenProcesion(String imagenProcesion) {
        this.imagenProcesion = imagenProcesion;
    }

    public String getNombreProcesion() {
        return nombreProcesion;
    }

    public void setNombreProcesion(String nombreProcesion) {
        this.nombreProcesion = nombreProcesion;
    }

    public String getPasos() {
        return pasos;
    }

    public void setPasos(String pasos) {
        this.pasos = pasos;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public String getTextoIntroProcesion() {
        return textoIntroProcesion;
    }

    public void setTextoIntroProcesion(String textoIntroProcesion) {
        this.textoIntroProcesion = textoIntroProcesion;
    }

    public Double getLatitudActual() {
        return latitudActual;
    }

    public void setLatitudActual(Double latitudActual) {
        this.latitudActual = latitudActual;
    }

    public Double getLongitudActual() {
        return longitudActual;
    }

    public void setLongitudActual(Double longitudActual) {
        this.longitudActual = longitudActual;
    }

    public Procesion() {
        this.coordenadas = new ArrayList<Coordenada>();
    }
}
