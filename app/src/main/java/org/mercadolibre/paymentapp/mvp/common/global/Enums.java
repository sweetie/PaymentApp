package org.mercadolibre.paymentapp.mvp.common.global;

/**
 * Created by Enny Querales
 */
public class Enums {

    public enum DialogType {TIME_DETERMINED, TIME_UNDETERMINED, MESSAGE}

    public enum ScreenUnit {PX, DP}

    public enum CustomTypeface {
        ROBOTO_REGULAR(0),
        ROBOTO_MEDIUM(1),
        ROBOTO_BLACK(2);

        /**
         * Attributes
         */
        int value;

        CustomTypeface(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static CustomTypeface getEnum(int value) {
            CustomTypeface result = null;
            for (CustomTypeface typeface : CustomTypeface.values())
                if (typeface.getValue() == value) {
                    result = typeface;
                    break;
                }
            return result;
        }
    }
}
