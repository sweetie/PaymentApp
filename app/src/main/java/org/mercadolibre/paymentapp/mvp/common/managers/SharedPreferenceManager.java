package org.mercadolibre.paymentapp.mvp.common.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Enny Querales
 */
public class SharedPreferenceManager {

    /**
     * Get default shared preference object
     *
     * @param context application / activity context
     * @return SharedPreference reference object
     */
    public static SharedPreferences getSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Put generic value in Default shared preference file
     *
     * @param context application / activity context
     * @param key     chosen key name
     * @param value   generic T value
     */
    public static <T> void putValue(Context context, String key, T value) {
        Class typeClass = value.getClass();
        if (Boolean.class.isAssignableFrom(typeClass)) {
            getSharedPreference(context)
                    .edit()
                    .putBoolean(key, (Boolean) value)
                    .apply();
        } else if (Integer.class.isAssignableFrom(typeClass)) {
            getSharedPreference(context)
                    .edit()
                    .putInt(key, (Integer) value)
                    .apply();
        } else if (Long.class.isAssignableFrom(typeClass)) {
            getSharedPreference(context)
                    .edit()
                    .putLong(key, (Long) value)
                    .apply();
        } else if (String.class.isAssignableFrom(typeClass)) {
            getSharedPreference(context)
                    .edit()
                    .putString(key, (String) value)
                    .apply();
        }
    }

    /**
     * Get generic value from Default shared preference file
     *
     * @param key          associated key name
     * @param defaultValue generic T expected value if no associated value exist
     * @return key associated or default T value if key doesn't exist on Default shared preference file
     */
    public static <T> T getValue(Context context, Class<T> classType, String key, T defaultValue) {
        T result = null;
        if (Boolean.class.isAssignableFrom(classType))
            result = classType.cast(getSharedPreference(context).getBoolean(key, (Boolean) defaultValue));
        else if (Integer.class.isAssignableFrom(classType))
            result = classType.cast(getSharedPreference(context).getInt(key, (Integer) defaultValue));
        else if (Long.class.isAssignableFrom(classType))
            result = classType.cast(getSharedPreference(context).getLong(key, (Long) defaultValue));
        else if (String.class.isAssignableFrom(classType))
            result = classType.cast(getSharedPreference(context).getString(key, (String) defaultValue));
        return result;
    }

    /**
     * Clear  Default shared preference file
     *
     * @param context application / activity context
     */
    public static void clear(Context context) {
        getSharedPreference(context).edit().clear().apply();
    }
}