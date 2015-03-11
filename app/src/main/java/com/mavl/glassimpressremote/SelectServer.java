package com.mavl.glassimpressremote;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;
import com.mavl.glassimpressremote.adapters.ServersAdapter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

public class SelectServer extends Activity {

    private CardScrollView mCardScroller;

    private View mView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new ServersAdapter());
        setContentView(mCardScroller);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCardScroller.activate();
    }

    @Override
    protected void onPause() {
        mCardScroller.deactivate();
        super.onPause();
    }



}
