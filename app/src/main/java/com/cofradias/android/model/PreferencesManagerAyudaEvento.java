package com.cofradias.android.model;

import android.content.SharedPreferences;
import android.content.Context;

/**
 * Created by alaria on 04/07/2016.
 */
public class PreferencesManagerAyudaEvento {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    // shared sharedPreferences mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "ayuda-eventos";

    private static final String MOSTRAT_AYUDA_EVENTOS = "MostrarAyudaEventos";

    public PreferencesManagerAyudaEvento(Context context) {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setMostrarAyudaEventos(boolean mostrarAyuda) {
        editor.putBoolean(MOSTRAT_AYUDA_EVENTOS, mostrarAyuda);
        editor.commit();
    }

    public boolean mostrarAyudaEventos() {
        return sharedPreferences.getBoolean(MOSTRAT_AYUDA_EVENTOS, true);
    }

}
