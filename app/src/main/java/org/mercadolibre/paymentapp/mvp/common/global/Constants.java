package org.mercadolibre.paymentapp.mvp.common.global;

import android.Manifest;
import android.support.v4.util.Pair;

/**
 * Created by Enny Querales
 */
public class Constants {

    /**
     * Permission constants
     */

    public static final String API_KEY = "444a9ef5-8a6b-429f-abdf-587639155d88";
    public static final String COUNTRY = "us";
    public static final int READ_EXTERNAL_STORAGE = 10;
    public static final int WRITE_EXTERNAL_STORAGE = 20;
    public static final int CAMERA = 30;

    public static final Pair<String,Integer> READ_PERMISSION =
            new Pair<>(Manifest.permission.READ_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE);


    public static final Pair<String,Integer> WRITE_PERMISSION =
            new Pair<>(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE);

    public static final Pair<String,Integer> CAMERA_PERMISSION =
            new Pair<>(Manifest.permission.CAMERA, CAMERA);
}
