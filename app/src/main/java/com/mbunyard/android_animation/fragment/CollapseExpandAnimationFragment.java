package com.mbunyard.android_animation.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
     * Bottom container views.
     */
    @InjectView(R.id.flight_from)
    TextView flightFromView;
    @InjectView(R.id.flight_to)
    TextView flightToView;
    @InjectView(R.id.flight_image)
    ImageView flightImage;

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

        // TODO: REMOVE
        Log.d(TAG, "***** BEFORE Padding - Left: " + flightFromView.getPaddingLeft()
                        + " | Top: " + flightFromView.getPaddingTop()
                        + " | Right: " + flightFromView.getPaddingRight()
                        + " | Bottom: " + flightFromView.getPaddingBottom()
        );
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) flightFromView.getLayoutParams();
        Log.d(TAG, "***** BEFORE Margin - Left: " + layoutParams.leftMargin
                        + " | Top: " + layoutParams.topMargin
                        + " | Right: " + layoutParams.rightMargin
                        + " | Bottom: " + layoutParams.bottomMargin
        );

        return rootView;
    }

    @OnClick({R.id.container_top, R.id.container_bottom})
    public void onContainerClick() {
        if (isTopContainerExpanded) {
            collapse(topContainerView);
            expand(bottomContainerView);
            scaleBottomPaneContent(true);
            isTopContainerExpanded = false;
        } else {
            collapse(bottomContainerView);
            expand(topContainerView);
            scaleBottomPaneContent(false);
            isTopContainerExpanded = true;
        }
    }

    @Override
    protected String getFragmentTag() {
        return TAG;
    }

    // ------------------ Internal API ------------------

    private void scaleBottomPaneContent(boolean increaseScale) {
        ObjectAnimator fromTextSizeAnimator = ObjectAnimator.ofFloat(
                flightFromView,
                "textSize",
                20f,
                (increaseScale ? 20f : 30f),
                (increaseScale ? 30f : 20f)
        );
        ObjectAnimator toTextSizeAnimator = ObjectAnimator.ofFloat(
                flightToView,
                "textSize",
                20f,
                (increaseScale ? 20f : 30f),
                (increaseScale ? 30f : 20f)
        );
        ObjectAnimator imageXAnimator = ObjectAnimator.ofFloat(
                flightImage,
                "scaleX",
                (increaseScale ? 0.66f : 1.0f),
                (increaseScale ? 1.0f : 0.66f)
        );
        ObjectAnimator imageYAnimator = ObjectAnimator.ofFloat(
                flightImage,
                "scaleY",
                (increaseScale ? 0.66f : 1.0f),
                (increaseScale ? 1.0f : 0.66f)
        );


        // Calculate padding/margins adjusted for device density
        final float densityMultiplier = getResources().getDisplayMetrics().density;
        int lowValue = Math.round(-4 * densityMultiplier);
        int highValue = Math.round(4 * densityMultiplier);
        Log.d(TAG, "***** BEFORE densityMultiplier: " + densityMultiplier + " | lowValue: " + lowValue + " | highValue: " + highValue);

        ValueAnimator imagePaddingAnimator = ValueAnimator.ofInt((increaseScale ? lowValue : highValue), (increaseScale ? highValue : lowValue));
        imagePaddingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                Log.d(TAG, "***** animatedValue = " + animatedValue);

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) flightFromView.getLayoutParams();
                layoutParams.setMargins(animatedValue, animatedValue, animatedValue, animatedValue);
                flightFromView.setLayoutParams(layoutParams);
                flightImage.setLayoutParams(layoutParams);
                flightToView.setLayoutParams(layoutParams);
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(fromTextSizeAnimator).with(toTextSizeAnimator).with(imageXAnimator).with(imageYAnimator).with(imagePaddingAnimator);
        animatorSet.setDuration(ANIMATION_DURATION);
        animatorSet.start();
    }

    private void expand(final LinearLayout containerLayout) {
        float currentViewWeight = ((LinearLayout.LayoutParams) containerLayout.getLayoutParams()).weight;
        animateLinearLayoutWeightValue(containerLayout, currentViewWeight, EXPANDED_WEIGHT);

        // TODO: Increase any of containerLayout's TextViews text size.
        //containerLayout.findViewById()
    }

    private void collapse(final LinearLayout containerLayout) {
        float currentViewWeight = ((LinearLayout.LayoutParams) containerLayout.getLayoutParams()).weight;
        animateLinearLayoutWeightValue(containerLayout, currentViewWeight, COLLAPSED_WEIGHT);

        // TODO: Reduce any of containerLayout's TextViews text size.
        //containerLayout.findViewById()

        // TODO: Queue animations in AnimatorSet
        /*
        AnimatorSet set = new AnimatorSet();
        set.play(textSizeAnimator).with().with()
        set.start();
        */
    }

    /**
     * Animates a LinearLayout's weight/layout_weight value using a ValueAnimator rather than
     * an ObjectAnimator due to LinearLayout class not having getter/setter for weight/layout_weight
     * attribute.
     *
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