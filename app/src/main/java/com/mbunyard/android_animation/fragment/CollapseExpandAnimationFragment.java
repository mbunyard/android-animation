package com.mbunyard.android_animation.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
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
     * Animation constants.
     */
    private static final long ANIMATION_DURATION = 500;  // duration of the animation in milliseconds.
    private static final float EXPANDED_WEIGHT = 0.9f;   // layout_weight view should have at end of expand animation.
    private static final float COLLAPSED_WEIGHT = 0.1f;  // layout_weight view should have at end of collapse animation.
    private static final float IMAGE_SCALE_MIN = 0.66f;  // 66% of original scale.
    private static final float IMAGE_SCALE_MAX = 1.0f;   // 100% of original scale.
    private static float TEXT_SIZE_MIN;                  // min sp text will be scaled/animated to.
    private static float TEXT_SIZE_MAX;                  // max sp text will be scaled/animated to.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState) {
        // Inflate layout/view(s).
        View rootView = inflater.inflate(R.layout.fragment_collapse_scale_animation, container, false);

        // Inject view references.
        ButterKnife.inject(this, rootView);

        // Initialize the expanded state.
        final float topContainerWeight = ((LinearLayout.LayoutParams) topContainerView.getLayoutParams()).weight;
        isTopContainerExpanded = topContainerWeight > COLLAPSED_WEIGHT;

        // Initialize values used in animating views.
        initAnimationValues();

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

    private void initAnimationValues() {
        float densityMultiplier = getResources().getDisplayMetrics().density;
        TEXT_SIZE_MIN = getResources().getDimension(R.dimen.collapsed_text_size) / densityMultiplier;
        TEXT_SIZE_MAX = getResources().getDimension(R.dimen.expanded_text_size) / densityMultiplier;
    }

    private void expand(final LinearLayout containerLayout) {
        float currentViewWeight = ((LinearLayout.LayoutParams) containerLayout.getLayoutParams()).weight;
        animateLinearLayoutWeightValue(containerLayout, currentViewWeight, EXPANDED_WEIGHT);

        // Could scale each containerLayout child view, allowing contents of container to be defined
        // in XML layout and remove need for explicit view referencing as done in this example.
    }

    private void collapse(final LinearLayout containerLayout) {
        float currentViewWeight = ((LinearLayout.LayoutParams) containerLayout.getLayoutParams()).weight;
        animateLinearLayoutWeightValue(containerLayout, currentViewWeight, COLLAPSED_WEIGHT);

        // Could scale each containerLayout child view, allowing contents of container to be defined
        // in XML layout and remove need for explicit view referencing as done in this example.
    }

    /**
     * Animates a LinearLayout's weight/layout_weight value using a ValueAnimator rather than
     * an ObjectAnimator due to LinearLayout class not having getter/setter for weight/layout_weight
     * attribute.
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

    /**
     * Rather than programmatically locating child views of the bottom container, this example app
     * references the specific views to animate/scale.
     */
    private void scaleBottomPaneContent(boolean toIncreaseScale) {
        ObjectAnimator fromTextSizeAnimator = ObjectAnimator.ofFloat(
                flightFromView,
                "textSize",
                (toIncreaseScale ? TEXT_SIZE_MIN : TEXT_SIZE_MAX),
                (toIncreaseScale ? TEXT_SIZE_MAX : TEXT_SIZE_MIN)
        );
        ObjectAnimator toTextSizeAnimator = ObjectAnimator.ofFloat(
                flightToView,
                "textSize",
                (toIncreaseScale ? TEXT_SIZE_MIN : TEXT_SIZE_MAX),
                (toIncreaseScale ? TEXT_SIZE_MAX : TEXT_SIZE_MIN)
        );
        ObjectAnimator imageXAnimator = ObjectAnimator.ofFloat(
                flightImage,
                "scaleX",
                (toIncreaseScale ? IMAGE_SCALE_MIN : IMAGE_SCALE_MAX),
                (toIncreaseScale ? IMAGE_SCALE_MAX : IMAGE_SCALE_MIN)
        );
        ObjectAnimator imageYAnimator = ObjectAnimator.ofFloat(
                flightImage,
                "scaleY",
                (toIncreaseScale ? IMAGE_SCALE_MIN : IMAGE_SCALE_MAX),
                (toIncreaseScale ? IMAGE_SCALE_MAX : IMAGE_SCALE_MIN)
        );

        // Note: If obtaining a dimension from a resource file, the dimension value will be
        //       automatically adjusted for the device's display density. If specifying the
        //       dimension inline or referring to integer constant, adjust the dimension to
        //       the device's display density before setting as a view attribute.
        //
        //       Example: Calling getResources().getDimension(R.dimen.my_margin) on a
        //                xxhdpi (3.0) density device, with my_margin set to 8dp in dimens.xml,
        //                will return 24.
        //
        //       To manually adjust an integer for display density, multiply the integer by the
        //       density, which can be obtained by calling getResources().getDisplayMetrics().density.

        // Define min/max margins values to use in animating image margin decreases/increases.
        float imageMargin = getResources().getDimension(R.dimen.scaled_image_margin);
        int marginMin = Math.round(-imageMargin);
        int marginMax = Math.round(imageMargin);

        // Utilize a Property Animation (in this case a ValueAnimator) to animate image margins changes.
        // Increase/decrease the margins to keep margin proportionate to text and image size/scale.
        final MarginLayoutParams layoutParams = (MarginLayoutParams) flightImage.getLayoutParams();
        ValueAnimator imageMarginAnimator = ValueAnimator.ofInt(
                (toIncreaseScale ? marginMin : marginMax), (toIncreaseScale ? marginMax : marginMin));
        imageMarginAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedMargin = (int) valueAnimator.getAnimatedValue();
                layoutParams.setMargins(animatedMargin, animatedMargin, animatedMargin, animatedMargin);
                flightImage.setLayoutParams(layoutParams);
            }
        });

        // Start/run the set of scaling animations together.
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(fromTextSizeAnimator)
                .with(toTextSizeAnimator)
                .with(imageXAnimator)
                .with(imageYAnimator)
                .with(imageMarginAnimator);
        animatorSet.setDuration(ANIMATION_DURATION);
        animatorSet.start();
    }

}