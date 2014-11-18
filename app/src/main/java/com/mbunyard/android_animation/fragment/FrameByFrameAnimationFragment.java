package com.mbunyard.android_animation.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mbunyard.android_animation.R;

public class FrameByFrameAnimationFragment extends BaseFragment {

    public static final String TAG = FrameByFrameAnimationFragment.class.getSimpleName();

    public FrameByFrameAnimationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate XML layout.
        View rootView = inflater.inflate(R.layout.fragment_frame_by_frame, container, false);

        // Set ImageView background to frame-by-frame animation-list drawable.
        ImageView plane = (ImageView) rootView.findViewById(R.id.plane);
        plane.setBackgroundResource(R.drawable.frame_up_down_shift);

        // Start animation.
        AnimationDrawable animation = (AnimationDrawable) plane.getBackground();
        animation.start();

        return rootView;
    }

    @Override
    protected String getFragmentTag() {
        return TAG;
    }

}