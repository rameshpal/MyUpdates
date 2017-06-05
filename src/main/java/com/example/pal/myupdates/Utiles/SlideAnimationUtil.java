package com.example.pal.myupdates.Utiles;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.pal.myupdates.R;

/**
 * Created by PaL on 6/5/2017.
 */

public class SlideAnimationUtil {
/*

    public static void slideInFromLeft(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_from_left);
    }

    public static void slideOutToLeft(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_to_left);
    }

*/
    public static void slideInFromRight(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_out_left);
    }

    public static void slideOutToRight(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_in_right);
    }

    private static void runSimpleAnimation(Context context, View view, int animationId) {
        view.startAnimation(AnimationUtils.loadAnimation(context, animationId));
    }
}
