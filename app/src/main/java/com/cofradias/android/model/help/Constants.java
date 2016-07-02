package com.cofradias.android.model.help;

/**
 * Created by alaria on 20/04/2016.
 */
public class Constants {

    public static final class HTTP {
        public static final String BASE_URL = "https://gist.githubusercontent.com";
    }

    public static final class REFERENCE {
        public static final String COFRADIA = Config.PACKAGE_NAME + "cofradia";
        public static final String PROCESION = Config.PACKAGE_NAME + "procesion";
        public static final String PASO = Config.PACKAGE_NAME + "paso";
        public static final String IMAGENGALERIA = Config.PACKAGE_NAME + "galeria";
        public static final String ORIGEN = Config.PACKAGE_NAME + "origen";
        public static final String MENU = Config.PACKAGE_NAME + "menu";
    }

    public static final class Config {
        public static final String PACKAGE_NAME = "com.cofradias.android.";
    }

    public static final class NoImage {
        public static final String NO_IMAGE = "no_image";
    }

    public static final class ConfigFireBase {
        public static final String FIREBASE_URL = "https://mr-cofrade.firebaseio.com/";
        public static final String FIREBASE_CHILD_COFRADIAS = "cofradias";
        public static final String FIREBASE_CHILD_PASOS = "pasos";
        public static final String FIREBASE_CHILD_PROCESIONES = "procesiones";
        public static final String FIREBASE_CHILD_GALERIA = "galeria";
        public static final String FIREBASE_CHILD_EVENTOS = "eventos";
        public static final String FIREBASE_CHILD_USUARIOS = "usuarios";
        public static final String FIREBASE_CHILD_CONFIGURACION = "configuracion";
    }
}