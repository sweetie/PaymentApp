package org.mercadolibre.paymentapp.mvp.common.interfaces;

/**
 * Created by Enny Querales
 */

public interface Subject {
    public void register(Observer observer);
    public void unregister(Observer observer);
    <T> void notifyObservers(T model);
}
