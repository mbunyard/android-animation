package com.mbunyard.android_animation.fragment;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mbunyard.android_animation.R;

public class PropertyAnimationFragment extends Fragment {

    public PropertyAnimationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        // Inflate layout and get view reference to animate.
        View rootView = layoutInflater.inflate(R.layout.fragment_main, viewGroup, false);
        ImageView plane = (ImageView) rootView.findViewById(R.id.plane);

        // Scenario 01: Create and implement fade ObjectAnimator in code.
        /*
        String propertyName = "alpha";
        float from = 1.0f;
        float to = 0.0f;

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(plane, propertyName, from, to);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
        */

        // Scenario 02: Create and implement several objectAnimators within code.
        // Note: could load animators from XML.
        /*
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator01).before(animator02);
        animatorSet.play(animator02).with(animator03);
        animatorSet.play(animator04).after(animator03);
        animatorSet.start();
        */

        // Scenario 03: Implement fade objectAnimator or set of objectAnimators from XML resource.
        Animator animator = AnimatorInflater.loadAnimator(getActivity(), R.animator.fade_in);
        animator.setTarget(plane);
        animator.start();

        return rootView;
    }
}