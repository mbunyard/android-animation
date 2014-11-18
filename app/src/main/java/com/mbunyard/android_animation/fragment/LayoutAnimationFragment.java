package com.mbunyard.android_animation.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.mbunyard.android_animation.R;

public class LayoutAnimationFragment extends Fragment {

    public static final String TAG = LayoutAnimationFragment.class.getSimpleName();

    public LayoutAnimationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        // Inflate XML layout and obtain reference to ViewGroup being animated.
        View rootView = layoutInflater.inflate(R.layout.fragment_layout_animation, viewGroup, false);

        // Scenario 01: Create and implement layout ObjectAnimator in code.
        /*
        ViewGroup animatedViewGroup = (ViewGroup) rootView.findViewById(R.id.animated_layout);
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.popin);
        animatedViewGroup.setLayoutAnimation(new LayoutAnimationController(animation));
        animatedViewGroup.scheduleLayoutAnimation();
        */

        // Scenario 02: XML - Nothing in code, just add the following attribute to the ViewGroup,
        // which points to a "layoutAnimation" resource (see layout_popin.xml as example).
        // android:layoutAnimation="@anim/layout_popin"

        return rootView;
    }

}