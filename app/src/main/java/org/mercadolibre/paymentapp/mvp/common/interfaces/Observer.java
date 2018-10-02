package org.mercadolibre.paymentapp.mvp.common.interfaces;

/**
 * Created by Enny Querales
 */

public interface Observer {
    <T> void update(T model);
}
