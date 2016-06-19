package com.cofradias.android.model.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaria on 17/05/2016.
 */
public class Jornada implements Serializable {

    private String dia, fecha;
    private List<Evento> evento;

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

    public List<Evento> getEvento() {
        return evento;
    }

    public void setEvento(List<Evento> evento) {
        this.evento = evento;
    }

    public Jornada() {
        this.evento = new ArrayList<Evento>();
    }

}
