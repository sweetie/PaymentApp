package org.mercadolibre.paymentapp.mvp.common.utilities;

import android.content.Context;

import org.mercadolibre.paymentapp.R;
import org.mercadolibre.paymentapp.mvp.common.global.Enums.CustomTypeface;


/**
 * Created by Enny Querales
 */
public class FontTools {

    /**
     * Attributes
     */
    private static CustomTypeface defaultTypeface = CustomTypeface.ROBOTO_REGULAR;
    private static final int FONT_ARRAY = R.array.fonts;


    public static String getFontTypeface(Context context, Integer typeface) {
        String fontArray[];
        fontArray = context.getResources().getStringArray(FONT_ARRAY);
        return fontArray[typeface];
    }

    public static String getDefaultFontType(Context context) {
        String fontArray[] = context.getResources().getStringArray(FONT_ARRAY);
        return fontArray[defaultTypeface.getValue()];
    }
}
