package com.mbunyard.android_animation.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
        Log.d(TAG, "*** onCreateOptionsMenu");
        menuInflater.inflate(R.menu.menu_fragment_base, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks.
        if (item.getItemId() == R.id.action_refresh) {
            refreshFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract String getFragmentTag();

    protected void refreshFragment() {
        Fragment fragment = getFragmentManager().findFragmentByTag(getFragmentTag());
        if (fragment != null) {
            getFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();
        }
    }
}