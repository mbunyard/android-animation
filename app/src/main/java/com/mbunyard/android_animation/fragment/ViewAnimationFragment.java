package com.mbunyard.android_animation.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.mbunyard.android_animation.R;

public class ViewAnimationFragment extends BaseFragment {

    public static final String TAG = ViewAnimationFragment.class.getSimpleName();

    public ViewAnimationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        // Inflate XML layout and get reference to view to animate.
        View rootView = layoutInflater.inflate(R.layout.fragment_main, viewGroup, false);
        ImageView plane = (ImageView) rootView.findViewById(R.id.plane);

        // Instantiate animation from XML resource and set animation listener for logging.
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.popin);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d(TAG, "*** onAnimationStart()");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d(TAG, "*** onAnimationEnd()");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d(TAG, "*** onAnimationRepeat()");
            }
        });

        // Start animating the view.
        plane.startAnimation(animation);

        return rootView;
    }

    @Override
    protected String getFragmentTag() {
        return TAG;
    }

}