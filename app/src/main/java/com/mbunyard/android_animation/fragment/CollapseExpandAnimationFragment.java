package com.mbunyard.android_animation.fragment;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mbunyard.android_animation.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CollapseExpandAnimationFragment extends BaseFragment {

    public static final String TAG = CollapseExpandAnimationFragment.class.getSimpleName();

    /**
     * Top container layout.
     */
    @InjectView(R.id.container_top)
    LinearLayout topContainerView;

    /**
     * Bottom container layout.
     */
    @InjectView(R.id.container_bottom)
    LinearLayout bottomContainerView;

    /**
     * Booleans to track expanded view states.
     */
    private boolean isTopContainerExpanded;

    /**
     * Animator/animation constants.
     */
    private float EXPANDED_WEIGHT = 0.9f;   // layout_weight view should have at end of expand animation.
    private float COLLAPSED_WEIGHT = 0.1f;  // layout_weight view should have at end of collapse animation.
    private long ANIMATION_DURATION = 500;  // duration of the animation in milliseconds.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState) {
        // Inflate layout/view(s).
        View rootView = inflater.inflate(R.layout.fragment_collapse_scale_animation, container, false);

        // Inject view references.
        ButterKnife.inject(this, rootView);

        // Initialize the expanded state.
        final float topContainerWeight = ((LinearLayout.LayoutParams) topContainerView.getLayoutParams()).weight;
        isTopContainerExpanded = topContainerWeight > COLLAPSED_WEIGHT;

        return rootView;
    }

    @OnClick({ R.id.container_top, R.id.container_bottom })
    public void onTopViewClick(View clickedView) {
        if (isTopContainerExpanded) {
            collapse(topContainerView);
            expand(bottomContainerView);
            isTopContainerExpanded = false;
        } else {
            collapse(bottomContainerView);
            expand(topContainerView);
            isTopContainerExpanded = true;
        }
    }

    @Override
    protected String getFragmentTag() {
        return TAG;
    }

    // ------------------ Internal API ------------------

    private void expand(final LinearLayout containerLayout) {
        float currentViewWeight = ((LinearLayout.LayoutParams) containerLayout.getLayoutParams()).weight;
        animateLinearLayoutWeightValue(containerLayout, currentViewWeight, EXPANDED_WEIGHT);
    }

    private void collapse(final LinearLayout containerLayout) {
        float currentViewWeight = ((LinearLayout.LayoutParams) containerLayout.getLayoutParams()).weight;
        animateLinearLayoutWeightValue(containerLayout, currentViewWeight, COLLAPSED_WEIGHT);
    }

    /**
     * Animates a LinearLayout's weight/layout_weight value using a ValueAnimator rather than
     * an ObjectAnimator due to LinearLayout class not having getter/setter for weight/layout_weight
     * attribute.
     * @param viewToAnimate
     * @param startWeight
     * @param endWeight
     */
    private void animateLinearLayoutWeightValue(final LinearLayout viewToAnimate, float startWeight, float endWeight) {
        ValueAnimator expandAnimator = ValueAnimator.ofFloat(startWeight, endWeight);
        expandAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // Update the view's weight with the new/current weight returned from ValueAnimator.
                // Note: this listener is called many times throughout the duration of the animation
                //       allowing the app to "manually" animate/adjust a View property value
                //       without the View having getter/setter for the property.
                float updatedCurrentViewWeight = (Float) valueAnimator.getAnimatedValue();
                LinearLayout.LayoutParams layoutParams =
                        (LinearLayout.LayoutParams) viewToAnimate.getLayoutParams();
                layoutParams.weight = updatedCurrentViewWeight;
                viewToAnimate.setLayoutParams(layoutParams);
            }
        });
        expandAnimator.setDuration(ANIMATION_DURATION);
        expandAnimator.start();
    }

}