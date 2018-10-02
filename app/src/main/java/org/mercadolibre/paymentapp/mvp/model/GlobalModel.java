package org.mercadolibre.paymentapp.mvp.model;

import android.content.Context;

import org.mercadolibre.paymentapp.mvp.MVP;
import org.mercadolibre.paymentapp.mvp.common.generic.GenericModel;


/**
  * Created by Enny Querales
 */
public class GlobalModel
        extends GenericModel
        implements MVP.ProvidedModelMethods {


    public GlobalModel() {
        // no-op
    }

    public GlobalModel(Context context) {
        super(context);
    }
}
