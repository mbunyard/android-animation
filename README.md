# 3 Kinds of Animations #

## Tweened View Animations ##
Tweened animations are applied to Views, letting you define a series of changes in position,
size, rotation, and opacity that animate the View contents.

+ offer a simple way to provide depth, movement, or feedback to your users at a minimal resource cost
+ used to apply a set of orientation, scale, position, and opacity changes is much less
  resource-intensive than manually redrawing the Canvas to achieve similar effects
+ simple to implement
+ created using the **Animation** class
    - [AlphaAnimation](http://developer.android.com/reference/android/view/animation/AlphaAnimation.html)
    - [RotateAnimation](http://developer.android.com/reference/android/view/animation/RotateAnimation.html)
    - [ScaleAnimation](http://developer.android.com/reference/android/view/animation/ScaleAnimation.html)
    - [TranslateAnimation](http://developer.android.com/reference/android/view/animation/TranslateAnimation.html)

A LayoutAnimation is used to animate ViewGroups, applying a single Animation or AnimationSet to
each child View in a predetermined sequence.

+ Can create layoutAnimation XML in anim directory, then add to ViewGroup via layoutAnimation XML attribute or in code
+ [LayoutAnimationController](http://developer.android.com/reference/android/view/animation/LayoutAnimationController.html)
+ [GridLayoutAnimationController](http://developer.android.com/reference/android/view/animation/GridLayoutAnimationController.html)

#### Tweened animations are commonly used to: ####
+ transition between Activities
+ transition between layouts within an Activity
+ transition between different content displayed within the same view
+ provide user feedback, such as indicating progress or "shaking" an input box to indicate an
  incorrect or invalid data entry

## Frame Animations ##
Traditional cell-based animations in which a different Drawable is displayed in each frame.
Frame-by-frame animations are displayed within a View, using its Canvas as a projection screen.

## Interpolated Property Animations ##
The property animation system enables you to animate almost anything within your application.
It's a framework designed to affect any object property over a period of time using the specified
interpolation technique.

An interpolator is an animation modifier defined in XML that affects the rate of change in an
animation. This allows your existing animation effects to bbe accelerated, decelerated, repeated,
bounced, etc.

+ [AccelerateDecelerateInterpolator](http://developer.android.com/reference/android/view/animation/AccelerateDecelerateInterpolator.html)
+ [AccelerateInterpolator](http://developer.android.com/reference/android/view/animation/AccelerateInterpolator.html)
+ [AnticipateInterpolator](http://developer.android.com/reference/android/view/animation/AnticipateInterpolator.html)
+ [AnticipateOvershootInterpolator](http://developer.android.com/reference/android/view/animation/AnticipateOvershootInterpolator.html)
+ [BounceInterpolator](http://developer.android.com/reference/android/view/animation/BounceInterpolator.html)
+ [CycleInterpolator](http://developer.android.com/reference/android/view/animation/CycleInterpolator.html)
+ [DecelerateInterpolator](http://developer.android.com/reference/android/view/animation/DecelerateInterpolator.html)
+ [LinearInterpolator](http://developer.android.com/reference/android/view/animation/LinearInterpolator.html)
+ [OvershootInterpolator](http://developer.android.com/reference/android/view/animation/OvershootInterpolator.html)

# Android Animations #

## [Property Animations](http://developer.android.com/guide/topics/graphics/prop-animation.html) ##
A tweened animation that can be used to potentially animate any property on the target object by
applying incremental change between two values. This can be used for anything from changing the
color or opacity of a View to gradually fade it in or out, to changing a font size, or increasing
a character's hit points.

Each property animation is stored in the **res/animator** directory within the project.

Property animators are extremely useful and used extensively for animating Fragments.

The simplest technique for creating property animations is using an
[ObjectAnimator](http://developer.android.com/reference/android/animation/ObjectAnimator.html).
[example] (http://developer.android.com/guide/topics/graphics/prop-animation.html#view-prop-animator)

If a getter and setter are not available for the desired property (height, width, weight, etc.),
a [ValueAnimator] (http://developer.android.com/reference/android/animation/ValueAnimator.html)
is the preferred method of property animation. [example](http://stackoverflow.com/a/16355027)

To group and choreograph property animations, use an
[AnimatorSet](http://developer.android.com/reference/android/animation/AnimatorSet.html).

## [View Animations](http://developer.android.com/guide/topics/graphics/view-animation.html) ##
Tweened animations that can be applied to rotate, move, and stretch a View.

Each view animation is stored in the **res/anim** directory within the project.

An animation can be defined for changes in alpha (fading), scale (scaling), translate (movement),
or rotate (rotation).

You can create a combination of animations using the **set** tag/element.

## [Frame/Drawable Animations](http://developer.android.com/guide/topics/graphics/drawable-animation.html) ##
Frame-by-frame "cell" animations used to display a sequence of Drawable images.

Because frame-by-frame animations represent animated Drawables, they are stored in the
**res/drawable** directory within the project.

### References ###
+ [Adding Animations](http://developer.android.com/training/animation)
+ [Animation and Graphics](http://developer.android.com/guide/topics/graphics)
+ [Animation Resources (XML)](http://developer.android.com/guide/topics/resources/animation-resource.html)
+ [fillBefore, fillAfter, and fillEnabled] (http://graphics-geek.blogspot.com/2011/08/mysterious-behavior-of-fillbefore.html)
+ [Material Design - Defining Custom Animations] (https://developer.android.com/training/material/animations.html)

Professional Android 4 Application Development - pg. 114-118, 486-494