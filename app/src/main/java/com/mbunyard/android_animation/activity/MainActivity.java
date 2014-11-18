package com.mbunyard.android_animation.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.mbunyard.android_animation.R;
import com.mbunyard.android_animation.fragment.FrameByFrameAnimationFragment;
import com.mbunyard.android_animation.fragment.LayoutAnimationFragment;
import com.mbunyard.android_animation.fragment.PropertyAnimationFragment;
import com.mbunyard.android_animation.fragment.ViewAnimationFragment;


public class MainActivity extends Activity implements ActionBar.OnNavigationListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // Do nothing, as onNavigationItemSelected callback will be invoked and set
            // the first fragment for display.
        } else {
            // TODO: restore last navigation selection
        }

        // Initialize action bar navigation.
        final ActionBar actionBar = getActionBar();
        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(
                actionBar.getThemedContext(),
                R.array.action_list,
                android.R.layout.simple_spinner_dropdown_item);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(spinnerAdapter, this);
    }

    /**
     * Callback for user selection from action bar spinner navigation list.
     */
    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        switch (itemPosition) {
            case 0:
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new PropertyAnimationFragment())
                        .commit();
                break;
            case 1:
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new ViewAnimationFragment())
                        .commit();
                break;
            case 2:
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new LayoutAnimationFragment())
                        .commit();
                break;
            case 3:
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new FrameByFrameAnimationFragment())
                        .commit();
                break;
            default:
                return false;
        }
        return true;
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
