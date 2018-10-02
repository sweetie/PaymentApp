package org.mercadolibre.paymentapp.mvp.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import org.mercadolibre.paymentapp.R;
import org.mercadolibre.paymentapp.mvp.common.global.Enums.CustomTypeface;
import org.mercadolibre.paymentapp.mvp.common.utilities.FontTools;

/**
 * Custom TextView with attributes font and typeface
 *
 * Created by Enny Querales
 */
public class CustomTextView
        extends TextView {

    /**
     * Attributes
     */
    private Context context;


    public CustomTextView(Context context) {
        super(context);
        if (!isInEditMode())
            setDefaultTypeface(context);
        this.context = context;
    }

    public CustomTextView(Context context, AttributeSet attributes) {
        super(context, attributes);
        if (!isInEditMode())
            parseAttributes(context, attributes);
        this.context = context;
    }

    /**
     * Parse view attributes typeface
     *
     * @param context view context
     * @param attributes attribute set
     */
    public void parseAttributes(Context context, AttributeSet attributes) {
        TypedArray typedArray = context.obtainStyledAttributes(attributes, R.styleable.CustomTextView);

        String textViewTypeface = typedArray.getString(R.styleable.CustomTextView_typeface);

        if (textViewTypeface != null) {
            String path = FontTools.getFontTypeface(context, Integer.parseInt(textViewTypeface));
            setTypeFace(context, path);
        }

        typedArray.recycle();
    }

    /**
     * Set typeface
     *
     *  @param path relative path to font typeface
     */
    public void setTypeFace(Context context, String path) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), path);
        if (typeface != null)
            setTypeface(typeface);
    }

    /**
     * Set typeface
     *
     * @param typeface preferred
     */
    public void setTypeFace(CustomTypeface typeface) {
        String path = FontTools.getFontTypeface(context, typeface.getValue());
        setTypeFace(context, path);
    }


    /**
     * Set default typeface
     *
     * @param context view context
     */
    public void setDefaultTypeface(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), FontTools.getDefaultFontType(context));
        if (typeface != null)
            setTypeface(typeface);
    }
}
