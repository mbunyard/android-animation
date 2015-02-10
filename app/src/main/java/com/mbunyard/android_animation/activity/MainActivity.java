package com.mbunyard.android_animation.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.mbunyard.android_animation.R;
import com.mbunyard.android_animation.fragment.CollapseExpandAnimationFragment;
import com.mbunyard.android_animation.fragment.FrameByFrameAnimationFragment;
import com.mbunyard.android_animation.fragment.LayoutAnimationFragment;
import com.mbunyard.android_animation.fragment.PropertyAnimationFragment;
import com.mbunyard.android_animation.fragment.ViewAnimationFragment;


public class MainActivity extends Activity implements ActionBar.OnNavigationListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Serialization Bundle key representing previously selected navigation item.
     */
    private static final String SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize action bar navigation.
        final ActionBar actionBar = getActionBar();
        // Rather than using activity for ArrayAdapter context, use action bar's themed context to
        // ensure text color is determined by action bar background rather than activity background.
        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(
                actionBar.getThemedContext(),
                R.array.action_list,
                android.R.layout.simple_spinner_dropdown_item);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(spinnerAdapter, this);

        // Determine if a previous activity state needs to be restored.
        if (savedInstanceState != null) {
            // Restore last navigation selection.
            restorePreviousNavSelection(savedInstanceState);
        } else {
            // Do nothing, as onNavigationItemSelected callback will be invoked and set
            // the first fragment for display.
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore previous navigation selection.
        restorePreviousNavSelection(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Persist the navigation selection.
        outState.putInt(SELECTED_NAVIGATION_ITEM, getActionBar().getSelectedNavigationIndex());
    }

    /**
     * Callback for user selection from action bar spinner navigation list.
     */
    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        switch (itemPosition) {
            case 0:
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new PropertyAnimationFragment(), PropertyAnimationFragment.TAG)
                        .commit();
                break;
            case 1:
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new ViewAnimationFragment(), ViewAnimationFragment.TAG)
                        .commit();
                break;
            case 2:
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new LayoutAnimationFragment(), LayoutAnimationFragment.TAG)
                        .commit();
                break;
            case 3:
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new FrameByFrameAnimationFragment(), FrameByFrameAnimationFragment.TAG)
                        .commit();
                break;
            case 4:
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new CollapseExpandAnimationFragment(), CollapseExpandAnimationFragment.TAG)
                        .commit();
                break;
            default:
                return false;
        }
        return true;
    }

    // -------------------- Internal API --------------------

    /**
     * Determines if a previous navigation selection was made/saved and if so, restore the selection.
     */
    private void restorePreviousNavSelection(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(SELECTED_NAVIGATION_ITEM));
        }
    }

}