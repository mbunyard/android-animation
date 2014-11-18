package com.mbunyard.android_animation.activity;

import android.app.Activity;
import android.os.Bundle;

import com.mbunyard.android_animation.fragment.LayoutAnimationFragment;
import com.mbunyard.android_animation.fragment.PropertyAnimationFragment;
import com.mbunyard.android_animation.fragment.ViewAnimationFragment;
import com.mbunyard.android_animation.R;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: add action bar spinner menu to switch animation experiences.

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PropertyAnimationFragment())
                    .commit();
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}