package com.mbunyard.android_animation.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.mbunyard.android_animation.R;

public abstract class BaseFragment extends Fragment implements Animation.AnimationListener {

    public static final String TAG = BaseFragment.class.getSimpleName();

    private Menu menu;
    private MenuItem mProgress;

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
        this.menu = menu;
        this.mProgress = menu.findItem(R.id.action_refresh);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // Handle action bar item clicks.
        if (menuItem.getItemId() == R.id.action_refresh) {
            Log.d(TAG, "*** refresh clicked");

            new DummyAsyncTask().execute();

            //startActionBarRefreshAnimation(menuItem);
            //refreshFragment();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    protected abstract String getFragmentTag();

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Log.d(TAG, "*** animated ended");
        /*
        MenuItem refreshMenuItem = menu.findItem(R.id.action_refresh);
        if (refreshMenuItem.getActionView() != null) {
            // Remove the animation.
            refreshMenuItem.getActionView().clearAnimation();
            refreshMenuItem.setActionView(null);
            Log.d(TAG, "*** animation removed");
        }
        */
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    // -------------------- Internal API --------------------

    private void startActionBarRefreshAnimation(MenuItem menuItem) {
        LayoutInflater layoutInflater =
                (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ImageView refreshActionView =
                (ImageView) layoutInflater.inflate(R.layout.action_refresh, null);
        Animation rotateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_refresh);
        rotateAnimation.setAnimationListener(this);
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

    private class DummyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            BaseFragment.this.mProgress.setActionView(R.layout.actionview_progress);
        }

        @Override
        protected Void doInBackground(Void... params) {
            SystemClock.sleep(1000);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            BaseFragment.this.mProgress.setActionView(null);
        }


    }
}