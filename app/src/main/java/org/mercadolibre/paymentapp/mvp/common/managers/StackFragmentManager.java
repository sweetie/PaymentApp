package org.mercadolibre.paymentapp.mvp.common.managers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * Created by Enny Querales
 */
public class StackFragmentManager {

    /**
     * Returns the number of fragments currently stored in the stack
     *
     * @param context AppCompatActivity context
     */
    public static int getCount(Context context) {
        return ((AppCompatActivity) context)
                .getSupportFragmentManager()
                .getBackStackEntryCount();
    }

    /**
     * Returns the name of the fragment at the top
     *
     * @param context AppCompatActivity context
     */
    public static String getTopName(Context context) {
        if (getCount(context) > 1) {
            int top = getCount(context) - 1;
            String name = ((AppCompatActivity) context)
                    .getSupportFragmentManager()
                    .getBackStackEntryAt(top).getName();

            return name.substring(name.lastIndexOf(".") + 1, name.length());
        } else {
            return "empty";
        }
    }

    /**
     * Removes fragment at the top
     *
     * @param context AppCompatActivity context
     */
    public static void popBackStack(Context context) {
        ((AppCompatActivity) context).getSupportFragmentManager().popBackStack();
    }

    /**
     * Checks weather a fragment has been added to the back stack
     *
     * @param context AppCompatActivity context
     * @param name    fragment to be found
     */
    public static boolean isStacked(Context context, String name) {
        boolean found = false;
        List<Fragment> fragments = ((AppCompatActivity) context).getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment.getTag().equalsIgnoreCase(name)) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * Removes fragment from back stack
     *
     * @param context AppCompatActivity context
     * @param name    fragment to be removed
     */
    public static void popFragment(Context context, String name) {
        Fragment popFragment = null;
        List<Fragment> fragments = ((AppCompatActivity) context).getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment.getTag().equalsIgnoreCase(name)) {
                popFragment = fragment;
                break;
            }
        }

        if (popFragment != null) {
            ((AppCompatActivity) context).getSupportFragmentManager()
                    .beginTransaction().remove(popFragment).commitNow();
        }
    }
}
