package com.mbunyard.android_animation.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.mbunyard.android_animation.R;

public abstract class BaseFragment extends Fragment {

    public static final String TAG = BaseFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Indicate fragment to add items to the Options Menu (i.e. call onCreateOptionsMenu()).
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.menu_fragment_base, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // Handle action bar item clicks.
        if (menuItem.getItemId() == R.id.action_refresh) {
            startActionBarRefreshAnimation(menuItem);
            refreshFragment();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    protected abstract String getFragmentTag();

    // -------------------- Internal API --------------------

    private void startActionBarRefreshAnimation(MenuItem menuItem) {
        LayoutInflater layoutInflater =
                (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView refreshActionView =
                (ImageView) layoutInflater.inflate(R.layout.action_refresh, null);
        Animation rotateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_refresh);
        refreshActionView.startAnimation(rotateAnimation);
        menuItem.setActionView(refreshActionView);
    }

    /**
     * Refreshes fragment to re-run animation(s).
     */
    private void refreshFragment() {
        Fragment fragment = getFragmentManager().findFragmentByTag(getFragmentTag());
        if (fragment != null) {
            // Detach and reattach fragment for refresh effect.
            getFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();
        }
    }
}